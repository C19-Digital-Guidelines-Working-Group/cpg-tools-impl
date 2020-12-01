package cpg.covid19.ed.fhir;

import cpg.covid19.ed.AbstractOntologyDrivenGenerator;
import cpg.util.fhir.IOUtil;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DataRequirement;
import org.hl7.fhir.r4.model.DataRequirement.SortDirection;
import org.hl7.fhir.r4.model.Duration;
import org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes;
import org.hl7.fhir.r4.model.PlanDefinition;
import org.omg.spec.api4kp._20200801.id.Term;

public class CPGDataRequirementGenerator extends AbstractOntologyDrivenGenerator {

  @Override
  public void run(Path srcPath, Path tgtPath) {
    Collection<DataRequirement> dataRequirements = load(srcPath);

    dataRequirements.forEach(dreq -> this.save(dreq, tgtPath));
  }

  public List<DataRequirement> load(Path srcPath) {
    List<SemanticDataElementInfo> infos = readDataElements(srcPath);

    return infos.stream()
        .flatMap(this::toDataRequirement)
        .collect(Collectors.toList());
  }



  private void save(DataRequirement dreq, Path tgtPath) {
    Coding cd = (Coding) dreq.getExtensionFirstRep().getValue();
    Path file = tgtPath.resolve(Path.of(
        cd.getDisplay() + " - " + dreq.getType() + ".r4.json"));

    PlanDefinition mockWrapper = new PlanDefinition();
    mockWrapper.addAction().addInput(dreq);

    IOUtil.save(mockWrapper, file);
  }

  private Stream<DataRequirement> toDataRequirement(
      final SemanticDataElementInfo semanticDataElementInfo) {
    return semanticDataElementInfo.fhirResources.stream()
        .flatMap(asResource -> toDataRequirement(semanticDataElementInfo, asResource.trim()));
  }

  private Stream<DataRequirement> toDataRequirement(
      final SemanticDataElementInfo semanticDataElementInfo, String resourceType) {
    DataRequirement dreq = new DataRequirement();

    FHIRAllTypes resource = FHIRAllTypes.fromCode(resourceType);

    dreq.setType(resource.toCode());
    dreq.addProfile("http://hl7.org/fhir/" + resource.toCode());

    Term cso = Term.newTerm(getSituationUri(semanticDataElementInfo));
    dreq.addExtension()
        .setUrl(OLEX_DENOTES)
        .setValue(new Coding()
            .setCode(cso.getTag())
            .setDisplay(semanticDataElementInfo.label())
            .setSystem(cso.getNamespaceUri().toString()));

    dreq.setSubject(new CodeableConcept()
        .addCoding(new Coding()
            .setCode(FHIRAllTypes.PATIENT.toCode())
            .setDisplay(FHIRAllTypes.PATIENT.getDisplay())));

    dreq.addCodeFilter().addCode(toCoding(cso, semanticDataElementInfo.label()));

    if (semanticDataElementInfo.kind != ConceptKind.DEMOGRAPHICS) {
//      semanticDataElementInfo.allConcepts()
//          .forEach(trm -> dreq.addCodeFilter().addCode(toCoding(trm)));

      String timeAttribute = getDefaultTimeFilter(resource);
      if (semanticDataElementInfo.temporal != null) {
        dreq.addDateFilter()
            .setPath(timeAttribute)
            .setValue(toThresholdDuration(semanticDataElementInfo.temporal));
      }
      dreq.addSort()
          .setDirection(SortDirection.DESCENDING)
          .setPath(timeAttribute);

    }

    return Stream.of(dreq);
  }

  private Coding toCoding(Term trm, String label) {
    return new Coding()
        .setCode(trm.getTag())
        .setDisplay(label)
        .setSystem(trm.getNamespaceUri().toString());
  }

  private Duration toThresholdDuration(Duration temporal) {
    return (Duration) temporal.copy()
        .setValue(temporal.getValue().intValue() * 3);
  }



}
