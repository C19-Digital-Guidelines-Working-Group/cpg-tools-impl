package cpg.covid19.ed;

import static cpg.util.CPGDistPublisher.clearAll;
import static cpg.util.CPGDistPublisher.publish;

import cpg.covid19.ed.cql.CQLPreMappedConceptsGenerator;
import cpg.covid19.ed.cql.CQLConceptsGenerator;
import cpg.covid19.ed.cql.CQLRetrieveGenerator;
import cpg.covid19.ed.cql.DMNToCQLInferenceTransformer;
import cpg.covid19.ed.cql.ItemDefinitionToCQLStructTransformer;
import cpg.covid19.ed.fhir.CPGOntologyToCaseFeatureDefTransformer;
import cpg.covid19.ed.fhir.DMNToQuestionnaireGenerator;
import cpg.covid19.ed.fhir.ProcessToPlanDefinitionGenerator;
import cpg.covid19.ed.owl.ConceptMapGenerator;
import cpg.covid19.ed.owl.ConceptSchemeGenerator;
import cpg.covid19.ed.owl.OntologyGenerator;
import cpg.covid19.ed.vendor.tt.TTAcceleratorGenerator;
import cpg.util.CPGAssetDownloader;
import java.io.IOException;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class Main {

  static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static final Path root =
      Path.of("C:/Users/M123110/Projects/MEA/cpg-tools-impl");

  public static final Path sourcePath = root.resolve(
      Path.of("src","main", "resources"));
  public static final Path tgtPath = root.resolve(
      Path.of("target", "generated-resources"));
  public static final Path distPath = root.resolve(
      Path.of("dist"));

  public static final Path relativeScope =
      Path.of("opencpg", "covid19", "ed");

  public static final Path bpmPath =
      sourcePath.resolve(Path.of("opencpg","covid19", "ed", "bpm"));

  public static final Path dataElementSheet =
      sourcePath.resolve(relativeScope.resolve(Path.of("owl", "Data Elements.xlsx")));

  public static final Path covidOWLPath =
      tgtPath.resolve(relativeScope.resolve(Path.of("owl", "covid19.owl")));

  public static final Path fhirPath =
      tgtPath.resolve(relativeScope.resolve(Path.of("fhir")));
  public static final Path fhirDataReqPath =
      fhirPath.resolve("casefeatures");
  public static final Path fhirTermsPath =
      fhirPath.resolve("terminology");
  public static final Path fhirQuestionnairePath =
      fhirPath.resolve("questionnaire");
  public static final Path planDefPath =
      fhirPath.resolve("pathway");

  public static final Path cqlPath =
      tgtPath.resolve(relativeScope.resolve(Path.of("cql")));

  /**
   * Generators
   *  - Excel "Glossary" to OWL 'Clinical Situation' Ontology
   *  - Ontology to FHIR CodeSystem
   *  - Glossary to FHIR ConceptMap
   *  - Glossary to Trisotech "Accelerator" (proprietary code system format)
   */
  public static class OntologyMain {
    public static void main(String... args) {

      new OntologyGenerator().run(dataElementSheet, covidOWLPath);
      new ConceptSchemeGenerator().run(covidOWLPath, fhirTermsPath);
      new ConceptMapGenerator().run(dataElementSheet, fhirTermsPath.resolve("conceptMap.r4.json"));
      new TTAcceleratorGenerator().run(dataElementSheet, fhirTermsPath.resolve("accelerator.json"));

    }
  }

  /**
   * Downloads, and caches, the ED BPM+ models from a Trisotech instance
   */
  public static class BPMPlusMain {
    public static void main(String... args) {
      ConfigurableApplicationContext context = SpringApplication.run(CPGAssetDownloader.class, args);
      SpringApplication.exit(context, () -> 0);
    }
  }

  /**
   * Generates:
   * - Glossary to CQL 'Concepts/Code/Valueset' header (ontology mediated)
   * - Glossary to CQL 'Concepts/Code/Valueset' header (pre-mapped to standard code systems)
   * - Glossary to CQL 'Retrieve' + Lifting operators (Exists, Last, .value, etc)
   * - Glossary to FHIR DataElement/Definitions (TODO for eCase Report)
   */
  public static class GlossaryMain {
    public static void main(String... args) {

      new CQLConceptsGenerator().run(dataElementSheet, cqlPath);
      new CQLPreMappedConceptsGenerator().run(dataElementSheet, cqlPath);
      new CQLRetrieveGenerator().run(dataElementSheet, cqlPath);
      new CPGOntologyToCaseFeatureDefTransformer().run(dataElementSheet, fhirDataReqPath);

    }
  }

  /**
   * Generates:
   * - Case Model to FHIR Plan Definition
   * - Decision Models to FHIR Plan Definition
   * - Decision Models to FHIR Questionnaire
   */
  public static class BPMtoFHIRMain {
    public static void main(String... args) {

      new ProcessToPlanDefinitionGenerator(dataElementSheet).run(bpmPath,planDefPath);
      new DMNToQuestionnaireGenerator().run(bpmPath,fhirQuestionnairePath);
    }
  }

  /**
   * Generates:
   * - "Situational Data" (DMN) to CQL 'Tuples'
   * - DMN (FEEL) to CQL Inferences for Case Features
   */
  public static class BPMtoCQLMain {
    public static void main(String... args) {

      new ItemDefinitionToCQLStructTransformer("Situational Data Definitions.dmn.xml", bpmPath)
          .run(dataElementSheet,cqlPath);
      //new DMNToCQLInferenceTransformer().run(bpmPath,cqlPath);
    }
  }

  public static class Publisher {
    public static void main(String... args) throws IOException {
      clearAll(distPath);
      publish(sourcePath.resolve("opencpg"),
          tgtPath.resolve("opencpg"),
          distPath);
    }
  }




}
