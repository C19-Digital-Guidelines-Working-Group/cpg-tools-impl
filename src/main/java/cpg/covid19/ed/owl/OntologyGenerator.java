package cpg.covid19.ed.owl;

import edu.mayo.kmdp.terms.TermsHelper;
import edu.mayo.kmdp.util.NameUtils;
import edu.mayo.kmdp.util.Util;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
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
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
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

public class OntologyGenerator extends AbstractOntologyDrivenGenerator {

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

  //TODO FIXME - Replace after publishing the ontology of Interrogatives
  enum Interrogatives {
    IS("Is", " - Present"),
    KIND_OF("KindOf", " - Kind Of"),
    VALUE_OF("ValueOf", " - Value Of");

    public final String code;
    public final String label;

    Interrogatives(String code, String label) {
      this.code = code;
      this.label = label;
    }
  }

  public void run(Path srcPath, Path tgtPath) {
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
    declareAsSnomed(TermsHelper.sct("Supected","415684004"),onto,df);
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

    pun(label, Interrogatives.IS, csUri, onto, df, concepts);

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

    pun(label, Interrogatives.VALUE_OF, csUri, onto, df, concepts);

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

    pun(label, Interrogatives.IS, csUri, onto, df, concepts);

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

    pun(label, Interrogatives.KIND_OF, csUri, onto, df, concepts);

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
    onto.addAxiom(
        df.getOWLAnnotationAssertionAxiom(
            csoClass.getIRI(),
            df.getOWLAnnotation(
                df.getOWLAnnotationProperty(RDFS.label.getURI()),
                df.getOWLLiteral(label)
            ))
    );

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


  private URI pun(String label, Interrogatives suffix, URI csoUri,
      OWLOntology onto, OWLDataFactory df, Map<URI, String> concepts) {

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
    onto.addAxiom(
        df.getOWLAnnotationAssertionAxiom(
            csoConcept.getIRI(),
            df.getOWLAnnotation(
                df.getOWLAnnotationProperty(RDFS.label.getURI()),
                df.getOWLLiteral(csoLabel)
            ))
    );
    onto.addAxiom(
        df.getOWLDataPropertyAssertionAxiom(
            df.getOWLDataProperty(SKOS.notation.getURI()),
            csoConcept,
            df.getOWLLiteral(csoUUID.toString()))
    );

    onto.addAxiom(df.getOWLClassAssertionAxiom(
        df.getOWLClass(IRI.create(
            SKOS.Concept.getURI())),
        csoConcept));

    String pcoLabel = label + suffix.label;
    OWLNamedIndividual pcoConcept = df
        .getOWLNamedIndividual(
            IRI.create(URI.create(C19ED_TAXONOMY_NS + csoUUID + "/" + suffix.code)));
    concepts.put(pcoConcept.getIRI().toURI(), pcoLabel);

    onto.addAxiom(
        df.getOWLDeclarationAxiom(pcoConcept)
    );
    onto.addAxiom(
        df.getOWLAnnotationAssertionAxiom(
            pcoConcept.getIRI(),
            df.getOWLAnnotation(
                df.getOWLAnnotationProperty(RDFS.label.getURI()),
                df.getOWLLiteral(pcoLabel)
            ))
    );
    onto.addAxiom(df.getOWLObjectPropertyAssertionAxiom(
        df.getOWLObjectProperty(IRI.create(SKOS.broader.getURI())),
        pcoConcept,
        csoConcept
    ));

    onto.addAxiom(
        df.getOWLDataPropertyAssertionAxiom(
            df.getOWLDataProperty(SKOS.notation.getURI()),
            pcoConcept,
            df.getOWLLiteral(csoUUID.toString() + ":" + suffix.code))
    );

    onto.addAxiom(df.getOWLClassAssertionAxiom(
        df.getOWLClass(IRI.create(
            SKOS.Concept.getURI())),
        pcoConcept));
    return pcoConcept.getIRI().toURI();
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
    onto.addAxiom(
        df.getOWLAnnotationAssertionAxiom(
            sctClass.getIRI(),
            df.getOWLAnnotation(
                df.getOWLAnnotationProperty(RDFS.label.getURI()),
                df.getOWLLiteral(sct.getLabel())
            ))
    );
  }


  private void saveOntology(OWLOntology onto, Path tgtPath)
      throws IOException, OWLOntologyStorageException {
    if (!Files.exists(tgtPath.getParent())) {
      Files.createDirectories(tgtPath.getParent());
    }
    try (OutputStream fos = Files.newOutputStream(tgtPath)) {
      onto.saveOntology(new RDFXMLDocumentFormat(), fos);
    }
  }
}
