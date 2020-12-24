package cpg.covid19.ed.cql;


import static cpg.util.fhir.IOUtil.readDMNModels;
import static edu.mayo.kmdp.util.Util.isNotEmpty;
import static org.omg.spec.api4kp._20200801.AbstractCarrier.rep;
import static org.omg.spec.api4kp._20200801.taxonomy.krformat.SerializationFormatSeries.XML_1_1;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.DMN_1_2;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.resolve;
import static org.omg.spec.api4kp._20200801.taxonomy.parsinglevel.ParsingLevelSeries.Abstract_Knowledge_Expression;

import cpg.util.fhir.IOUtil;
import edu.mayo.kmdp.language.LanguageDeSerializer;
import edu.mayo.kmdp.language.parsers.dmn.v1_2.DMN12Parser;
import edu.mayo.kmdp.util.FileUtil;
import edu.mayo.kmdp.util.StreamUtil;
import edu.mayo.kmdp.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
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
import java.util.stream.Stream;
import javax.xml.bind.JAXBElement;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.omg.spec.api4kp._20200801.AbstractCarrier;
import org.omg.spec.api4kp._20200801.AbstractCarrier.Encodings;
import org.omg.spec.api4kp._20200801.services.KnowledgeCarrier;
import org.omg.spec.api4kp._20200801.surrogate.Annotation;
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


  public void run(Path dmnFolder, Path cqlFolder) {
    List<KnowledgeCarrier> kcs = readDMNModels(dmnFolder);

    for (KnowledgeCarrier dmnCarrier : kcs) {
      TDefinitions dmnModel = dmnCarrier.as(TDefinitions.class).orElseThrow();
      String cql = addHeader( dmnModel.getName(), processDMNModel(dmnModel) );
      if (isNotEmpty(cql)) {
        IOUtil.saveText(cql, cqlFolder.resolve(dmnModel.getName() + ".cql"));
      }
    }

  }

  protected String addHeader(String model, String cql) {
    if (Util.isEmpty(cql)) {
      return cql;
    }

    StringBuilder lib = new StringBuilder();

    lib.append("library \"" + model + "\"\n")
        .append("using FHIR \n\n")
        .append(cql);

    return lib.toString();
  }

  protected String processDMNModel(TDefinitions tDefinitions) {
    return processDMNDecisions(tDefinitions, this::isAssessment);
  }

  protected String processDMNDecisions(TDefinitions tDefinitions, Predicate<TDecision> applies) {
    return tDefinitions.getDrgElement().stream()
        .map(JAXBElement::getValue)
        .filter(TDecision.class::isInstance)
        .map(TDecision.class::cast)
        .filter(applies)
        .map(this::toCQLInferences)
        .collect(Collectors.joining("\n\n"));
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
        expr = "\n define \"" + exprName + "\" :\n\t"
            + rules.keySet().stream()
            .map(x -> "\"" + x + "\"")
            .collect(Collectors.joining(mapAggregate(table.getAggregation())))
            + "\n\n";
        rules.put(exprName, expr);
        break;
      case FIRST:
      case PRIORITY:
        exprName = table.getOutputLabel();
        expr = "\n define \"" + exprName + "\" :\n\t"
            + "Coalesce( "
            + rules.keySet().stream()
            .map(x -> "\"" + x + "\"")
            .collect(Collectors.joining(","))
            + " )\n\n";
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

    rules.keySet()
        .forEach(k -> lib.append(rules.get(k)));
    return lib.toString();
  }

  protected String mapAggregate(TBuiltinAggregator aggregation) {
    if (aggregation == null) {
      return ",";
    }
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
    return "\n define \"" + resultName(r, table) + "\": \n\t"
        + toCQLExpression(r, out, table);
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
    if (! out.isEmpty()) {
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
      return " Tuple { \n"
          + IntStream.range(0, out.size())
          .mapToObj(
              i -> "\t\t" + out.get(i).getName() + " : " + r.getOutputEntry().get(i).getText())
          .collect(Collectors.joining(",\n"))
          + "\n\t}\n";
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
      double low = Double.parseDouble(range.substring(0,range.indexOf("..")));
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
      return hasAnnotation(dec,
          "https://ontology.mayo.edu/taxonomies/KAO/DecisionType#d03ea564-0d31-3e72-a98b-cb93aa4c5cce");
  }

  protected boolean hasAnnotation(TDecision dec, String annotationId) {
    if (dec.getExtensionElements() == null || dec.getExtensionElements().getAny().isEmpty()) {
      return false;
    }
    return dec.getExtensionElements().getAny().stream()
        .flatMap(StreamUtil.filterAs(Annotation.class))
        .map(Annotation::getRef)
        .anyMatch(cid -> annotationId.equals(cid.getResourceId().toString()));
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
