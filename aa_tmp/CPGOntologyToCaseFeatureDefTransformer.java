package cpg.covid19.ed.poc;

import ca.uhn.fhir.context.FhirContext;
import edu.mayo.kmdp.util.FileUtil;
import java.io.File;
import java.io.FileNotFoundException;
import org.hl7.fhir.r4.model.Expression;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.StructureDefinition;
import org.hl7.fhir.r4.model.StructureDefinition.StructureDefinitionKind;
import org.hl7.fhir.r4.model.StructureDefinition.TypeDerivationRule;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class CPGOntologyToCaseFeatureDefTransformer extends CPGOntologyToDataTransformer {

  static File caseFeats = new File("C:\\Users\\M123110\\Desktop\\CovidCPG\\fhir\\caseFeatures");

  public static void main(String... args)
      throws FileNotFoundException, OWLOntologyCreationException {

    new CPGOntologyToCaseFeatureDefTransformer().run();
  }

  protected CPGOntologyToCaseFeatureDefTransformer() throws OWLOntologyCreationException {
    super();
    caseFeats.mkdirs();
  }

  protected void run() {
    generateCaseFeatureDefinitions(ontology);
  }

  private void generateCaseFeatureDefinitions(OWLOntology onto) {
     onto.classesInSignature()
        .filter(k -> k.getIRI().toString().contains("/covid"))
        .forEach(k -> {
          toCaseFeature(k, onto, onto.getOWLOntologyManager().getOWLDataFactory());
        });

  }


  private void toCaseFeature(OWLClass k, OWLOntology onto, OWLDataFactory df) {
    String kLabel = getLabel(k,onto, df);
    String kResource = mapToResource(getParent(k,onto));
    OWLClass kFocus = findFocus(k,onto);

    StructureDefinition sd = new StructureDefinition();
    sd.setName(kLabel);
    sd.setKind(StructureDefinitionKind.RESOURCE);
    sd.setAbstract(false);
    sd.setDerivation(TypeDerivationRule.CONSTRAINT);
    sd.setBaseDefinition(kResource);

    Expression expr = new Expression();
    expr.setExpression(kLabel);
    expr.setName(kLabel);
    expr.setLanguage("text/cql");
    sd.addExtension("http://build.fhir.org/ig/HL7/cqf-recommendations/StructureDefinition/cpg-inferenceExpression",
        expr);

    String json = FhirContext.forR4().newJsonParser().setPrettyPrint(true).encodeResourceToString(sd);
    FileUtil.write(json.getBytes(), new File(caseFeats,kLabel+".json"));
  }


}
