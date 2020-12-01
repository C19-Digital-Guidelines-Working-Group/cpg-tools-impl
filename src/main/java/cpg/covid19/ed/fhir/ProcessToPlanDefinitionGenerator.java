package cpg.covid19.ed.fhir;

import static cpg.covid19.ed.cql.CQLRetrievesGenerator.getDefaultLiftName;
import static cpg.covid19.ed.cql.CQLRetrievesGenerator.getDefaultRetrieveName;
import static cpg.util.fhir.IOUtil.readCMMNModels;
import static cpg.util.fhir.IOUtil.readDMNModels;
import static cpg.util.fhir.IOUtil.save;
import static org.omg.spec.api4kp._20200801.AbstractCarrier.codedRep;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.FHIR_STU3;

import cpg.covid19.ed.AbstractOntologyDrivenGenerator;
import edu.mayo.kmdp.language.translators.cmmn.v1_1.r4.CmmnToPlanDefR4Translator;
import edu.mayo.kmdp.language.translators.dmn.v1_2.r4.DmnToPlanDefR4Translator;
import edu.mayo.kmdp.util.NameUtils;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hl7.fhir.r4.model.CodeType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DataRequirement;
import org.hl7.fhir.r4.model.Expression;
import org.hl7.fhir.r4.model.PlanDefinition;
import org.hl7.fhir.r4.model.PlanDefinition.PlanDefinitionActionComponent;
import org.hl7.fhir.r4.model.PlanDefinition.PlanDefinitionActionDynamicValueComponent;
import org.omg.spec.api4kp._20200801.services.KnowledgeCarrier;

public class ProcessToPlanDefinitionGenerator {

  DmnToPlanDefR4Translator dmnTranslator = new DmnToPlanDefR4Translator();
  CmmnToPlanDefR4Translator cmmnTranslator = new CmmnToPlanDefR4Translator();

  Path glossaryPath;
  CPGDataRequirementWeaver weaver;


  public ProcessToPlanDefinitionGenerator(Path glossaryPath) {
    this.glossaryPath = glossaryPath;
    weaver = new CPGDataRequirementWeaver(glossaryPath);
  }

  public void run(Path dmnFolder, Path planDefFolder) {
    List<KnowledgeCarrier> cmmnKCs = readCMMNModels(dmnFolder);

    cmmnKCs.forEach(kc ->
        cmmnTranslator.applyTransrepresent(kc, codedRep(FHIR_STU3), null)
            .flatOpt(ans -> ans.as(PlanDefinition.class))
            .map(this::annotate)
            .ifPresent(pd -> save(pd, planDefFolder.resolve(pd.getName() + ".planDef.r4.json")))
    );

    List<KnowledgeCarrier> dmnKCs = readDMNModels(dmnFolder);

    dmnKCs.forEach(kc ->
        dmnTranslator.applyTransrepresent(kc, codedRep(FHIR_STU3), null)
            .flatOpt(ans -> ans.as(PlanDefinition.class))
            .map(pd -> weaver.weave(pd))
            .map(this::annotate)
            .ifPresent(pd -> save(pd, planDefFolder.resolve(pd.getName() + ".planDef.r4.json")))
    );

  }

  private PlanDefinition annotate(PlanDefinition pd) {
    pd.addExtension(
        "http://build.fhir.org/ig/HL7/cqf-recommendations/StructureDefinition/cpg-knowledgeCapability",
        new CodeType("executable"));
    pd.addExtension(
        "http://build.fhir.org/ig/HL7/cqf-recommendations/StructureDefinition/cpg-knowledgeRepresentationLevel",
        new CodeType("execbutable"));
    return pd;
  }

  private class CPGDataRequirementWeaver {

    private Collection<DataRequirement> dataDefs;

    public CPGDataRequirementWeaver(Path glossaryPath) {
      dataDefs = new CPGDataRequirementGenerator().load(glossaryPath);
    }

    public PlanDefinition weave(PlanDefinition pd) {
      actStreamAll(pd.getAction())
          .forEach(this::weaveAction);
      return pd;
    }

    private void weaveAction(PlanDefinitionActionComponent act) {
      Optional<Coding> interrogative = lookupInterrogative(act);
      interrogative.ifPresent(
          coding -> lookupClinical(act).forEach(cd -> weaveExpressions(act, cd, coding)));
    }

    private void weaveExpressions(PlanDefinitionActionComponent act, Coding cd, Coding interrog) {
      if (dataDefs.stream().noneMatch(
          dr -> dr.getCodeFilterFirstRep().getCodeFirstRep().getCode().equals(cd.getCode()))) {
        throw new IllegalStateException();
      }

      dataDefs.stream()
          .filter(
          dr -> dr.getCodeFilterFirstRep().getCodeFirstRep().getCode().equals(cd.getCode()))
          .forEach(dr -> weaveDataRequirement(act, dr, interrog));

    }

    private void weaveDataRequirement(PlanDefinitionActionComponent act, DataRequirement dr, Coding interrog) {
      act.addInput(dr);

      String baseExpr = getDefaultRetrieveName(
          dr.getCodeFilterFirstRep().getCodeFirstRep().getDisplay(),
          NameUtils.getTrailingPart(dr.getProfile().get(0).asStringValue()));
      act.addDynamicValue(new PlanDefinitionActionDynamicValueComponent()
          .setPath("input")
          .setExpression(new Expression()
              .setLanguage("text/cql-expressions")
              .setExpression(baseExpr))
      );

      String liftExpr = getDefaultLiftName(
          baseExpr,
          interrog.getDisplay());
      act.addDynamicValue(new PlanDefinitionActionDynamicValueComponent()
          .setPath("output")
          .setExpression(new Expression()
              .setLanguage("text/cql-expressions")
              .setExpression(liftExpr))
      );
    }

    private Optional<Coding> lookupInterrogative(PlanDefinitionActionComponent act) {
      return act.getCode().stream()
          .map(CodeableConcept::getCodingFirstRep)
          .filter(cd -> cd.getSystem().contains("https://ontology.mayo.edu/taxonomies/ClinicalInterrogatives"))
          .findFirst();
    }

    private List<Coding> lookupClinical(PlanDefinitionActionComponent act) {
      return act.getCode().stream()
          .map(CodeableConcept::getCodingFirstRep)
          .filter(cd -> cd.getSystem().equals(AbstractOntologyDrivenGenerator.C19ED_TAXONOMY_NS))
          .collect(Collectors.toList());
    }

    private Stream<PlanDefinitionActionComponent> actStreamAll(
        Collection<PlanDefinitionActionComponent> root) {
      return root.stream()
          .flatMap(this::actStream);
    }

    private Stream<PlanDefinitionActionComponent> actStream(PlanDefinitionActionComponent root) {
      return Stream.concat(Stream.of(root),
          root.getAction().stream().flatMap(this::actStream));
    }
  }

}
