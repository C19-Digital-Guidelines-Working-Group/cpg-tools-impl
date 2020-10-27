package cpg.covid19.ed.owl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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

  IParser jsonParser = FhirContext.forR4()
      .newJsonParser()
      .setPrettyPrint(true);

  @Override
  public void run(Path srcPath, Path tgtPath) {
    List<SemanticDataElementInfo> infos = readDataElements(srcPath);

    List<DataRequirement> dataRequirements = infos.stream()
        .flatMap(this::toDataRequirement)
        .collect(Collectors.toList());

    dataRequirements.forEach(dreq -> this.save(dreq, tgtPath));

  }

  private void save(DataRequirement dreq, Path tgtPath) {
    Coding cd = (Coding) dreq.getExtensionFirstRep().getValue();
    Path file = tgtPath.resolve(Path.of("dataRequirement",
        cd.getDisplay() + " - " + dreq.getType() + ".fhir.json"));

    PlanDefinition mockWrapper = new PlanDefinition();
    mockWrapper.addAction().addInput(dreq);

    String json = jsonParser.encodeResourceToString(mockWrapper);
    try {
      if (! Files.exists(file.getParent())) {
        Files.createDirectories(file.getParent());
      }
      Files.write(file,json.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
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
            .setDisplay(semanticDataElementInfo.dataElement)
            .setSystem(cso.getNamespaceUri().toString()));

    dreq.setSubject(new CodeableConcept()
        .addCoding(new Coding()
            .setCode(FHIRAllTypes.PATIENT.toCode())
            .setDisplay(FHIRAllTypes.PATIENT.getDisplay())));

    if (semanticDataElementInfo.kind != ConceptKind.DEMOGRAPHICS) {
      semanticDataElementInfo.allConcepts()
          .forEach(trm -> dreq.addCodeFilter().addCode(toCoding(trm)));

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

  private Coding toCoding(Term trm) {
    return new Coding()
        .setCode(trm.getTag())
        .setSystem(trm.getNamespaceUri().toString());
  }

  private Duration toThresholdDuration(Duration temporal) {
    return (Duration) temporal.copy()
        .setValue(temporal.getValue().intValue() * 3);
  }



}
