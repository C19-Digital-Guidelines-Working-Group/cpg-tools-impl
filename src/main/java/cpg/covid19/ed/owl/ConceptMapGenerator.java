package cpg.covid19.ed.owl;

import static cpg.util.fhir.IOUtil.save;

import cpg.covid19.ed.AbstractOntologyDrivenGenerator;
import cpg.util.fhir.CPGUtil;
import cpg.util.fhir.CPGUtil.KnownCodeSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import org.hl7.fhir.r4.model.ConceptMap;
import org.hl7.fhir.r4.model.ConceptMap.ConceptMapGroupComponent;
import org.hl7.fhir.r4.model.ConceptMap.SourceElementComponent;
import org.hl7.fhir.r4.model.Enumerations.ConceptMapEquivalence;
import org.omg.spec.api4kp._20200801.id.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConceptMapGenerator extends AbstractOntologyDrivenGenerator {

  Logger logger = LoggerFactory.getLogger(ConceptMapGenerator.class);

  public void run(Path srcPath, Path tgtPath) {
    logger.info("Generate Concept Mappings from Data Glossary...");
    List<SemanticDataElementInfo> infos = readDataElements(srcPath);

    ConceptMap conceptMap = toConceptMap(infos);

    save(conceptMap, tgtPath);
  }

  private ConceptMap toConceptMap(
      final List<SemanticDataElementInfo> semanticDataElementInfos) {

    ConceptMap conceptMap = new ConceptMap();
    conceptMap.setName("Covid19_ED_ConceptMap");
    conceptMap.setTitle("Covid-19 ED Concept Map");

    addMappings(conceptMap,
        semanticDataElementInfos,
        KnownCodeSystems.SNOMED,
        sdi -> Stream.ofNullable(sdi.focalConcept));

    addMappings(conceptMap,
        semanticDataElementInfos,
        KnownCodeSystems.CPT,
        sdi -> sdi.cpt.stream());
    addMappings(conceptMap,
        semanticDataElementInfos,
        KnownCodeSystems.HCPCS,
        sdi -> sdi.hcpcs.stream());

    addMappings(conceptMap,
        semanticDataElementInfos,
        KnownCodeSystems.ICD10CM,
        sdi -> sdi.icd10cm.stream());
    addMappings(conceptMap,
        semanticDataElementInfos,
        KnownCodeSystems.ICD10PCS,
        sdi -> sdi.icd10pcs.stream());

    addMappings(conceptMap,
        semanticDataElementInfos,
        KnownCodeSystems.LOINC,
        sdi -> sdi.loinc.stream());

    addMappings(conceptMap,
        semanticDataElementInfos,
        KnownCodeSystems.RxNORM,
        sdi -> sdi.rxNorm.stream());

    return conceptMap;
  }

  protected void addMappings(
      ConceptMap conceptMap,
      List<SemanticDataElementInfo> semanticDataElementInfos,
      KnownCodeSystems targetCodeSystem,
      Function<SemanticDataElementInfo, Stream<Term>> targetMapping) {

    ConceptMapGroupComponent grp = conceptMap.addGroup()
        .setSource(C19ED_TAXONOMY_NS)
        .setTarget(CPGUtil.getCodeSystem(targetCodeSystem.code));

    semanticDataElementInfos.forEach(sdi -> {
          if (targetMapping.apply(sdi).findAny().isPresent()) {
            Term cso = Term.newTerm(getSituationUri(sdi));
            SourceElementComponent mapSrc = grp.addElement()
                .setCode(cso.getTag())
                .setDisplay(sdi.label());
            targetMapping.apply(sdi).forEach(cpt ->
                mapSrc.addTarget()
                    .setCode(cpt.getTag())
                    .setEquivalence(ConceptMapEquivalence.NARROWER));
          }
        }
    );
  }
}
