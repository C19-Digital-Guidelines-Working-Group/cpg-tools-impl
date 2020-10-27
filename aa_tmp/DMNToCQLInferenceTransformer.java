package cpg.covid19.ed.poc;


import static org.omg.spec.api4kp._20200801.AbstractCarrier.rep;
import static org.omg.spec.api4kp._20200801.taxonomy.krformat.SerializationFormatSeries.XML_1_1;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.DMN_1_2;
import static org.omg.spec.api4kp._20200801.taxonomy.parsinglevel.ParsingLevelSeries.Abstract_Knowledge_Expression;

import edu.mayo.kmdp.language.LanguageDeSerializer;
import edu.mayo.kmdp.language.parsers.dmn.v1_2.DMN12Parser;
import edu.mayo.kmdp.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.xml.bind.JAXBElement;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.omg.spec.api4kp._20200801.AbstractCarrier;
import org.omg.spec.api4kp._20200801.AbstractCarrier.Encodings;
import org.omg.spec.dmn._20180521.model.TBuiltinAggregator;
import org.omg.spec.dmn._20180521.model.TContext;
import org.omg.spec.dmn._20180521.model.TContextEntry;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.omg.spec.dmn._20180521.model.TDecisionRule;
import org.omg.spec.dmn._20180521.model.TDecisionTable;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.omg.spec.dmn._20180521.model.TExpression;
import org.omg.spec.dmn._20180521.model.TInputClause;
import org.omg.spec.dmn._20180521.model.TLiteralExpression;
import org.omg.spec.dmn._20180521.model.TOutputClause;
import org.omg.spec.dmn._20180521.model.TUnaryTests;

public class DMNToCQLInferenceTransformer {

  static File dmnFolder = new File("C:\\Users\\M123110\\Desktop\\CovidCPG\\omg");
  static File cqlFolder = new File("C:\\Users\\M123110\\Desktop\\CovidCPG\\fhir\\cql");

  public static void main(String... args) {
    new DMNToCQLInferenceTransformer().run();
  }

  protected void run() {
    LanguageDeSerializer parser = new LanguageDeSerializer(Arrays.asList(new DMN12Parser()));

    Arrays.stream(dmnFolder.listFiles(f -> f.getName().endsWith("dmn.xml")))
        .map(this::loadDmnXML)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(is -> AbstractCarrier.of(is)
            .withRepresentation(rep(DMN_1_2, XML_1_1, Charset.defaultCharset(), Encodings.DEFAULT))
        )
        .map(kc -> parser.applyLift(kc, Abstract_Knowledge_Expression))
        .map(ans -> ans.get().as(TDefinitions.class))
        .filter(Optional::isPresent)
        .map(Optional::get)
    .forEach(this::processDMNModel);

  }

  protected void processDMNModel(TDefinitions tDefinitions) {
    processDMNDecisions(tDefinitions, this::isAssessment);
  }

  protected void processDMNDecisions(TDefinitions tDefinitions, Predicate<TDecision> applies) {
    tDefinitions.getDrgElement().stream()
        .map(JAXBElement::getValue)
        .filter(TDecision.class::isInstance)
        .map(TDecision.class::cast)
        .filter(applies)
        .forEach(this::processDMNDecision);
  }

  protected void processDMNDecision(TDecision d) {
    persist(d,toCQLInferences(d));
  }

  protected void persist(TDecision d, String toCQLInferences) {
    cqlFolder.mkdirs();
    StringBuilder lib = new StringBuilder();

    lib.append("library \"" + d.getName() + "\"\n")
        .append("using FHIR \n\n")
    .append(toCQLInferences);

    persist(d.getName(),lib.toString());
  }

  protected void persist(String fileName, String expressions) {
    System.out.println(expressions);
    FileUtil.write(expressions, new File(cqlFolder, fileName + ".cql"));
  }

  protected String toCQLInferences(TExpression expr, String name) {
    if (expr instanceof TDecisionTable) {
      return toCQLInferences((TDecisionTable) expr);
    } else if (expr instanceof TContext) {
      return toCQLInferencesCtx((TContext) expr, name);
    } else if (expr instanceof TLiteralExpression) {
      return "\t" + ((TLiteralExpression) expr).getText();
    } else {
      throw new UnsupportedOperationException("Unable to handle logic of type " + expr.getClass());
    }
  }
  protected String toCQLInferences(TDecision d) {
    String expr = toCQLInferences(d.getExpression().getValue(), d.getVariable().getName());
    if (d.getExpression().getValue() instanceof TLiteralExpression) {
      expr = "\n define \"" + d.getName() + "\": \t\n" + expr + "\n\n";
    }
    return expr;
  }

  private String toCQLInferencesCtx(TContext context, String name) {
    StringBuilder bld = new StringBuilder();
    for (TContextEntry entry : context.getContextEntry()) {
      String var = entry.getVariable() != null ? entry.getVariable().getName() : name;
      String inf = toCQLInferences(entry.getExpression().getValue(), var);

      if (! (entry.getExpression().getValue() instanceof TContext
      || entry.getExpression().getValue() instanceof TDecisionTable)) {
        bld.append("\n define \"").append(var).append("\": \t\n");
      }
      bld.append(inf).append("\n\n");
    }
    return bld.toString();
  }

  protected String toCQLInferences(TDecisionTable table) {
    Map<String,String> rules = new LinkedHashMap<>();
    for (TDecisionRule r : table.getRule()) {
      if (table.getOutput().size() == 1) {
        TOutputClause out = table.getOutput().get(0);
        String exprName = resultName(r, table);
        String expr = toCQLInferences(r, out, table);
        rules.put(exprName, expr);
      } else {
        String exprName = resultName(r, table);
        String expr = toCQLInferences(r, table.getOutput(), table);
        rules.put(exprName, expr);

      }
    }

    String exprName;
    String expr;
    switch (table.getHitPolicy()) {
      case COLLECT:
        exprName = table.getOutputLabel();
        expr = new StringBuilder()
            .append("\n define \"").append(exprName).append("\" :\n\t")
            .append(rules.keySet().stream()
                .map(x -> "\"" + x + "\"")
                .collect(Collectors.joining(mapAggregate(table.getAggregation()))))
            .append("\n\n")
            .toString();
        rules.put(exprName, expr);
        break;
      case FIRST:
      case PRIORITY:
        exprName = table.getOutputLabel();
        expr = new StringBuilder()
            .append("\n define \"").append(exprName).append("\" :\n\t")
            .append("Coalesce( ")
            .append(rules.keySet().stream()
                .map(x -> "\"" + x + "\"")
                .collect(Collectors.joining(",")))
            .append(" )\n\n")
            .toString();
        rules.put(exprName, expr);
        break;
      case ANY:
      case UNIQUE:
        break;
      default:
        throw new UnsupportedOperationException("cant support " + table.getHitPolicy());
    }

    return flatten(rules, table);
  }

  protected String flatten(Map<String, String> rules,
      TDecisionTable table) {
    StringBuilder lib = new StringBuilder();

    rules.keySet().stream()
        .forEach(k -> lib.append(rules.get(k)));
    return lib.toString();
  }

  protected String mapAggregate(TBuiltinAggregator aggregation) {
    switch (aggregation) {
      case SUM:
        return " + ";
      case MAX:
      case MIN:
      case COUNT:
      default:
        throw new UnsupportedOperationException("TODO - fix aggregation " + aggregation);
    }
  }

  protected String toCQLInferences(TDecisionRule r, TOutputClause out, TDecisionTable table) {
    return toCQLInferences(r, Collections.singletonList(out), table);
  }

  protected String toCQLInferences(TDecisionRule r, List<TOutputClause> out, TDecisionTable table) {
    StringBuilder expr = new StringBuilder();
    expr.append("\n define \"" + resultName(r, table) + "\": \n\t");
    expr.append(toCQLExpression(r, out, table));
    return expr.toString();
  }

  protected String toCQLExpression(TDecisionRule r, List<TOutputClause> out, TDecisionTable table) {
    StringBuilder expr = new StringBuilder();
    List<String> clauses = new ArrayList<>();
    for (int j = 0; j < table.getInput().size(); j++) {
      String cql = toCQL(table.getInput().get(j), r.getInputEntry().get(j));
      if (cql.trim().length() > 0) {
        clauses.add(cql);
      }
    }
    if (clauses.size() > 0) {
      String precondition = "\"" + resultName(r,table) + "_Precondition\"";
      expr.append("if ");
      expr.append(precondition);
      expr.append(" then ");
      expr.append(getRHS(r, out, table));
      expr.append(" else " + zeroOf(r, out, table));
      expr.append("\n");

      expr.append("\n define " + precondition + ": \n\t");
      expr.append(String.join(" and ", clauses));
      expr.append("\n\n");
    } else {
      expr.append(getRHS(r, out, table));
    }
    return expr.toString();

  }

  protected String zeroOf(TDecisionRule r, List<TOutputClause> out, TDecisionTable table) {
    if (out.size() == 0) {
      return zeroOf(r, out.get(0), table);
    } else {
      return "null";
    }
  }

  protected String zeroOf(TDecisionRule r, TOutputClause out, TDecisionTable table) {
    String typeRef = out.getTypeRef() != null ? out.getTypeRef() : table.getTypeRef();
    switch (typeRef) {
      case "number" :
        return "0";
      case "boolean" :
        return "false";
      default:
        return "null";
    }
  }

  protected String getRHS(TDecisionRule r, List<TOutputClause> out, TDecisionTable table) {
    if (out.size() == 1) {
      return r.getOutputEntry().get(table.getOutput().indexOf(out.get(0))).getText();
    } else {
      StringBuilder sb = new StringBuilder();
      sb.append(" Tuple { \n");
      sb.append(IntStream.range(0,out.size())
          .mapToObj(i -> "\t\t" + out.get(i).getName() + " : " + r.getOutputEntry().get(i).getText()).collect(Collectors.joining(",\n")));
      sb.append("\n\t}\n");
      return sb.toString();
    }
  }

  protected String toCQL(TInputClause x, TUnaryTests logic) {
    return getRight(getLeft(x), logic);
  }

  protected String getRight(String left, TUnaryTests logic) {
    String txt = logic.getText();
    if ("-".equals(txt)) {
      return "";
    }
    if (txt.startsWith("[")) {
      String range = txt.substring(1, txt.length() - 1);
      Double low = Double.parseDouble(range.substring(0,range.indexOf("..")));
      Double high = Double.parseDouble(range.substring(range.indexOf("..") + 2));
      return left + " between " + low + " and " + high;
    }
    if (txt.contains(",")) {
      return left + " in {" + txt + "}";
    }
    if ("true".equals(txt)) {
      return "IsTrue( " + left + " )";
    }
    if ("false".equals(txt)) {
      return "IsFalse( " + left + " )";
    }
    if ("null".equals(txt)) {
      return "IsNull( " + left + " )";
    }
    return left + " = " + txt;
  }

  protected String getLeft(TInputClause input) {
    return Arrays.stream(input.getInputExpression().getText().split("\\."))
        .map(x -> x.contains(" ") ? ("\"" + x + "\"") : x)
        .collect(Collectors.joining("."));
  }

  protected String resultName(TDecisionRule r, TDecisionTable table) {
    return table.getOutputLabel() + "_" + table.getRule().indexOf(r);
  }

  protected boolean isAssessment(TDecision dec) {
      return hasAnnotation(dec, "https://ontology.mayo.edu/taxonomies/KAO/DecisionType#d03ea564-0d31-3e72-a98b-cb93aa4c5cce");
  }


  protected boolean hasAnnotation(TDecision dec, String annotationId) {
    if (dec.getExtensionElements() == null || dec.getExtensionElements().getAny().isEmpty()) {
      return false;
    }
    XPath xPath = XPathFactory.newInstance().newXPath();
    try {
      XPathExpression assessCheck =
          xPath.compile("//@resourceId='" + annotationId + "'");
      for (Object o : dec.getExtensionElements().getAny()) {
        if ((Boolean) assessCheck.evaluate(o, XPathConstants.BOOLEAN)) {
          return true;
        }
      }
    } catch (XPathExpressionException e) {
      e.printStackTrace();
      return false;
    }
    return false;
  }


  protected Optional<InputStream> loadDmnXML(File file) {
    try {
      return Optional.of(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

}