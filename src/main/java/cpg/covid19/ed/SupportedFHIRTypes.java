package cpg.covid19.ed;

import java.util.Arrays;
import org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes;

public enum SupportedFHIRTypes {
  MEDICATIONSTATEMENT(FHIRAllTypes.MEDICATIONSTATEMENT, "MedicationStatement"),
  PATIENT(FHIRAllTypes.PATIENT, "Patient"),
  CONDITION(FHIRAllTypes.CONDITION, "Condition"),
  DIAGNOSTICREPORT(FHIRAllTypes.DIAGNOSTICREPORT, "DiagnosticReport"),
  OBSERVATION(FHIRAllTypes.OBSERVATION, "Observation"),
  PROCEDURE(FHIRAllTypes.PROCEDURE, "Procedure"),
  DEVICEREQUEST(FHIRAllTypes.DEVICEREQUEST, "DeviceRequest"),
  MEDICATIONREQUEST(FHIRAllTypes.MEDICATIONREQUEST, "MedicationRequest"),
  SERVICEREQUEST(FHIRAllTypes.SERVICEREQUEST, "ServiceRequest");

  public final String resourceType;
  public final FHIRAllTypes fhirType;

  private SupportedFHIRTypes(FHIRAllTypes fhirType, String resourceType) {
    this.fhirType = fhirType;
    this.resourceType = resourceType;
  }

  public static SupportedFHIRTypes fromCode(String resourceType) {
    return Arrays.stream(SupportedFHIRTypes.values())
        .filter(x -> x.resourceType.equals(resourceType))
        .findFirst()
        .orElseThrow();
  }

}
