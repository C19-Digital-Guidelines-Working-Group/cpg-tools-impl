package cpg.covid19.ed.poc;


import static org.omg.spec.api4kp._20200801.AbstractCarrier.rep;
import static org.omg.spec.api4kp._20200801.taxonomy.krformat.SerializationFormatSeries.XML_1_1;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.BPMN_2_0;
import static org.omg.spec.api4kp._20200801.taxonomy.parsinglevel.ParsingLevelSeries.Abstract_Knowledge_Expression;

import ca.uhn.fhir.context.FhirContext;
import edu.mayo.kmdp.language.LanguageDeSerializer;
import edu.mayo.kmdp.language.parsers.bpmn.v2_02.BPMN202Parser;
import edu.mayo.kmdp.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.hl7.fhir.r4.model.ActivityDefinition;
import org.hl7.fhir.r4.model.CanonicalType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Expression;
import org.hl7.fhir.r4.model.PlanDefinition;
import org.hl7.fhir.r4.model.PlanDefinition.ActionConditionKind;
import org.hl7.fhir.r4.model.PlanDefinition.ActionRelationshipType;
import org.hl7.fhir.r4.model.PlanDefinition.PlanDefinitionActionComponent;
import org.omg.spec.api4kp._20200801.AbstractCarrier;
import org.omg.spec.api4kp._20200801.AbstractCarrier.Encodings;
import org.omg.spec.bpmn._20100524.model.TBaseElement;
import org.omg.spec.bpmn._20100524.model.TBusinessRuleTask;
import org.omg.spec.bpmn._20100524.model.TDataOutput;
import org.omg.spec.bpmn._20100524.model.TDefinitions;
import org.omg.spec.bpmn._20100524.model.TExpression;
import org.omg.spec.bpmn._20100524.model.TFormalExpression;
import org.omg.spec.bpmn._20100524.model.TGateway;
import org.omg.spec.bpmn._20100524.model.TProcess;
import org.omg.spec.bpmn._20100524.model.TSequenceFlow;
import org.omg.spec.bpmn._20100524.model.TTask;

public class BPMNToPlanDefinitionTransformer {


  static File bpmnFolder = new File("C:\\Users\\M123110\\Desktop\\CovidCPG\\omg");
  static File planFolder = new File("C:\\Users\\M123110\\Desktop\\CovidCPG\\fhir\\planDefs");

  Map<String,TTask> taskMap = new HashMap<>();
  Map<String,PlanDefinitionActionComponent> actionMap = new HashMap<>();

  public static void main(String... args) {
    new BPMNToPlanDefinitionTransformer().run();
  }

  private void run() {
    LanguageDeSerializer parser = new LanguageDeSerializer(Arrays.asList(new BPMN202Parser()));

    Arrays.stream(bpmnFolder.listFiles(f -> f.getName().endsWith("bpmn.xml")))
        .map(this::loadBPMNXml)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(is -> AbstractCarrier.of(is)
            .withRepresentation(rep(BPMN_2_0,XML_1_1, Charset.defaultCharset(), Encodings.DEFAULT))
        )
        .map(kc -> parser.applyLift(kc, Abstract_Knowledge_Expression))
        .map(ans -> ans.get().as(TDefinitions.class))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(bpmn -> toPlanDefinition(bpmn));

  }

  private void toPlanDefinition(TDefinitions bpmn) {
    PlanDefinition planDef = new PlanDefinition();
    planDef.setName(bpmn.getName());
    planDef.setExperimental(true);

    TProcess process = bpmn.getRootElement().stream()
        .map(JAXBElement::getValue)
        .filter(TProcess.class::isInstance)
        .map(TProcess.class::cast)
        .findFirst().orElseThrow();

    List<TTask> tasks =
    process.getFlowElement().stream()
        .map(JAXBElement::getValue)
        .filter(TTask.class::isInstance)
        .map(TTask.class::cast)
        .collect(Collectors.toList());

    tasks.stream().forEach(t -> addTaskToPlan(t ,process,bpmn, planDef));
    tasks.stream().forEach(t -> registerFlows(t ,process,bpmn, planDef));

   persist(planDef);
  }

  private void persist(PlanDefinition planDef) {
    String str = FhirContext.forR4().newJsonParser().setPrettyPrint(true).encodeResourceToString(planDef);
    planFolder.mkdirs();
    FileUtil.write(str, new File(planFolder, planDef.getName() + ".planDef.json"));

    planDef.getContained().stream()
        .filter(ActivityDefinition.class::isInstance)
        .map(ActivityDefinition.class::cast)
        .forEach(def -> persist(def));
    System.out.println(str);
  }

  private void persist(ActivityDefinition actDef) {
    String str = FhirContext.forR4().newJsonParser().setPrettyPrint(true).encodeResourceToString(actDef);
    planFolder.mkdirs();
    FileUtil.write(str, new File(planFolder, actDef.getName() + ".actDef.json"));

    System.out.println(str);
  }

  private void addTaskToPlan(TTask t, TProcess process, TDefinitions bpmn,
      PlanDefinition planDef) {
    PlanDefinitionActionComponent act = planDef.addAction();
    act.setTitle(t.getName());
    act.setId(t.getId());

    String expr = null;
    if (t instanceof TBusinessRuleTask) {
      TBusinessRuleTask rule = (TBusinessRuleTask) t;
      if (! rule.getDataOutputAssociation().isEmpty()) {
        List<?> assocs = rule.getDataOutputAssociation().get(0).getSourceRef();
        if (! assocs.isEmpty()) {
          Object x = ((JAXBElement<?>) assocs.get(0)).getValue();
          if (x instanceof TDataOutput) {
            TDataOutput dataRef = (TDataOutput) x;
            expr = dataRef.getName();
          }
        }
      }
    }

    if (expr != null) {
      act.addDynamicValue()
          .setExpression(new Expression()
              .setExpression(expr)
          );
    }

    if (t.getExtensionElements() != null &&
        t.getExtensionElements().getAny() != null &&
        ! t.getExtensionElements().getAny().isEmpty()) {
      org.w3c.dom.Element extEl = (org.w3c.dom.Element) t.getExtensionElements().getAny().get(0);
      Optional<String> proc = getProcedureCode(extEl);

      proc.ifPresent(
          st -> {
            ActivityDefinition def = new ActivityDefinition();
            def.setName(t.getName() + " Activity");
            def.setId("_" + UUID.randomUUID().toString());
            def.setCode(toCodeableConcept(st));
            act.setDefinition(new CanonicalType().setValue("#" + def.getId()));
            planDef.addContained(def);
          }
      );
    }

    index(act, t);
  }

  private CodeableConcept toCodeableConcept(String st) {
    CodeableConcept c = new CodeableConcept();
    c.addCoding()
        .setCode(st.substring(st.lastIndexOf('/') + 1))
        .setSystem(st.substring(0, st.lastIndexOf('/')));
    return c;
  }

  private Optional<String> getProcedureCode(org.w3c.dom.Element extEl) {
    XPath xPath = XPathFactory.newInstance().newXPath();
    try {
      String s = "//node()[@name='procedureCode']/@uri";
      XPathExpression procedureCodeExpr = xPath.compile(s);
      String uri = (String) procedureCodeExpr.evaluate(extEl, XPathConstants.STRING);
      return uri != null && uri.length() > 0 ? Optional.ofNullable(uri) : Optional.empty();
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  private void registerFlows(TTask t, TProcess process, TDefinitions bpmn,
      PlanDefinition planDef) {
    PlanDefinitionActionComponent act = actionMap.get(t.getId());
    TSequenceFlow incoming = findBefore(t, process);

    if (incoming.getConditionExpression() != null) {
      act.addCondition()
          .setKind(ActionConditionKind.APPLICABILITY)
          .setExpression(new Expression()
              .setExpression(getExpression(incoming.getConditionExpression()))
          );
    }
    if (incoming.getSourceRef() instanceof TGateway) {
      TGateway gw = (TGateway) incoming.getSourceRef();
      List<PlanDefinitionActionComponent> acts = gw.getIncoming().stream()
          .map(qName -> getInEdge(process, qName))
          .map(TSequenceFlow::getSourceRef)
          .filter(TTask.class::isInstance)
          .map(TTask.class::cast)
          .map(TBaseElement::getId)
          .map(actionMap::get)
          .collect(Collectors.toList());

      acts.forEach(previous ->
          act.addRelatedAction()
          .setRelationship(ActionRelationshipType.AFTER)
          .setActionId(previous.getId()));

      System.out.println();
    }
    incoming.toString();

  }

  private String getExpression(TExpression conditionExpression) {
    if (conditionExpression instanceof TFormalExpression) {
      TFormalExpression fex = (TFormalExpression) conditionExpression;
      return fex.getContent().get(0).toString();
    }
    throw new UnsupportedOperationException("Unable to handle type " + conditionExpression.getClass());
  }

  private TSequenceFlow findBefore(TTask t, TProcess process) {
    List<QName> in = t.getIncoming();
    if (in.size() > 1) {
      throw new UnsupportedOperationException("Cannot handle multiple incomings yet for " + t.getName());
    }
    return getInEdge(process, in.get(0));
  }

  private TSequenceFlow getInEdge(TProcess process, QName qName) {
    return process.getFlowElement().stream()
        .map(JAXBElement::getValue)
        .filter(f -> f.getId().contains(qName.getLocalPart()))
        .filter(TSequenceFlow.class::isInstance)
        .map(TSequenceFlow.class::cast)
        .findFirst()
        .orElseThrow();
  }

  private void index(PlanDefinitionActionComponent act, TTask t) {
    taskMap.put(t.getId(), t);
    actionMap.put(t.getId(), act);
  }


  private Optional<InputStream> loadBPMNXml(File file) {
    try {
      return Optional.of(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }
}
