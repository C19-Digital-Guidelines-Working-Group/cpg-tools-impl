package cpg.covid19.ed.owl;

import static cpg.util.fhir.IOUtil.saveOntology;
import static edu.mayo.ontology.taxonomies.clinicalinterrogatives.ClinicalInterrogativeSeries.Is;
import static edu.mayo.ontology.taxonomies.clinicalinterrogatives.ClinicalInterrogativeSeries.Kind_Of;
import static edu.mayo.ontology.taxonomies.clinicalinterrogatives.ClinicalInterrogativeSeries.Quantity_Of;

import cpg.covid19.ed.AbstractOntologyDrivenGenerator;
import edu.mayo.kmdp.terms.TermsHelper;
import edu.mayo.kmdp.util.NameUtils;
import edu.mayo.kmdp.util.Util;
import edu.mayo.ontology.taxonomies.clinicalinterrogatives.ClinicalInterrogativeSeries;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.SKOS;
import org.omg.spec.api4kp._20200801.id.Term;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OntologyGenerator extends AbstractOntologyDrivenGenerator {

  Logger logger = LoggerFactory.getLogger(OntologyGenerator.class);

  private static final String SCT_NS = "http://snomed.info/id/";
  private static final String SCT_ASSOC_FINDING = SCT_NS + "246090004";
  private static final String SCT_ASSOC_FINDING_CTX = SCT_NS + "408729009";
  private static final String SCT_ASSOC_PROCEDR = SCT_NS + "363589002";
  private static final String SCT_KNOWN = SCT_NS + "36692007";
  private static final String SCT_SUSPECTED = SCT_NS + "415684004";

  private static final String CSO_NAMESPACE = "http://ontology.mayo.edu/ontologies/clinicalsituationontology/";
  // parent class used to group non-situation concepts
  private static final String CSO_CLINICAL_ENTITY =
      CSO_NAMESPACE + "1534a5c8-8c5b-4312-9746-6511c1777fc2";
  private static final String CSO_CURRENT_OBS =
      CSO_NAMESPACE + "2a1effcd-8ecb-487d-b203-65dd91f9b274";
  private static final String CSO_PRIOR_PROC =
      CSO_NAMESPACE + "fcb7205f-af58-466e-90d3-d21f67f7a94e";
  private static final String CSO_HAS_PROBLEM =
      CSO_NAMESPACE + "e777c47a-246c-4963-9fa1-2bf9d8d91705";
  private static final String CSO_ON_MED = CSO_NAMESPACE + "c744a1f8-ae51-49ab-b4b2-5fc79f6fb480";
  private static final String CSO_MOST_REC_LAB =
      CSO_NAMESPACE + "897c9bc1-3578-4d01-b1f5-74e8b29a65d7";
  private static final String CSO_PATIENT =
      CSO_NAMESPACE + "40340021-52fb-42a0-97b8-3fb4af6c2834";

  public void run(Path srcPath, Path tgtPath) {
    logger.info("Generate OWL Ontology from Data Glossary...");
    List<SemanticDataElementInfo> elements = readDataElements(srcPath);
    try {
      createOntology(elements, tgtPath);
    } catch (OWLOntologyCreationException | IOException | OWLOntologyStorageException e) {
      e.printStackTrace();
    }
  }

  private void createOntology(
      List<SemanticDataElementInfo> elements, Path tgtPath)
      throws OWLOntologyCreationException, IOException, OWLOntologyStorageException {

    OWLOntology onto = OWLManager.createOWLOntologyManager().createOntology(
        new OWLOntologyID(
            IRI.create(C19ED_NS),
            IRI.create(C19ED_VERSION)));
    OWLDataFactory df = onto.getOWLOntologyManager().getOWLDataFactory();

    initializeOntology(onto, df);

    populateOntology(elements, onto, df);

    saveOntology(onto, tgtPath);
  }

  private void initializeOntology(OWLOntology onto, OWLDataFactory df) {
    onto.applyChange(new AddAxiom(onto,
        df.getOWLAnnotationAssertionAxiom(
            onto.getOntologyID().getOntologyIRI().orElseThrow(),
            df.getOWLAnnotation(df.getOWLAnnotationProperty(RDFS.label.getURI()),
                df.getOWLLiteral("Covid19 Emergency Department - Clinical Situation Ontology")))));
    onto.applyChange(new AddAxiom(onto,
        df.getOWLAnnotationAssertionAxiom(
            onto.getOntologyID().getOntologyIRI().orElseThrow(),
            df.getOWLAnnotation(df.getOWLAnnotationProperty(DCTerms.license.getURI()),
                df.getOWLLiteral("http://opensource.org/licenses/MIT",
                    OWL2Datatype.XSD_ANY_URI)))));
    onto.applyChange(new AddImport(onto,
        df.getOWLImportsDeclaration(IRI.create(URI.create(CSO_NAMESPACE)))));

    addCommonContent(onto, df);
  }

  private void addCommonContent(OWLOntology onto, OWLDataFactory df) {
    declareAsSnomed(TermsHelper.sct("Suspected","415684004"),onto,df);
  }


  private void populateOntology(
      List<SemanticDataElementInfo> dataElementInfos,
      OWLOntology onto, OWLDataFactory df) {

    Map<URI, String> concepts = new HashMap<>();
    for (SemanticDataElementInfo concept : dataElementInfos) {
      ConceptKind kind = concept.kind;

      if (concept.focalConcept == null) {
        System.err.println("WARN : No focal concept defined for " + concept.dataElement);
        continue;
      }
      declareAsSnomed(concept.focalConcept, onto, df);

      switch (kind) {
        case PROBLEM:
          declareAsHavingProblem(concept, onto, df, concepts);
          break;
        case PROCEDURE:
          declareAsPriorProc(concept, onto, df, concepts);
          break;
        case MEDICATION:
          declareAsOnMed(concept, onto, df, concepts);
          break;
        case OBSERVABLE:
        case LAB:
          declareAsObs(concept, onto, df, concepts);
          break;
        case DEMOGRAPHICS:
          declareAsDemographics(concept, onto, df, concepts);
          break;
        default:
          throw new IllegalStateException();
      }
    }
  }


  private void declareAsOnMed(SemanticDataElementInfo term, OWLOntology onto,
      OWLDataFactory df, Map<URI, String> concepts) {
    String label = "On " + term.dataElement;
    URI csUri = declareAsCS(
        label,
        term,
        CSO_ON_MED,
        SCT_ASSOC_PROCEDR,
        onto,
        df,
        t -> df.getOWLObjectIntersectionOf(
            df.getOWLClass(SCT_NS + "416608005"),
            df.getOWLObjectSomeValuesFrom(
                df.getOWLObjectProperty(SCT_NS + "363701004"),
                df.getOWLClass(IRI.create(term.focalConcept.getConceptId()))
            )
        ));

    pun(label, Is, csUri, onto, df, concepts, term);

    concepts.put(csUri, label);
  }

  private void declareAsObs(SemanticDataElementInfo term, OWLOntology onto,
      OWLDataFactory df, Map<URI, String> concepts) {

    boolean mostRecent = term.kind == ConceptKind.LAB;

    String label = term.label();
    URI csUri = declareAsCS(
        label,
        term,
        mostRecent ? CSO_MOST_REC_LAB : CSO_CURRENT_OBS,
        SCT_ASSOC_FINDING,
        onto, df);

    pun(label, Quantity_Of, csUri, onto, df, concepts, term);

    concepts.put(csUri, label);
  }


  private void declareAsDemographics(SemanticDataElementInfo term, OWLOntology onto,
      OWLDataFactory df, Map<URI, String> concepts) {

    String label = term.label();
    URI csUri = declareAsCS(
        label,
        term,
        CSO_PATIENT,
        SCT_ASSOC_FINDING,
        onto, df);

    pun(label, Kind_Of, csUri, onto, df, concepts, term);

    concepts.put(csUri, label);
  }

  private void declareAsHavingProblem(SemanticDataElementInfo term, OWLOntology onto,
      OWLDataFactory df, Map<URI, String> concepts) {
    String label = term.label();
    URI csUri = declareAsCS(
        label,
        term,
        CSO_HAS_PROBLEM,
        SCT_ASSOC_FINDING,
        onto,
        df
    );

    pun(label, Is, csUri, onto, df, concepts, term);

    concepts.put(csUri, label);
  }

  private void declareAsPriorProc(SemanticDataElementInfo term, OWLOntology onto,
      OWLDataFactory df, Map<URI, String> concepts) {
    String label = term.label();
    URI csUri = declareAsCS(
        label,
        term,
        CSO_PRIOR_PROC,
        SCT_ASSOC_PROCEDR,
        onto, df);

    pun(label, Kind_Of, csUri, onto, df, concepts, term);

    concepts.put(csUri, label);
  }

  private URI declareAsCS(String label, SemanticDataElementInfo sctCode,
      String csoParentUri, String sctParentFocalRel,
      OWLOntology onto, OWLDataFactory df) {
    return declareAsCS(
        label,
        sctCode,
        csoParentUri,
        sctParentFocalRel,
        onto,
        df,
        t -> df.getOWLClass(IRI.create(t.getConceptId())));
  }

  private URI declareAsCS(String label, SemanticDataElementInfo termInfo,
      String csoParentUri, String sctParentFocalRel,
      OWLOntology onto, OWLDataFactory df,
      Function<Term, OWLClassExpression> ranger) {

    OWLClass csoClass = df
        .getOWLClass(IRI.create(getSituationUri(termInfo)));

    onto.addAxiom(
        df.getOWLDeclarationAxiom(csoClass)
    );
    addLabel(csoClass.getIRI(), label, df, onto);


    Term focalConcept = termInfo.focalConcept;
    if (focalConcept != null) {
      OWLClassExpression sctClass = ranger.apply(focalConcept);
      onto.addAxiom(df.getOWLEquivalentClassesAxiom(
          csoClass,
          df.getOWLObjectIntersectionOf(
              df.getOWLClass(IRI.create(csoParentUri)),
              df.getOWLObjectSomeValuesFrom(
                  df.getOWLObjectProperty(IRI.create(sctParentFocalRel)),
                  sctClass
              ),
              df.getOWLObjectSomeValuesFrom(
                  df.getOWLObjectProperty(SCT_ASSOC_FINDING_CTX),
                  df.getOWLClass(Optional.ofNullable(termInfo.context)
                      .map(t -> t.getConceptId().toString())
                      .orElse(SCT_KNOWN)))
          )
      ));
    }
    onto.addAxiom(df.getOWLSubClassOfAxiom(
        csoClass,
        df.getOWLClass(IRI.create(csoParentUri))
    ));

    return csoClass.getIRI().toURI();
  }


  private URI pun(String label, ClinicalInterrogativeSeries suffix, URI csoUri,
      OWLOntology onto, OWLDataFactory df, Map<URI, String> concepts,
      SemanticDataElementInfo mappings) {

    UUID csoUUID = Util.ensureUUID(NameUtils.getTrailingPart(csoUri.toString()))
        .orElseThrow();

    String csoLabel = label + " (Descr. of)";

    OWLNamedIndividual csoConcept = df
        .getOWLNamedIndividual(IRI.create(URI.create(C19ED_TAXONOMY_NS + csoUUID)));
    concepts.put(csoConcept.getIRI().toURI(), csoLabel);

    onto.addAxiom(
        df.getOWLDeclarationAxiom(csoConcept)
    );
    onto.addAxiom(
        df.getOWLAnnotationAssertionAxiom(
            df.getOWLAnnotationProperty(IRI.create(OLEX_CONCEPTOF)),
            csoConcept.getIRI(),
            IRI.create(csoUri)
        )
    );
    addLabel(csoConcept.getIRI(), csoLabel, df, onto);

    onto.addAxiom(
        df.getOWLDataPropertyAssertionAxiom(
            df.getOWLDataProperty(SKOS.notation.getURI()),
            csoConcept,
            df.getOWLLiteral(csoUUID.toString()))
    );

    asSkosConcept(csoConcept, df, onto);

    String pcoLabel = label + suffix.getLabel();
    OWLNamedIndividual pcoConcept = df
        .getOWLNamedIndividual(
            IRI.create(URI.create(C19ED_TAXONOMY_NS + csoUUID + "/" + suffix.getTag())));
    concepts.put(pcoConcept.getIRI().toURI(), pcoLabel);

    onto.addAxiom(
        df.getOWLDeclarationAxiom(pcoConcept)
    );
    addLabel(pcoConcept.getIRI(), pcoLabel, df, onto);

    onto.addAxiom(df.getOWLObjectPropertyAssertionAxiom(
        df.getOWLObjectProperty(IRI.create(SKOS.broader.getURI())),
        pcoConcept,
        csoConcept
    ));

    onto.addAxiom(
        df.getOWLDataPropertyAssertionAxiom(
            df.getOWLDataProperty(SKOS.notation.getURI()),
            pcoConcept,
            df.getOWLLiteral(csoUUID.toString() + ":" + suffix.getTag()))
    );

    asSkosConcept(pcoConcept, df, onto);

    assertMappings(csoConcept, mappings, df, onto);

    return pcoConcept.getIRI().toURI();
  }

  private void asSkosConcept(OWLNamedIndividual concept, OWLDataFactory df, OWLOntology onto) {
    OWLNamedIndividual conceptScheme = df.getOWLNamedIndividual(IRI.create(C19ED_TAXONOMY_SCHEME));
    OWLNamedIndividual topConcept = df.getOWLNamedIndividual(IRI.create(C19ED_TAXONOMY_TOP));

    // These axioms should be asserted one-time
    onto.addAxiom(df.getOWLClassAssertionAxiom(
        df.getOWLClass(IRI.create(
            SKOS.ConceptScheme.getURI())),
        conceptScheme));
    addLabel(conceptScheme.getIRI(), "C19 ED Concept Scheme", df, onto);

    onto.addAxiom(df.getOWLClassAssertionAxiom(
        df.getOWLClass(IRI.create(
            SKOS.Concept.getURI())),
        topConcept));
    addLabel(topConcept.getIRI(), "C19 ED Concept", df, onto);


    onto.addAxiom(df.getOWLObjectPropertyAssertionAxiom(
        df.getOWLObjectProperty(IRI.create(SKOS.inScheme.getURI())),
        topConcept,
        conceptScheme));

    onto.addAxiom(df.getOWLObjectPropertyAssertionAxiom(
        df.getOWLObjectProperty(IRI.create(SKOS.hasTopConcept.getURI())),
        conceptScheme,
        topConcept));

    // These are per-individual concept

    onto.addAxiom(df.getOWLClassAssertionAxiom(
        df.getOWLClass(IRI.create(
            SKOS.Concept.getURI())),
        concept));

    onto.addAxiom(df.getOWLObjectPropertyAssertionAxiom(
        df.getOWLObjectProperty(IRI.create(SKOS.inScheme.getURI())),
        concept,
        conceptScheme));

    onto.addAxiom(df.getOWLObjectPropertyAssertionAxiom(
        df.getOWLObjectProperty(IRI.create(SKOS.broader.getURI())),
        concept,
        topConcept));

  }

  private void assertMappings(OWLNamedIndividual csoConcept, SemanticDataElementInfo mappings,
      OWLDataFactory df, OWLOntology onto) {
    mappings.allConcepts().forEach(trm ->
        onto.addAxiom(df.getOWLObjectPropertyAssertionAxiom(
            df.getOWLObjectProperty(IRI.create(SKOS.broadMatch.getURI())),
            csoConcept,
            df.getOWLNamedIndividual(IRI.create(trm.getResourceId()))
        ))
    );
    if (mappings.focalConcept != null) {
      onto.addAxiom(df.getOWLObjectPropertyAssertionAxiom(
          df.getOWLObjectProperty(IRI.create(SKOS.broadMatch.getURI())),
          csoConcept,
          df.getOWLNamedIndividual(IRI.create(mappings.focalConcept.getResourceId()))));
    }
  }


  private void declareAsSnomed(Term sct, OWLOntology onto,
      OWLDataFactory df) {
    OWLClass sctClass = df.getOWLClass(IRI.create(sct.getConceptId()));
    onto.addAxiom(
        df.getOWLDeclarationAxiom(sctClass)
    );
    onto.addAxiom(df.getOWLSubClassOfAxiom(
        sctClass,
        df.getOWLClass(IRI.create(
            CSO_CLINICAL_ENTITY))
    ));
    addLabel(sctClass.getIRI(),sct.getLabel(), df, onto);
  }

  private void addLabel(IRI entity, String label, OWLDataFactory df, OWLOntology onto) {
    onto.addAxiom(
        df.getOWLAnnotationAssertionAxiom(
            entity,
            df.getOWLAnnotation(
                df.getOWLAnnotationProperty(RDFS.label.getURI()),
                df.getOWLLiteral(label)
            )));
  }

}
