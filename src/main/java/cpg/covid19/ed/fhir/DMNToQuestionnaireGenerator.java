package cpg.covid19.ed.fhir;


import static cpg.util.fhir.IOUtil.readDMNModels;
import static org.omg.spec.api4kp._20200801.AbstractCarrier.codedRep;
import static org.omg.spec.api4kp._20200801.AbstractCarrier.rep;
import static org.omg.spec.api4kp._20200801.taxonomy.krformat.SerializationFormatSeries.XML_1_1;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.DMN_1_2;
import static org.omg.spec.api4kp._20200801.taxonomy.parsinglevel.ParsingLevelSeries.Abstract_Knowledge_Expression;

import cpg.util.fhir.IOUtil;
import edu.mayo.kmdp.knowledgebase.KnowledgeBaseProvider;
import edu.mayo.kmdp.knowledgebase.flatteners.dmn.v1_2.DMN12ModelFlattener;
import edu.mayo.kmdp.language.parsers.dmn.v1_2.DMN12Parser;
import edu.mayo.kmdp.util.NameUtils;
import edu.mayo.kmdp.util.StreamUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.bind.JAXBElement;
import org.hl7.fhir.r4.model.CodeType;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Enumerations.PublicationStatus;
import org.hl7.fhir.r4.model.Questionnaire;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemAnswerOptionComponent;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemComponent;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemType;
import org.omg.spec.api4kp._20200801.AbstractCarrier;
import org.omg.spec.api4kp._20200801.AbstractCarrier.Encodings;
import org.omg.spec.api4kp._20200801.Answer;
import org.omg.spec.api4kp._20200801.id.ResourceIdentifier;
import org.omg.spec.api4kp._20200801.services.CompositeKnowledgeCarrier;
import org.omg.spec.api4kp._20200801.services.KnowledgeCarrier;
import org.omg.spec.api4kp._20200801.surrogate.Annotation;
import org.omg.spec.dmn._20180521.model.TDMNElementReference;
import org.omg.spec.dmn._20180521.model.TDRGElement;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.omg.spec.dmn._20180521.model.TDecisionService;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.omg.spec.dmn._20180521.model.TInformationRequirement;
import org.omg.spec.dmn._20180521.model.TInputData;
import org.omg.spec.dmn._20180521.model.TItemDefinition;

public class DMNToQuestionnaireGenerator {

  KnowledgeBaseProvider kbManager = new KnowledgeBaseProvider(null)
      .withNamedFlattener(DMN12ModelFlattener::new);

  public void run(Path dmnFolder, Path questFolders) {
    List<KnowledgeCarrier> kcs = readDMNModels(dmnFolder);

    kcs.stream()
        .flatMap(kc ->
            flatten(kc.getAssetId(), kcs)
                .flatOpt(x -> x.as(TDefinitions.class))
                .map(this::toQuestionnaires)
                .orElse(Stream.empty())
        ).forEach(q -> IOUtil.save(q, questFolders.resolve(q.getName() + ".questionnaire.r4.json")));
  }

  private Answer<KnowledgeCarrier> flatten(ResourceIdentifier assetId, List<KnowledgeCarrier> kcs) {
    CompositeKnowledgeCarrier ckc = AbstractCarrier.ofHeterogeneousComposite(kcs)
        .withRootId(assetId);

    return kbManager.initKnowledgeBase(ckc)
        .flatMap(ptr -> kbManager.flatten(ptr.getUuid(), ptr.getVersionTag()))
        .flatMap(
            ptr -> kbManager.getKnowledgeBaseManifestation(ptr.getUuid(), ptr.getVersionTag()))
        .map(KnowledgeCarrier::mainComponent);
  }


  private Stream<Questionnaire> toQuestionnaires(TDefinitions dmn) {
    return dmn.getDrgElement().stream()
        .map(JAXBElement::getValue)
        .flatMap(StreamUtil.filterAs(TDecisionService.class))
        .filter(ds -> !ds.getName().contains("Whole Model") && !ds.getName().contains("Diagram"))
        .map(ds -> toQuestionnaire(ds, dmn));
  }

  private Questionnaire toQuestionnaire(TDecisionService ds, TDefinitions dmn) {
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

    ds.getOutputDecision()
        .forEach(dref -> toQuestionnaireRoot(resolveDecision(dref.getHref(), dmn), dmn, q));
    return q;
  }

  private void toQuestionnaireRoot(TDecision rootDecision, TDefinitions dmn,
      Questionnaire q) {
    QuestionnaireItemComponent rootGroup = new QuestionnaireItemComponent()
        .setType(QuestionnaireItemType.GROUP)
        .setText(rootDecision.getName());
    q.addItem(rootGroup);
    toQuestionnaireGroup(rootDecision, dmn, rootGroup);
  }

  private void toQuestionnaireGroup(TDecision rootDecision, TDefinitions dmn,
      QuestionnaireItemComponent rootGroup) {
    rootDecision.getInformationRequirement().stream()
        .filter(ir -> ir.getRequiredDecision() != null)
        .map(TInformationRequirement::getRequiredDecision)
        .forEach(dr -> {
              TDecision sub = resolveDecision(dr.getHref(), dmn);
              List<Coding> codings = getAnnotationCodes(sub);
              if (getInterrogative(codings).isPresent()) {
                addAsQuestion(sub, dmn, rootGroup, codings);
              } else {
                QuestionnaireItemComponent subGroup = new QuestionnaireItemComponent()
                    .setType(QuestionnaireItemType.GROUP)
                    .setText(sub.getName());
                rootGroup.addItem(subGroup);
                toQuestionnaireGroup(sub, dmn, subGroup);
              }
            }
        );
  }


  private Optional<Coding> getInterrogative(List<Coding> codings) {
    return codings.stream()
        .filter(cd -> "https://ontology.mayo.edu/taxonomies/ClinicalInterrogatives".equals(cd.getSystem()))
        .findFirst();
  }

  private void addAsQuestion(TDecision sub, TDefinitions dmn,
      QuestionnaireItemComponent rootGroup, List<Coding> codings) {
    QuestionnaireItemComponent question = new QuestionnaireItemComponent();
    question.setText(sub.getName());
    question.setLinkId(sub.getVariable().getName());

    Coding interr = getInterrogative(codings)
        .orElseThrow();
    codings.stream()
        .filter(cd -> cd != interr)
        .forEach(question::addCode);

    mapDatatype(question, sub.getVariable().getTypeRef(), interr, dmn);

    rootGroup.addItem(question);
  }


  private List<Coding> getAnnotationCodes(TDRGElement input) {
    if (input.getExtensionElements() == null) {
      return Collections.emptyList();
    }
    List<Object> any = input.getExtensionElements().getAny();
    if (any.isEmpty()) {
      return Collections.emptyList();
    }

    List<Coding> codings = new ArrayList<>();
    any.stream()
        .flatMap(StreamUtil.filterAs(Annotation.class))
        .forEach(ann -> {
          String url = ann.getRef().getResourceId().toString();
          String label = ann.getRef().getLabel();
          Coding c = new Coding()
              .setCode(ann.getRef().getTag())
              .setSystem(NameUtils.removeTrailingPart(url))
              .setDisplay(label);
          codings.add(c);
        });

    return codings;
  }

  private void mapDatatype(
      QuestionnaireItemComponent question,
      String typeRef,
      Coding interr, TDefinitions dmn) {
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
          mapDatatype(question, def.getTypeRef(), interr, dmn);
        }
      } else {
        switch (interr.getCode().trim().toLowerCase()) {
          case "is":
            question.setType(QuestionnaireItemType.BOOLEAN);
            return;
          case "kind":
            question.setType(QuestionnaireItemType.OPENCHOICE);
            return;
          case "quantity":
            question.setType(QuestionnaireItemType.QUANTITY);
            return;
        }
        throw new UnsupportedOperationException("Unable to handle " + typeRef);
      }
    }
  }

  private Optional<TItemDefinition> getItemDefinition(String typeRef, TDefinitions dmn) {
    return dmn.getItemDefinition().stream()
        .filter(def -> def.getName().equals(typeRef))
        .findAny();
  }


  private TDecision resolveDecision(String decisionHref, TDefinitions dmn) {
    return dmn.getDrgElement().stream()
        .map(JAXBElement::getValue)
        .flatMap(StreamUtil.filterAs(TDecision.class))
        .filter(dec -> dec.getId().contains(URI.create(decisionHref).getFragment()))
        .findFirst()
        .orElseThrow();
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


}
