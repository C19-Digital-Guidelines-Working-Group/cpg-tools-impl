package cpg.covid19.ed.poc;

import ca.uhn.fhir.context.FhirContext;
import edu.mayo.kmdp.util.FileUtil;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBElement;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DataRequirement;
import org.hl7.fhir.r4.model.Expression;
import org.hl7.fhir.r4.model.PlanDefinition;
import org.hl7.fhir.r4.model.PlanDefinition.ActionConditionKind;
import org.hl7.fhir.r4.model.PlanDefinition.PlanDefinitionActionComponent;
import org.omg.spec.dmn._20180521.model.TDMNElementReference;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.omg.spec.dmn._20180521.model.TInputData;

public class DMNToCQLRecommendationTransformer extends DMNToCQLInferenceTransformer {

  static File planFolder = new File("C:\\Users\\M123110\\Desktop\\CovidCPG\\fhir\\planDefs");

  private Map<String,TInputData> inputs = new HashMap<>();

  public static void main(String... args) {
    new DMNToCQLRecommendationTransformer().run();
  }

  protected void processDMNModel(TDefinitions tDefinitions) {
    index(tDefinitions);
    processDMNDecisions(tDefinitions, this::isActionable);
  }

  private void index(TDefinitions tDefinitions) {
    tDefinitions.getDrgElement().stream()
        .map(JAXBElement::getValue)
        .filter(el -> el instanceof TInputData)
        .map(TInputData.class::cast)
        .forEach(in -> inputs.put(in.getId(), in));
  }

  protected void processDMNDecision(TDecision d) {
    String indications = toCQLInferences(d);
    persist(d,indications);

    buildStrategy(indications, d);
  }

  private void buildStrategy(String indications, TDecision d) {
    // should not have to do this...
    indications = indications.substring(indications.indexOf("define"));
    Map<String,String> exprs = Arrays.stream(indications.trim().split("define"))
        .map(String::trim)
        .filter(s -> s.length() > 0 && s.contains(":"))
        .collect(Collectors.toMap(
            s -> s.substring(0, s.indexOf(':')).replace("\"","").trim(),
            s -> s.substring(s.indexOf(':') + 1).trim()
        ));

    String altKey = exprs.get(d.getName());
    Map<String,String> alts = exprs.entrySet().stream()
        .filter(e -> e.getKey().contains(altKey + "_"))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    PlanDefinition strategy = new PlanDefinition();
    strategy.setTitle(d.getName());
    strategy.setType(new CodeableConcept()
        .addCoding(new Coding()
            .setCode("strategy"))
    );

    alts.forEach((def, expr) -> {
      PlanDefinitionActionComponent action = strategy.addAction();
      action
          .addCondition()
          .setKind(ActionConditionKind.APPLICABILITY)
          .setExpression(new Expression().setLanguage("text/cql").setExpression(def));

      d.getInformationRequirement().stream()
          .map(req -> req.getRequiredInput())
          .map(in -> resolve(in))
          .filter(Optional::isPresent)
          .map(Optional::get)
          .forEach(input -> addRequirement(action, input));
    });

    String str = FhirContext.forR4().newJsonParser().setPrettyPrint(true).encodeResourceToString(strategy);
    planFolder.mkdirs();
    FileUtil.write(str, new File(planFolder, d.getName() + ".json"));
  }

  private void addRequirement(PlanDefinitionActionComponent action, TInputData input) {
    DataRequirement dr = new DataRequirement();
    Optional<Coding> cd = getCodeAnnotation(input);
    if (cd.isPresent()) {
      dr.addProfile(cd.get().getDisplay());
    } else {
      dr.addProfile(input.getVariable().getName());
    }
    dr.setType(toFHIRType(input.getVariable().getTypeRef()));
    action.addInput(dr);
  }

  private String toFHIRType(String typeRef) {
    switch (typeRef) {
      case "Common.Quantity" :
        return "Quantity";
      default:
        return typeRef;
    }
  }

  private Optional<TInputData> resolve(TDMNElementReference in) {
    if (in == null || in.getHref() == null) {
      return Optional.empty();
    }
    String key = in.getHref().trim().replace("#","");
    return Optional.ofNullable(inputs.get(key));
  }


  private boolean isActionable(TDecision dec) {
    return hasAnnotation(dec, "https://ontology.mayo.edu/taxonomies/KAO/DecisionType#28d541e1-bd08-3afc-a3f0-3780601576c6");
  }


  private Optional<Coding> getCodeAnnotation(TInputData input) {
    if (input.getExtensionElements() == null) {
      return Optional.empty();
    }
    List<Object> o = input.getExtensionElements().getAny();
    if (o.isEmpty()) {
      return Optional.empty();
    }
    XPath xPath = XPathFactory.newInstance().newXPath();
    try {
      String url = (String) xPath.compile("//@resourceId").evaluate(o.get(0), XPathConstants.STRING);
      String label = (String) xPath.compile("//@name").evaluate(o.get(0), XPathConstants.STRING);
      int split = url.lastIndexOf('/');
      Coding c = new Coding()
          .setCode(url.substring(0,split))
          .setSystem(url.substring(split+1))
          .setDisplay(label);
      return Optional.of(c);
    } catch (XPathExpressionException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }
}
