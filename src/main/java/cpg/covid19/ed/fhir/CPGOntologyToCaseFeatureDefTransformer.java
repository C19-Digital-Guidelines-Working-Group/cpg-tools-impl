package cpg.covid19.ed.fhir;

import static cpg.covid19.ed.cql.CQLRetrieveGenerator.getDefaultRetrieveName;

import cpg.covid19.ed.AbstractOntologyDrivenGenerator;
import cpg.covid19.ed.cql.CQLConceptsGenerator;
import cpg.util.fhir.IOUtil;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.hl7.fhir.r4.model.Expression;
import org.hl7.fhir.r4.model.StructureDefinition;
import org.hl7.fhir.r4.model.StructureDefinition.StructureDefinitionKind;
import org.hl7.fhir.r4.model.StructureDefinition.TypeDerivationRule;

public class CPGOntologyToCaseFeatureDefTransformer extends AbstractOntologyDrivenGenerator {


  public void run(Path srcPath, Path tgtPath) {
    List<SemanticDataElementInfo> infos = readDataElements(srcPath);

    infos.stream()
        .flatMap(this::toCaseFeatures)
        .forEach(sd -> IOUtil.save(sd, tgtPath.resolve(sd.getName() + ".structDef.r4.json")));

  }

  private Stream<StructureDefinition> toCaseFeatures(SemanticDataElementInfo info) {
    return info.fhirResources.stream()
        .map(res -> toCaseFeature(info, res));
  }


  private StructureDefinition toCaseFeature(SemanticDataElementInfo info, String kResource) {
    String kLabel = info.label();

    StructureDefinition sd = new StructureDefinition();
    sd.setName(kLabel);
    sd.setKind(StructureDefinitionKind.RESOURCE);
    sd.setAbstract(false);
    sd.setDerivation(TypeDerivationRule.CONSTRAINT);
    sd.setBaseDefinition(kResource);

    Expression expr = new Expression();
    expr.setExpression(getDefaultRetrieveName(kLabel,kResource));
    expr.setName(kLabel);
    expr.setLanguage("text/cql");

    sd.addExtension("http://build.fhir.org/ig/HL7/cqf-recommendations/StructureDefinition/cpg-inferenceExpression",
        expr);

    return sd;
  }

}
