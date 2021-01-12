library Patient_Demographics version '1.0.0'

using FHIR version '4.0.1'

include FHIRHelpers version '4.0.1' called FHIRHelpers
include CDS_Connect_Commons_for_FHIRv400 version '1.0.2' called C3F

context Patient

/*
 * Demographics
 */

//https://opencpg.org/ontology/covid19/ed/9d8d2d5a-b12b-3151-82a5-05f54db7f538
define "Age - Patient - Quantity Of":
   AgeInYears()

//https://opencpg.org/ontology/covid19/ed/019ec313-2cdf-3ee0-b2e2-a75cf5d3e459
define "Gender - Patient - Kind Of":
  Patient.gender

//https://opencpg.org/ontology/covid19/ed/4e221fc0-84bb-39a8-a315-a154d6dd7879
define "Race - Patient - Kind Of":
  (flatten (
    Patient.extension Extension
      where Extension.url = 'http://hl7.org/fhir/us/core/StructureDefinition/us-core-race'
        return Extension.extension
  )) E
    where E.url = 'ombCategory'
      or E.url = 'detailed'
    return E.value as Coding

define "Race - Patient - Text Of":
  First((flatten (
    Patient.extension Extension
      where Extension.url = 'http://hl7.org/fhir/us/core/StructureDefinition/us-core-race'
        return Extension.extension
  )) E
    where E.url = 'text'
    return E.value)
