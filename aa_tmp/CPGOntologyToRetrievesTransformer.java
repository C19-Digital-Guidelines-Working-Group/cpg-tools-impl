package cpg.covid19.ed.poc;

import edu.mayo.kmdp.util.FileUtil;
import java.io.File;
import java.io.FileNotFoundException;
import org.apache.jena.vocabulary.SKOS;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.search.EntitySearcher;

public class CPGOntologyToRetrievesTransformer extends CPGOntologyToDataTransformer {

  static File retrieves = new File("C:\\Users\\M123110\\Desktop\\CovidCPG\\fhir\\retrieves.cql");

  public static void main(String... args)
      throws FileNotFoundException, OWLOntologyCreationException {

    new CPGOntologyToRetrievesTransformer().run();
  }

  protected CPGOntologyToRetrievesTransformer() throws OWLOntologyCreationException {
    super();
  }

  protected void run() {
    generateRetrieveLibrary(ontology);
  }

  protected void generateRetrieveLibrary(OWLOntology onto) {
    StringBuilder sb = new StringBuilder();
    sb.append("library COVID19_ED version '0.0.1'\n\n");
    sb.append("using FHIR \n\n");
    sb.append("codesystem \"SNOMED:2020\": 'http://snomed.info/sct'\n\n");

    sb.append(" code \"TODO\": \n"
        + "\t '00000' from \"SNOMED:2020\"\n\n");

    StringBuilder cdb = new StringBuilder();
    StringBuilder rtb = new StringBuilder();
    StringBuilder pcb = new StringBuilder();

    OWLDataFactory df = onto.getOWLOntologyManager().getOWLDataFactory();

    onto.individualsInSignature()
        .filter(k -> k.getIRI().toString().contains("/covid"))
        .filter(k -> ! isPropositional(k, onto, df))
        .forEach(ind -> {
          toRetrieve(ind, onto, df, cdb, rtb);
        });

    onto.individualsInSignature()
        .filter(k -> k.getIRI().toString().contains("/covid"))
        .filter(k -> isPropositional(k, onto, df))
        .forEach(ind -> {
          toPropositionalExpression(ind, onto, df, pcb);
        });

    sb.append(cdb.toString());
    sb.append("context Patient \n\n");
    sb.append(rtb.toString());
    sb.append(pcb.toString());

    System.out.println(sb);
    FileUtil.write(sb.toString(), retrieves);
  }

  private void toPropositionalExpression(OWLNamedIndividual ind, OWLOntology onto,
      OWLDataFactory df, StringBuilder pcb) {
    String pcLabel = getLabel(ind, onto, df);
    OWLNamedIndividual parent = getBroader(ind, onto, df);
    String broaderLabel = getLabel(parent, onto, df);
    pcb.append("define \"" + pcLabel + "\" : \n\t");

    if (pcLabel.contains("- Is")) {
        pcb.append("exists ( \"" + broaderLabel + "\" )\n\n" );
    }
    if (pcLabel.contains("- Value")) {
      pcb.append("\"" + broaderLabel + "\".value as Quantity\n\n" );
    }
    if (pcLabel.contains("- Kind")) {
      pcb.append("\"" + broaderLabel + "\".code \n\n" );
    }
  }

  private OWLNamedIndividual getBroader(OWLNamedIndividual ind, OWLOntology onto, OWLDataFactory df) {
    return EntitySearcher.getObjectPropertyValues(ind, onto)
        .get(df.getOWLObjectProperty(IRI.create(SKOS.broader.getURI())))
        .stream()
        .filter(OWLIndividual::isNamed)
        .map(OWLNamedIndividual.class::cast)
        .findFirst()
        .orElseThrow();
  }

  private boolean isPropositional(OWLNamedIndividual k, OWLOntology onto,
      OWLDataFactory df) {
    return EntitySearcher.getObjectPropertyValues(k, onto)
        .containsKey(df.getOWLObjectProperty(IRI.create(SKOS.broader.getURI())));
  }

}
