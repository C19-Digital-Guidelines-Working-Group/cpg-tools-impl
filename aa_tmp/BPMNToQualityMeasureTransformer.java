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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Expression;
import org.hl7.fhir.r4.model.Measure;
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
import org.omg.spec.bpmn._20100524.model.TFlowElement;
import org.omg.spec.bpmn._20100524.model.TFormalExpression;
import org.omg.spec.bpmn._20100524.model.TGateway;
import org.omg.spec.bpmn._20100524.model.TProcess;
import org.omg.spec.bpmn._20100524.model.TSequenceFlow;
import org.omg.spec.bpmn._20100524.model.TStartEvent;
import org.omg.spec.bpmn._20100524.model.TTask;

public class BPMNToQualityMeasureTransformer {


  static File bpmnFolder = new File("C:\\Users\\M123110\\Desktop\\CovidCPG\\omg");
  static File msrFolder = new File("C:\\Users\\M123110\\Desktop\\CovidCPG\\fhir\\measures");

  Map<String,TTask> taskMap = new HashMap<>();
  Map<String,PlanDefinitionActionComponent> actionMap = new HashMap<>();

  public static void main(String... args) {
    new BPMNToQualityMeasureTransformer().run();
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
        .forEach(bpmn -> toQualityMeasures(bpmn));

  }

  private void toQualityMeasures(TDefinitions bpmn) {
    PlanDefinition planDef = new PlanDefinition();
    planDef.setName(bpmn.getName());
    planDef.setExperimental(true);

    TProcess process = bpmn.getRootElement().stream()
        .map(JAXBElement::getValue)
        .filter(TProcess.class::isInstance)
        .map(TProcess.class::cast)
        .findFirst().orElseThrow();

    TTask incl = findInclusionTask(process);
    String I = getSourceExpression(incl);
    TTask excl = findExclusionTask(incl, process);
    String E = getSourceExpression(excl);

    System.out.println(I);
    System.out.println(E);
    System.out.println();

    List<TTask> msr = findConditionalTask(process).stream()
        .filter(t -> t != excl && t != incl)
        .collect(Collectors.toList());

    msr.stream()
        .map(t -> toQualityMeasure(t, process, I, E))
        .filter(Optional::isPresent)
        .map(Optional::get)
    .forEach(this::persist);


  }

  private Optional<Measure> toQualityMeasure(TTask t, TProcess process, String incl, String excl) {
    Measure msr = new Measure();
    msr.setName(t.getName() + " Measure");

    String D = getPrecondition(t, process);
    Optional<String> N = getConsequence(t, process);
    if (!N.isPresent()) {
      return Optional.empty();
    }

    msr.addGroup()
        .addPopulation()
        .setCode(populationCode("measure-population"))
        .setCriteria(new Expression().setExpression(incl));

    msr.addGroup()
        .addPopulation()
        .setCode(populationCode("measure-population-exclusion"))
        .setCriteria(new Expression().setExpression(excl));

    msr.addGroup()
        .addPopulation()
        .setCode(populationCode("denominator"))
        .setCriteria(new Expression().setExpression(D));

    msr.addGroup()
        .addPopulation()
        .setCode(populationCode("numerator"))
        .setCriteria(new Expression().setExpression(N.get()));

    return Optional.of(msr);

  }

  private Optional<String> getConsequence(TTask t, TProcess process) {
    if (t.getExtensionElements() != null &&
        t.getExtensionElements().getAny() != null &&
        ! t.getExtensionElements().getAny().isEmpty()) {
      org.w3c.dom.Element extEl = (org.w3c.dom.Element) t.getExtensionElements().getAny().get(0);
      Optional<String> proc = getProcedureCode(extEl);
      return proc.map(code -> "[Procedure: " + code.substring(code.lastIndexOf('/') + 1) + "]");
    }
    return Optional.empty();
  }

  private String getPrecondition(TTask t, TProcess process) {
    TSequenceFlow incoming = findBefore(t, process);

    if (incoming.getConditionExpression() != null) {
      return getExpression(incoming.getConditionExpression());
    }
    throw new IllegalStateException();
  }


  private CodeableConcept populationCode(String type) {
    CodeableConcept c = new CodeableConcept();
    c.addCoding().setCode(type);
    return c;
  }

  private List<TTask> findConditionalTask(TProcess process) {
    return findTasks(process)
        .filter(t -> hasPrecondition(t,process))
        .collect(Collectors.toList());
  }

  private boolean hasPrecondition(TTask t, TProcess process) {
    TSequenceFlow incoming = findBefore(t, process);
    return incoming.getConditionExpression() != null;
  }

  private Stream<TTask> findTasks(TProcess process) {
    return process.getFlowElement().stream()
        .map(x -> x.getValue())
        .filter(TTask.class::isInstance)
        .map(TTask.class::cast);
  }

  private TTask findExclusionTask(TTask incl, TProcess process) {
    TSequenceFlow edge = getEdge(process, incl.getOutgoing().get(0));
    TGateway gw = (TGateway) edge.getTargetRef();
    return findTask(gw.getOutgoing(), process);
  }

  private TTask findTask(List<QName> outgoing, TProcess process) {
    return outgoing.stream()
        .map(qN -> resolve(qN, process))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .filter(TSequenceFlow.class::isInstance)
        .map(TSequenceFlow.class::cast)
        .map(f -> f.getTargetRef())
        .filter(TTask.class::isInstance)
        .map(TTask.class::cast)
        .findFirst()
        .orElseThrow();
  }

  private Optional<? extends TFlowElement> resolve(QName qN, TProcess process) {
    return process.getFlowElement().stream()
        .map(x -> x.getValue())
        .filter(x -> x.getId().contains(qN.getLocalPart()))
        .findFirst();
  }

  private String getSourceExpression(TTask t) {
    TBusinessRuleTask rule = (TBusinessRuleTask) t;
    if (! rule.getDataOutputAssociation().isEmpty()) {
      List<?> assocs = rule.getDataOutputAssociation().get(0).getSourceRef();
      if (! assocs.isEmpty()) {
        Object x = ((JAXBElement<?>) assocs.get(0)).getValue();
        if (x instanceof TDataOutput) {
          TDataOutput dataRef = (TDataOutput) x;
          return dataRef.getName();
        }
      }
    }
    throw new IllegalStateException();
  }

  private TTask findInclusionTask(TProcess process) {
    TStartEvent start = process.getFlowElement().stream()
        .map(JAXBElement::getValue)
        .filter(TStartEvent.class::isInstance)
        .map(TStartEvent.class::cast)
        .findFirst().orElseThrow();
    TSequenceFlow edge = getEdge(process,start.getOutgoing().get(0));
    TTask task = (TTask) edge.getTargetRef();
    return task;
  }


  private void persist(Measure qualMeasure) {
    String str = FhirContext.forR4().newJsonParser().setPrettyPrint(true).encodeResourceToString(qualMeasure);
    msrFolder.mkdirs();
    FileUtil.write(str, new File(msrFolder, qualMeasure.getName() + ".json"));
    System.out.println(str);
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
          .map(qName -> getEdge(process, qName))
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
    return getEdge(process, in.get(0));
  }

  private TSequenceFlow getEdge(TProcess process, QName qName) {
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
}
