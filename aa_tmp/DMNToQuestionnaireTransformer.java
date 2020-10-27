package cpg.covid19.ed.poc;


import static org.omg.spec.api4kp._20200801.AbstractCarrier.rep;
import static org.omg.spec.api4kp._20200801.taxonomy.krformat.SerializationFormatSeries.XML_1_1;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.DMN_1_2;
import static org.omg.spec.api4kp._20200801.taxonomy.parsinglevel.ParsingLevelSeries.Abstract_Knowledge_Expression;

import ca.uhn.fhir.context.FhirContext;
import edu.mayo.kmdp.language.LanguageDeSerializer;
import edu.mayo.kmdp.language.parsers.dmn.v1_2.DMN12Parser;
import edu.mayo.kmdp.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.xml.bind.JAXBElement;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.hl7.fhir.r4.model.CodeType;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Enumerations.PublicationStatus;
import org.hl7.fhir.r4.model.Questionnaire;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemAnswerOptionComponent;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemComponent;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemType;
import org.omg.spec.api4kp._20200801.AbstractCarrier;
import org.omg.spec.api4kp._20200801.AbstractCarrier.Encodings;
import org.omg.spec.dmn._20180521.model.TDMNElementReference;
import org.omg.spec.dmn._20180521.model.TDRGElement;
import org.omg.spec.dmn._20180521.model.TDecisionService;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.omg.spec.dmn._20180521.model.TInputData;
import org.omg.spec.dmn._20180521.model.TItemDefinition;

public class DMNToQuestionnaireTransformer {

  static File dmnFolder = new File("C:\\Users\\M123110\\Desktop\\CovidCPG\\omg");
  static File questFolders = new File("C:\\Users\\M123110\\Desktop\\CovidCPG\\fhir\\questionnaires");

  public static void main(String... args) {
    new DMNToQuestionnaireTransformer().run();
  }

  private void run() {
    LanguageDeSerializer parser = new LanguageDeSerializer(Arrays.asList(new DMN12Parser()));

    Arrays.stream(dmnFolder.listFiles(f -> f.getName().endsWith("dmn.xml")))
        .map(this::loadDmnXML)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(is -> AbstractCarrier.of(is)
            .withRepresentation(rep(DMN_1_2,
                XML_1_1, Charset.defaultCharset(), Encodings.DEFAULT))
        )
        .map(kc -> parser.applyLift(kc, Abstract_Knowledge_Expression))
        .map(ans -> ans.get().as(TDefinitions.class))
        .filter(Optional::isPresent)
        .map(Optional::get)
    .forEach(dmn -> toQuestionnaires(dmn));

  }

  private void toQuestionnaires(TDefinitions dmn) {
    dmn.getDrgElement().stream()
    .map(JAXBElement::getValue)
    .filter(TDecisionService.class::isInstance)
    .map(TDecisionService.class::cast)
    .filter(ds -> ! ds.getName().contains("Whole Model") && ! ds.getName().contains("Diagram"))
    .forEach(ds -> toQuestionnaire(ds, dmn));
  }

  private void toQuestionnaire(TDecisionService ds, TDefinitions dmn) {
    Questionnaire q = new Questionnaire();
    q.setName(ds.getName());
    q.setTitle(ds.getName());
    q.setStatus(PublicationStatus.DRAFT);
    q.addExtension(
        "http://build.fhir.org/ig/HL7/cqf-recommendations/StructureDefinition/cpg-knowledgeCapability",
        new CodeType("executable"));
    q.addExtension(
        "http://build.fhir.org/ig/HL7/cqf-recommendations/StructureDefinition/cpg-knowledgeRepresentationLevel",
        new CodeType("execbutable"));

    System.out.println(ds.getInputData().size());
    ds.getInputData().stream()
        .map(ref -> resolveInput(ref,dmn))
    .forEach(input -> addQuestion(input, q, dmn));

    String jsonQuest = FhirContext.forR4().newJsonParser().setPrettyPrint(true).encodeResourceToString(q);
    questFolders.mkdirs();
    FileUtil.write(jsonQuest, new File(questFolders,ds.getName() + ".questionnaire.json"));
  }

  private void addQuestion(TInputData input, Questionnaire q, TDefinitions dmn) {
    QuestionnaireItemComponent question = new QuestionnaireItemComponent();
    question.setText(input.getName());
    question.setLinkId(input.getVariable().getName());
    getCodeAnnotation(input)
        .ifPresent(question::addCode);
    mapDatatype(question, input.getVariable().getTypeRef(), dmn);

    q.addItem(question);
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
      System.out.println(url);
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

  private void mapDatatype(
      QuestionnaireItemComponent question,
      String typeRef,
      TDefinitions dmn) {
    if (typeRef == null) {
      question.setType(QuestionnaireItemType.NULL);
      return;
    }

    if (typeRef.contains("Quantity")) {
      question.setType(QuestionnaireItemType.QUANTITY);
    } else if (typeRef.contains("String") || typeRef.contains("string")) {
      question.setType(QuestionnaireItemType.STRING);
    } else if (typeRef.contains("Boolean") || typeRef.contains("boolean")) {
      question.setType(QuestionnaireItemType.BOOLEAN);
    } else {
      Optional<TItemDefinition> oDef = getItemDefinition(typeRef, dmn);
      if (oDef.isPresent()) {
        TItemDefinition def = oDef.get();
        if (def.getAllowedValues() != null) {
          question.setType(QuestionnaireItemType.CHOICE);
          Arrays.stream(def.getAllowedValues().getText().split(","))
              .forEach(opt -> question.addAnswerOption(
                  new QuestionnaireItemAnswerOptionComponent()
                      .setValue(new CodeType(opt))
              ));
        } else {
          mapDatatype(question, def.getTypeRef(), dmn);
        }
      } else {
        throw new UnsupportedOperationException("Unable to handle " + typeRef);
      }
    }
  }

  private Optional<TItemDefinition> getItemDefinition(String typeRef, TDefinitions dmn) {
    return dmn.getItemDefinition().stream()
        .filter(def -> def.getName().equals(typeRef))
        .findAny();
  }

  private TInputData resolveInput(TDMNElementReference ref, TDefinitions dmn) {
    return dmn.getDrgElement().stream()
        .map(JAXBElement::getValue)
        .filter(el -> isRef(ref, el))
        .filter(TInputData.class::isInstance)
        .map(TInputData.class::cast)
        .findFirst()
        .orElseThrow();
  }

  private boolean isRef(TDMNElementReference ref, TDRGElement el) {
    String frag = ref.getHref();
    frag = frag.substring(frag.lastIndexOf('#') + 1);
    return el.getId().equals(frag);
  }

  private Optional<InputStream> loadDmnXML(File file) {
    try {
      return Optional.of(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

}
