library COVID19_ED_CPG_Retrieves version '0.0.1'

using FHIR version '4.0.1'

include FHIRHelpers version '4.0.1'
include CDS_Connect_Commons_for_FHIRv400 version '1.0.2' called C3F

include COVID19_ED_CPG_Concepts_Premapped version '0.0.1' called CC

include Patient_Demographics_FHIRv400 version '1.0.0'
context Patient

/*
//https://opencpg.org/ontology/covid19/ed/0d631b60-7489-3b5e-b816-dd4dd5d3b243
define "Current Mental Status - Observation" : 
C3F.Verified( [ Observation : CC."Mental Status" ] )
*/

/*
define "Current Mental Status - Observation - Is" : 
	Exists( "Current Mental Status - Observation" ) 

*/

/*
define "Current Mental Status - Observation - Last" : 
	Last( "Current Mental Status - Observation" ) 

*/

/*
define "Current Mental Status - Observation - Kind Of" : 
	"Current Mental Status - Observation - Last".code 

*/

/*
define "Current Mental Status - Observation - Value of" : 
	"Current Mental Status - Observation - Last".value 

*/

/*
define "Current Mental Status - Observation - Quantity Of" : 
	"Current Mental Status - Observation - Last".value 

*/



//https://opencpg.org/ontology/covid19/ed/0f783f73-2d10-3dbf-b0f2-55f311e83e67
define "Current GCS - Observation" : 
C3F.Verified( [ Observation : CC."GCS" ] )


define "Current GCS - Observation - Is" : 
	Exists( "Current GCS - Observation" ) 



define "Current GCS - Observation - Last" : 
	Last( "Current GCS - Observation" ) 



define "Current GCS - Observation - Kind Of" : 
	"Current GCS - Observation - Last".code 



define "Current GCS - Observation - Value of" : 
	"Current GCS - Observation - Last".value 



define "Current GCS - Observation - Quantity Of" : 
	"Current GCS - Observation - Last".value 



/*
//https://opencpg.org/ontology/covid19/ed/7d8d47da-454d-3f38-bee9-6e87d7e2657a
define "Current Work of Breathing - Observation" : 
C3F.Verified( [ Observation : CC."Work of Breathing" ] )
*/

/*
define "Current Work of Breathing - Observation - Is" : 
	Exists( "Current Work of Breathing - Observation" ) 

*/

/*
define "Current Work of Breathing - Observation - Last" : 
	Last( "Current Work of Breathing - Observation" ) 

*/

/*
define "Current Work of Breathing - Observation - Kind Of" : 
	"Current Work of Breathing - Observation - Last".code 

*/

/*
define "Current Work of Breathing - Observation - Value of" : 
	"Current Work of Breathing - Observation - Last".value 

*/

/*
define "Current Work of Breathing - Observation - Quantity Of" : 
	"Current Work of Breathing - Observation - Last".value 

*/


/*
//https://opencpg.org/ontology/covid19/ed/78380b3f-a38e-39fe-ae3e-5ecfd7e8b086
define "Current Persistent Dyspnea - Observation" : 
C3F.Verified( [ Observation : CC."Persistent Dyspnea" ] )
*/

/*
define "Current Persistent Dyspnea - Observation - Is" : 
	Exists( "Current Persistent Dyspnea - Observation" ) 

*/

/*
define "Current Persistent Dyspnea - Observation - Last" : 
	Last( "Current Persistent Dyspnea - Observation" ) 

*/

/*
define "Current Persistent Dyspnea - Observation - Kind Of" : 
	"Current Persistent Dyspnea - Observation - Last".code 

*/

/*
define "Current Persistent Dyspnea - Observation - Value of" : 
	"Current Persistent Dyspnea - Observation - Last".value 

*/

/*
define "Current Persistent Dyspnea - Observation - Quantity Of" : 
	"Current Persistent Dyspnea - Observation - Last".value 

*/



//https://opencpg.org/ontology/covid19/ed/a3a8db59-2649-3876-9bf8-61f81878bef9
define "Has Hemoptysis - Observation" : 
C3F.Verified( [ Observation : CC."Hemoptysis" ] )


define "Has Hemoptysis - Observation - Is" : 
	Exists( "Has Hemoptysis - Observation" ) 



define "Has Hemoptysis - Observation - Last" : 
	Last( "Has Hemoptysis - Observation" ) 



define "Has Hemoptysis - Observation - Kind Of" : 
	"Has Hemoptysis - Observation - Last".code 



define "Has Hemoptysis - Observation - Value of" : 
	"Has Hemoptysis - Observation - Last".value 



define "Has Hemoptysis - Observation - Quantity Of" : 
	"Has Hemoptysis - Observation - Last".value 



/*
//https://opencpg.org/ontology/covid19/ed/5ae3dc37-3287-398d-bf85-d8fdbfa36ba1
define "Current O2 Saturation - Observation" : 
C3F.ObservationLookBack( C3F.Verified( [ Observation : CC."O2 Saturation" ] ), 4 hours)
*/

/*
define "Current O2 Saturation - Observation - Is" : 
	Exists( "Current O2 Saturation - Observation" ) 

*/

/*
define "Current O2 Saturation - Observation - Last" : 
	Last( "Current O2 Saturation - Observation" ) 

*/

/*
define "Current O2 Saturation - Observation - Kind Of" : 
	"Current O2 Saturation - Observation - Last".code 

*/

/*
define "Current O2 Saturation - Observation - Value of" : 
	"Current O2 Saturation - Observation - Last".value 

*/

/*
define "Current O2 Saturation - Observation - Quantity Of" : 
	"Current O2 Saturation - Observation - Last".value 

*/



//https://opencpg.org/ontology/covid19/ed/e9c846d0-1f96-3181-8150-fb0ed1c149b1
define "Most Recent O2 Saturation (Ambulatory) - Observation" : 
C3F.Verified( [ Observation : CC."O2 Saturation (Ambulatory)" ] )


define "Most Recent O2 Saturation (Ambulatory) - Observation - Is" : 
	Exists( "Most Recent O2 Saturation (Ambulatory) - Observation" ) 



define "Most Recent O2 Saturation (Ambulatory) - Observation - Last" : 
	Last( "Most Recent O2 Saturation (Ambulatory) - Observation" ) 



define "Most Recent O2 Saturation (Ambulatory) - Observation - Kind Of" : 
	"Most Recent O2 Saturation (Ambulatory) - Observation - Last".code 



define "Most Recent O2 Saturation (Ambulatory) - Observation - Value of" : 
	"Most Recent O2 Saturation (Ambulatory) - Observation - Last".value 



define "Most Recent O2 Saturation (Ambulatory) - Observation - Quantity Of" : 
	"Most Recent O2 Saturation (Ambulatory) - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/79919c0e-1a3d-38ea-a212-e5db8ab30fca
define "Current Respiratory Rate - Observation" : 
C3F.Verified( [ Observation : CC."Respiratory Rate" ] )


define "Current Respiratory Rate - Observation - Is" : 
	Exists( "Current Respiratory Rate - Observation" ) 



define "Current Respiratory Rate - Observation - Last" : 
	Last( "Current Respiratory Rate - Observation" ) 



define "Current Respiratory Rate - Observation - Kind Of" : 
	"Current Respiratory Rate - Observation - Last".code 



define "Current Respiratory Rate - Observation - Value of" : 
	"Current Respiratory Rate - Observation - Last".value 



define "Current Respiratory Rate - Observation - Quantity Of" : 
	"Current Respiratory Rate - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/84a8ab48-fc39-307a-9f4f-99a89db68d73
define "Current Heart Rate - Observation" : 
C3F.Verified( [ Observation : CC."Heart Rate" ] )


define "Current Heart Rate - Observation - Is" : 
	Exists( "Current Heart Rate - Observation" ) 



define "Current Heart Rate - Observation - Last" : 
	Last( "Current Heart Rate - Observation" ) 



define "Current Heart Rate - Observation - Kind Of" : 
	"Current Heart Rate - Observation - Last".code 



define "Current Heart Rate - Observation - Value of" : 
	"Current Heart Rate - Observation - Last".value 



define "Current Heart Rate - Observation - Quantity Of" : 
	"Current Heart Rate - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/98a2ce86-4178-3b16-b22e-d0471aca7a49
define "Current Systolic BP - Observation" : 
C3F.Verified( [ Observation : CC."Systolic BP" ] )


define "Current Systolic BP - Observation - Is" : 
	Exists( "Current Systolic BP - Observation" ) 



define "Current Systolic BP - Observation - Last" : 
	Last( "Current Systolic BP - Observation" ) 



define "Current Systolic BP - Observation - Kind Of" : 
	"Current Systolic BP - Observation - Last".code 



define "Current Systolic BP - Observation - Value of" : 
	"Current Systolic BP - Observation - Last".value 



define "Current Systolic BP - Observation - Quantity Of" : 
	"Current Systolic BP - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/84aa13c4-6e2b-3997-9a98-8b16dc2ffdf3
define "Current Diastolic BP - Observation" : 
C3F.Verified( [ Observation : CC."Diastolic BP" ] )


define "Current Diastolic BP - Observation - Is" : 
	Exists( "Current Diastolic BP - Observation" ) 



define "Current Diastolic BP - Observation - Last" : 
	Last( "Current Diastolic BP - Observation" ) 



define "Current Diastolic BP - Observation - Kind Of" : 
	"Current Diastolic BP - Observation - Last".code 



define "Current Diastolic BP - Observation - Value of" : 
	"Current Diastolic BP - Observation - Last".value 



define "Current Diastolic BP - Observation - Quantity Of" : 
	"Current Diastolic BP - Observation - Last".value 



/*
//https://opencpg.org/ontology/covid19/ed/019ec313-2cdf-3ee0-b2e2-a75cf5d3e459
define "Gender - Patient" : 

*/


/*
//https://opencpg.org/ontology/covid19/ed/9d8d2d5a-b12b-3151-82a5-05f54db7f538
define "Age - Patient" : 

*/


/*
//https://opencpg.org/ontology/covid19/ed/4e221fc0-84bb-39a8-a315-a154d6dd7879
define "Race - Patient" : 

*/



//https://opencpg.org/ontology/covid19/ed/3bffa372-b64e-31de-8412-2300ceaf5910
define "Current FiO2 - Observation" : 
C3F.Verified( [ Observation : CC."FiO2" ] )


define "Current FiO2 - Observation - Is" : 
	Exists( "Current FiO2 - Observation" ) 



define "Current FiO2 - Observation - Last" : 
	Last( "Current FiO2 - Observation" ) 



define "Current FiO2 - Observation - Kind Of" : 
	"Current FiO2 - Observation - Last".code 



define "Current FiO2 - Observation - Value of" : 
	"Current FiO2 - Observation - Last".value 



define "Current FiO2 - Observation - Quantity Of" : 
	"Current FiO2 - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/f6e6247c-50c3-39f1-beba-de8703a55294
define "Prior Respiratory Support- Supplemental O2 - Procedure" : 
C3F.ProcedurePerformance( [ Procedure : CC."Respiratory Support- Supplemental O2" ] )


define "Prior Respiratory Support- Supplemental O2 - Procedure - Is" : 
	Exists( "Prior Respiratory Support- Supplemental O2 - Procedure" ) 



define "Prior Respiratory Support- Supplemental O2 - Procedure - Last" : 
	Last( "Prior Respiratory Support- Supplemental O2 - Procedure" ) 



define "Prior Respiratory Support- Supplemental O2 - Procedure - Kind Of" : 
	"Prior Respiratory Support- Supplemental O2 - Procedure - Last".code 




//https://opencpg.org/ontology/covid19/ed/f6e6247c-50c3-39f1-beba-de8703a55294
define "Prior Respiratory Support- Supplemental O2 - DeviceRequest" : 
 /*FIXME*/ C3F.ServiceRequestActiveOrCompleted( [ DeviceRequest : CC."Respiratory Support- Supplemental O2" ] )


define "Prior Respiratory Support- Supplemental O2 - DeviceRequest - Is" : 
	Exists( "Prior Respiratory Support- Supplemental O2 - DeviceRequest" ) 




//https://opencpg.org/ontology/covid19/ed/f6e6247c-50c3-39f1-beba-de8703a55294
define "Prior Respiratory Support- Supplemental O2 - ServiceRequest" : 
C3F.ServiceRequestActiveOrCompleted( [ ServiceRequest : CC."Respiratory Support- Supplemental O2" ] )


define "Prior Respiratory Support- Supplemental O2 - ServiceRequest - Is" : 
	Exists( "Prior Respiratory Support- Supplemental O2 - ServiceRequest" ) 




//https://opencpg.org/ontology/covid19/ed/7ac71826-bd37-3fc3-a8bf-2ecbe377e9fd
define "Prior Respiratory Support- High Flow Nasal Canula - Procedure" : 
C3F.ProcedurePerformance( [ Procedure : CC."Respiratory Support- High Flow Nasal Canula" ] )


define "Prior Respiratory Support- High Flow Nasal Canula - Procedure - Is" : 
	Exists( "Prior Respiratory Support- High Flow Nasal Canula - Procedure" ) 



define "Prior Respiratory Support- High Flow Nasal Canula - Procedure - Last" : 
	Last( "Prior Respiratory Support- High Flow Nasal Canula - Procedure" ) 



define "Prior Respiratory Support- High Flow Nasal Canula - Procedure - Kind Of" : 
	"Prior Respiratory Support- High Flow Nasal Canula - Procedure - Last".code 




//https://opencpg.org/ontology/covid19/ed/7ac71826-bd37-3fc3-a8bf-2ecbe377e9fd
define "Prior Respiratory Support- High Flow Nasal Canula - DeviceRequest" : 
 /*FIXME*/ C3F.ServiceRequestActiveOrCompleted( [ DeviceRequest : CC."Respiratory Support- High Flow Nasal Canula" ] )


define "Prior Respiratory Support- High Flow Nasal Canula - DeviceRequest - Is" : 
	Exists( "Prior Respiratory Support- High Flow Nasal Canula - DeviceRequest" ) 




//https://opencpg.org/ontology/covid19/ed/7ac71826-bd37-3fc3-a8bf-2ecbe377e9fd
define "Prior Respiratory Support- High Flow Nasal Canula - ServiceRequest" : 
C3F.ServiceRequestActiveOrCompleted( [ ServiceRequest : CC."Respiratory Support- High Flow Nasal Canula" ] )


define "Prior Respiratory Support- High Flow Nasal Canula - ServiceRequest - Is" : 
	Exists( "Prior Respiratory Support- High Flow Nasal Canula - ServiceRequest" ) 




//https://opencpg.org/ontology/covid19/ed/2b07c564-a2e2-393b-9fa4-99bce1f865c2
define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - Procedure" : 
C3F.ProcedurePerformance( [ Procedure : CC."Respiratory Support- Non Invasive Positive Pressure Ventilation" ] )


define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - Procedure - Is" : 
	Exists( "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - Procedure" ) 



define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - Procedure - Last" : 
	Last( "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - Procedure" ) 



define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - Procedure - Kind Of" : 
	"Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - Procedure - Last".code 




//https://opencpg.org/ontology/covid19/ed/2b07c564-a2e2-393b-9fa4-99bce1f865c2
define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - DeviceRequest" : 
 /*FIXME*/ C3F.ServiceRequestActiveOrCompleted( [ DeviceRequest : CC."Respiratory Support- Non Invasive Positive Pressure Ventilation" ] )


define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - DeviceRequest - Is" : 
	Exists( "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - DeviceRequest" ) 




//https://opencpg.org/ontology/covid19/ed/2b07c564-a2e2-393b-9fa4-99bce1f865c2
define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - ServiceRequest" : 
C3F.ServiceRequestActiveOrCompleted( [ ServiceRequest : CC."Respiratory Support- Non Invasive Positive Pressure Ventilation" ] )


define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - ServiceRequest - Is" : 
	Exists( "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - ServiceRequest" ) 



/*
//https://opencpg.org/ontology/covid19/ed/270c354c-22ae-3077-8e0e-7f0ab0a2b483
define "Prior Respiratory Support- Mechanical Ventilation - Procedure" : 
C3F.ProcedurePerformance( [ Procedure : CC."Respiratory Support- Mechanical Ventilation" ] )
*/

/*
define "Prior Respiratory Support- Mechanical Ventilation - Procedure - Is" : 
	Exists( "Prior Respiratory Support- Mechanical Ventilation - Procedure" ) 

*/

/*
define "Prior Respiratory Support- Mechanical Ventilation - Procedure - Last" : 
	Last( "Prior Respiratory Support- Mechanical Ventilation - Procedure" ) 

*/

/*
define "Prior Respiratory Support- Mechanical Ventilation - Procedure - Kind Of" : 
	"Prior Respiratory Support- Mechanical Ventilation - Procedure - Last".code 

*/


/*
//https://opencpg.org/ontology/covid19/ed/270c354c-22ae-3077-8e0e-7f0ab0a2b483
define "Prior Respiratory Support- Mechanical Ventilation - DeviceRequest" : 
 FIXME C3F.ServiceRequestActiveOrCompleted( [ DeviceRequest : CC."Respiratory Support- Mechanical Ventilation" ] )
*/

/*
define "Prior Respiratory Support- Mechanical Ventilation - DeviceRequest - Is" : 
	Exists( "Prior Respiratory Support- Mechanical Ventilation - DeviceRequest" ) 

*/


/*
//https://opencpg.org/ontology/covid19/ed/270c354c-22ae-3077-8e0e-7f0ab0a2b483
define "Prior Respiratory Support- Mechanical Ventilation - ServiceRequest" : 
C3F.ServiceRequestActiveOrCompleted( [ ServiceRequest : CC."Respiratory Support- Mechanical Ventilation" ] )
*/

/*
define "Prior Respiratory Support- Mechanical Ventilation - ServiceRequest - Is" : 
	Exists( "Prior Respiratory Support- Mechanical Ventilation - ServiceRequest" ) 

*/



//https://opencpg.org/ontology/covid19/ed/311eb6b1-c1c8-389c-8826-d8673cc4d623
define "On Dobutamine - MedicationStatement" : 
C3F.ActiveMedicationStatement( [ MedicationStatement : CC."Dobutamine" ] )


define "On Dobutamine - MedicationStatement - Is" : 
	Exists( "On Dobutamine - MedicationStatement" ) 




//https://opencpg.org/ontology/covid19/ed/311eb6b1-c1c8-389c-8826-d8673cc4d623
define "On Dobutamine - MedicationRequest" : 
C3F.ActiveMedicationRequest( [ MedicationRequest : CC."Dobutamine" ] )


define "On Dobutamine - MedicationRequest - Is" : 
	Exists( "On Dobutamine - MedicationRequest" ) 




//https://opencpg.org/ontology/covid19/ed/39e38c19-caa5-39eb-a485-2fea6166e9fd
define "On Epinephrine - MedicationStatement" : 
C3F.ActiveMedicationStatement( [ MedicationStatement : CC."Epinephrine" ] )


define "On Epinephrine - MedicationStatement - Is" : 
	Exists( "On Epinephrine - MedicationStatement" ) 




//https://opencpg.org/ontology/covid19/ed/39e38c19-caa5-39eb-a485-2fea6166e9fd
define "On Epinephrine - MedicationRequest" : 
C3F.ActiveMedicationRequest( [ MedicationRequest : CC."Epinephrine" ] )


define "On Epinephrine - MedicationRequest - Is" : 
	Exists( "On Epinephrine - MedicationRequest" ) 




//https://opencpg.org/ontology/covid19/ed/f7ac0c35-613d-3796-b45c-13fa031adcdb
define "On Norepinephrine - MedicationStatement" : 
C3F.ActiveMedicationStatement( [ MedicationStatement : CC."Norepinephrine" ] )


define "On Norepinephrine - MedicationStatement - Is" : 
	Exists( "On Norepinephrine - MedicationStatement" ) 




//https://opencpg.org/ontology/covid19/ed/f7ac0c35-613d-3796-b45c-13fa031adcdb
define "On Norepinephrine - MedicationRequest" : 
C3F.ActiveMedicationRequest( [ MedicationRequest : CC."Norepinephrine" ] )


define "On Norepinephrine - MedicationRequest - Is" : 
	Exists( "On Norepinephrine - MedicationRequest" ) 




//https://opencpg.org/ontology/covid19/ed/7a1c6d5b-f54f-39cc-bb36-e6843a58d1e4
define "On Vasopressin - MedicationStatement" : 
C3F.ActiveMedicationStatement( [ MedicationStatement : CC."Vasopressin" ] )


define "On Vasopressin - MedicationStatement - Is" : 
	Exists( "On Vasopressin - MedicationStatement" ) 




//https://opencpg.org/ontology/covid19/ed/7a1c6d5b-f54f-39cc-bb36-e6843a58d1e4
define "On Vasopressin - MedicationRequest" : 
C3F.ActiveMedicationRequest( [ MedicationRequest : CC."Vasopressin" ] )


define "On Vasopressin - MedicationRequest - Is" : 
	Exists( "On Vasopressin - MedicationRequest" ) 




//https://opencpg.org/ontology/covid19/ed/d7e7bc21-3b84-3a95-9946-ff0d9cb165cb
define "On Dopamine - MedicationStatement" : 
C3F.ActiveMedicationStatement( [ MedicationStatement : CC."Dopamine" ] )


define "On Dopamine - MedicationStatement - Is" : 
	Exists( "On Dopamine - MedicationStatement" ) 




//https://opencpg.org/ontology/covid19/ed/d7e7bc21-3b84-3a95-9946-ff0d9cb165cb
define "On Dopamine - MedicationRequest" : 
C3F.ActiveMedicationRequest( [ MedicationRequest : CC."Dopamine" ] )


define "On Dopamine - MedicationRequest - Is" : 
	Exists( "On Dopamine - MedicationRequest" ) 




//https://opencpg.org/ontology/covid19/ed/18ba15e1-65ae-3f63-b08c-42dd43e3676e
define "Most Recent SARS-CoV-2 test - Observation" : 
C3F.Verified( [ Observation : CC."SARS-CoV-2 test" ] )


define "Most Recent SARS-CoV-2 test - Observation - Is" : 
	Exists( "Most Recent SARS-CoV-2 test - Observation" ) 



define "Most Recent SARS-CoV-2 test - Observation - Last" : 
	Last( "Most Recent SARS-CoV-2 test - Observation" ) 



define "Most Recent SARS-CoV-2 test - Observation - Kind Of" : 
	"Most Recent SARS-CoV-2 test - Observation - Last".code 



define "Most Recent SARS-CoV-2 test - Observation - Value of" : 
	"Most Recent SARS-CoV-2 test - Observation - Last".value 



define "Most Recent SARS-CoV-2 test - Observation - Quantity Of" : 
	"Most Recent SARS-CoV-2 test - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/4becaa45-8d21-383b-89b7-8e17f06e6712
define "Most Recent Troponin I - Observation" : 
C3F.Verified( [ Observation : CC."Troponin I" ] )


define "Most Recent Troponin I - Observation - Is" : 
	Exists( "Most Recent Troponin I - Observation" ) 



define "Most Recent Troponin I - Observation - Last" : 
	Last( "Most Recent Troponin I - Observation" ) 



define "Most Recent Troponin I - Observation - Kind Of" : 
	"Most Recent Troponin I - Observation - Last".code 



define "Most Recent Troponin I - Observation - Value of" : 
	"Most Recent Troponin I - Observation - Last".value 



define "Most Recent Troponin I - Observation - Quantity Of" : 
	"Most Recent Troponin I - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/5959cf6f-9a5d-3640-947a-e81012372314
define "Most Recent D-dimer - Observation" : 
C3F.Verified( [ Observation : CC."D-dimer" ] )


define "Most Recent D-dimer - Observation - Is" : 
	Exists( "Most Recent D-dimer - Observation" ) 



define "Most Recent D-dimer - Observation - Last" : 
	Last( "Most Recent D-dimer - Observation" ) 



define "Most Recent D-dimer - Observation - Kind Of" : 
	"Most Recent D-dimer - Observation - Last".code 



define "Most Recent D-dimer - Observation - Value of" : 
	"Most Recent D-dimer - Observation - Last".value 



define "Most Recent D-dimer - Observation - Quantity Of" : 
	"Most Recent D-dimer - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/c434158d-0aa9-3676-b699-ea3d00be1e1b
define "Most Recent Lactate - Observation" : 
C3F.Verified( [ Observation : CC."Lactate" ] )


define "Most Recent Lactate - Observation - Is" : 
	Exists( "Most Recent Lactate - Observation" ) 



define "Most Recent Lactate - Observation - Last" : 
	Last( "Most Recent Lactate - Observation" ) 



define "Most Recent Lactate - Observation - Kind Of" : 
	"Most Recent Lactate - Observation - Last".code 



define "Most Recent Lactate - Observation - Value of" : 
	"Most Recent Lactate - Observation - Last".value 



define "Most Recent Lactate - Observation - Quantity Of" : 
	"Most Recent Lactate - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/95bfa631-4574-3a2b-8c16-e6b08ab7c989
define "Most Recent Lactate Dehydrogenase - Observation" : 
C3F.Verified( [ Observation : CC."Lactate Dehydrogenase" ] )


define "Most Recent Lactate Dehydrogenase - Observation - Is" : 
	Exists( "Most Recent Lactate Dehydrogenase - Observation" ) 



define "Most Recent Lactate Dehydrogenase - Observation - Last" : 
	Last( "Most Recent Lactate Dehydrogenase - Observation" ) 



define "Most Recent Lactate Dehydrogenase - Observation - Kind Of" : 
	"Most Recent Lactate Dehydrogenase - Observation - Last".code 



define "Most Recent Lactate Dehydrogenase - Observation - Value of" : 
	"Most Recent Lactate Dehydrogenase - Observation - Last".value 



define "Most Recent Lactate Dehydrogenase - Observation - Quantity Of" : 
	"Most Recent Lactate Dehydrogenase - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/1a5f251e-6304-3d4c-b207-6fe2a5961eaa
define "Most Recent C-Reactive Peptide - Observation" : 
C3F.Verified( [ Observation : CC."C-Reactive Peptide" ] )


define "Most Recent C-Reactive Peptide - Observation - Is" : 
	Exists( "Most Recent C-Reactive Peptide - Observation" ) 



define "Most Recent C-Reactive Peptide - Observation - Last" : 
	Last( "Most Recent C-Reactive Peptide - Observation" ) 



define "Most Recent C-Reactive Peptide - Observation - Kind Of" : 
	"Most Recent C-Reactive Peptide - Observation - Last".code 



define "Most Recent C-Reactive Peptide - Observation - Value of" : 
	"Most Recent C-Reactive Peptide - Observation - Last".value 



define "Most Recent C-Reactive Peptide - Observation - Quantity Of" : 
	"Most Recent C-Reactive Peptide - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/9547fdae-e53d-36d2-8c8d-546b2822d40f
define "Most Recent Neutrophils (#) - Observation" : 
C3F.Verified( [ Observation : CC."Neutrophils (#)" ] )


define "Most Recent Neutrophils (#) - Observation - Is" : 
	Exists( "Most Recent Neutrophils (#) - Observation" ) 



define "Most Recent Neutrophils (#) - Observation - Last" : 
	Last( "Most Recent Neutrophils (#) - Observation" ) 



define "Most Recent Neutrophils (#) - Observation - Kind Of" : 
	"Most Recent Neutrophils (#) - Observation - Last".code 



define "Most Recent Neutrophils (#) - Observation - Value of" : 
	"Most Recent Neutrophils (#) - Observation - Last".value 



define "Most Recent Neutrophils (#) - Observation - Quantity Of" : 
	"Most Recent Neutrophils (#) - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/fc070a93-5c12-34c2-8b3f-3cc7115e1059
define "Most Recent Lymphocytes (#) - Observation" : 
C3F.Verified( [ Observation : CC."Lymphocytes (#)" ] )


define "Most Recent Lymphocytes (#) - Observation - Is" : 
	Exists( "Most Recent Lymphocytes (#) - Observation" ) 



define "Most Recent Lymphocytes (#) - Observation - Last" : 
	Last( "Most Recent Lymphocytes (#) - Observation" ) 



define "Most Recent Lymphocytes (#) - Observation - Kind Of" : 
	"Most Recent Lymphocytes (#) - Observation - Last".code 



define "Most Recent Lymphocytes (#) - Observation - Value of" : 
	"Most Recent Lymphocytes (#) - Observation - Last".value 



define "Most Recent Lymphocytes (#) - Observation - Quantity Of" : 
	"Most Recent Lymphocytes (#) - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/136be6a5-dd5c-3e80-bba9-aa1dd3c9b056
define "Most Recent Lymphocyte (%) - Observation" : 
C3F.Verified( [ Observation : CC."Lymphocyte (%)" ] )


define "Most Recent Lymphocyte (%) - Observation - Is" : 
	Exists( "Most Recent Lymphocyte (%) - Observation" ) 



define "Most Recent Lymphocyte (%) - Observation - Last" : 
	Last( "Most Recent Lymphocyte (%) - Observation" ) 



define "Most Recent Lymphocyte (%) - Observation - Kind Of" : 
	"Most Recent Lymphocyte (%) - Observation - Last".code 



define "Most Recent Lymphocyte (%) - Observation - Value of" : 
	"Most Recent Lymphocyte (%) - Observation - Last".value 



define "Most Recent Lymphocyte (%) - Observation - Quantity Of" : 
	"Most Recent Lymphocyte (%) - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/cfb06246-ab5b-3871-8385-120d5664f14f
define "Most Recent Ferritin - Observation" : 
C3F.Verified( [ Observation : CC."Ferritin" ] )


define "Most Recent Ferritin - Observation - Is" : 
	Exists( "Most Recent Ferritin - Observation" ) 



define "Most Recent Ferritin - Observation - Last" : 
	Last( "Most Recent Ferritin - Observation" ) 



define "Most Recent Ferritin - Observation - Kind Of" : 
	"Most Recent Ferritin - Observation - Last".code 



define "Most Recent Ferritin - Observation - Value of" : 
	"Most Recent Ferritin - Observation - Last".value 



define "Most Recent Ferritin - Observation - Quantity Of" : 
	"Most Recent Ferritin - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/256c1862-05d1-3c22-bfd8-b34c40206eb5
define "Most Recent Platelets - Observation" : 
C3F.Verified( [ Observation : CC."Platelets" ] )


define "Most Recent Platelets - Observation - Is" : 
	Exists( "Most Recent Platelets - Observation" ) 



define "Most Recent Platelets - Observation - Last" : 
	Last( "Most Recent Platelets - Observation" ) 



define "Most Recent Platelets - Observation - Kind Of" : 
	"Most Recent Platelets - Observation - Last".code 



define "Most Recent Platelets - Observation - Value of" : 
	"Most Recent Platelets - Observation - Last".value 



define "Most Recent Platelets - Observation - Quantity Of" : 
	"Most Recent Platelets - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/dfbfc9cc-7a23-3560-b04c-b1a5faf1ec3e
define "Most Recent ALT - Observation" : 
C3F.Verified( [ Observation : CC."ALT" ] )


define "Most Recent ALT - Observation - Is" : 
	Exists( "Most Recent ALT - Observation" ) 



define "Most Recent ALT - Observation - Last" : 
	Last( "Most Recent ALT - Observation" ) 



define "Most Recent ALT - Observation - Kind Of" : 
	"Most Recent ALT - Observation - Last".code 



define "Most Recent ALT - Observation - Value of" : 
	"Most Recent ALT - Observation - Last".value 



define "Most Recent ALT - Observation - Quantity Of" : 
	"Most Recent ALT - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/c77bb0f6-9e2a-30e0-86a8-dc7f0da9320f
define "Most Recent AST - Observation" : 
C3F.Verified( [ Observation : CC."AST" ] )


define "Most Recent AST - Observation - Is" : 
	Exists( "Most Recent AST - Observation" ) 



define "Most Recent AST - Observation - Last" : 
	Last( "Most Recent AST - Observation" ) 



define "Most Recent AST - Observation - Kind Of" : 
	"Most Recent AST - Observation - Last".code 



define "Most Recent AST - Observation - Value of" : 
	"Most Recent AST - Observation - Last".value 



define "Most Recent AST - Observation - Quantity Of" : 
	"Most Recent AST - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/efd4ddff-ab84-30a9-9b02-796d87f769a3
define "Has COVID-19 diagnosis (suspected) - Condition" : 
C3F.Confirmed( C3F.ActiveOrRecurring( [ Condition : CC."COVID-19 diagnosis (suspected)" ] ) ) 


define "Has COVID-19 diagnosis (suspected) - Condition - Is" : 
	Exists( "Has COVID-19 diagnosis (suspected) - Condition" ) 



define "Has COVID-19 diagnosis (suspected) - Condition - Last" : 
	Last( "Has COVID-19 diagnosis (suspected) - Condition" ) 



define "Has COVID-19 diagnosis (suspected) - Condition - Kind Of" : 
	"Has COVID-19 diagnosis (suspected) - Condition - Last".code 




//https://opencpg.org/ontology/covid19/ed/cfe98e01-a3dc-33f9-b6a5-94d1f28f99bc
define "Has COVID-19 diagnosis (confirmed) - Condition" : 
C3F.Confirmed( C3F.ActiveOrRecurring( [ Condition : CC."COVID-19 diagnosis (confirmed)" ] ) ) 


define "Has COVID-19 diagnosis (confirmed) - Condition - Is" : 
	Exists( "Has COVID-19 diagnosis (confirmed) - Condition" ) 



define "Has COVID-19 diagnosis (confirmed) - Condition - Last" : 
	Last( "Has COVID-19 diagnosis (confirmed) - Condition" ) 



define "Has COVID-19 diagnosis (confirmed) - Condition - Kind Of" : 
	"Has COVID-19 diagnosis (confirmed) - Condition - Last".code 




//https://opencpg.org/ontology/covid19/ed/e4f842e1-d5c7-3b8c-9819-31c6aaeaddc5
define "Has Diabetes Mellitus Type 1 - Condition" : 
C3F.Confirmed( C3F.ActiveOrRecurring( [ Condition : CC."Diabetes Mellitus Type 1" ] ) ) 


define "Has Diabetes Mellitus Type 1 - Condition - Is" : 
	Exists( "Has Diabetes Mellitus Type 1 - Condition" ) 



define "Has Diabetes Mellitus Type 1 - Condition - Last" : 
	Last( "Has Diabetes Mellitus Type 1 - Condition" ) 



define "Has Diabetes Mellitus Type 1 - Condition - Kind Of" : 
	"Has Diabetes Mellitus Type 1 - Condition - Last".code 




//https://opencpg.org/ontology/covid19/ed/8d45f1b7-8513-3589-bd37-fe16739d9d89
define "Has Diabetes Mellitus Type 2 - Condition" : 
C3F.Confirmed( C3F.ActiveOrRecurring( [ Condition : CC."Diabetes Mellitus Type 2" ] ) ) 


define "Has Diabetes Mellitus Type 2 - Condition - Is" : 
	Exists( "Has Diabetes Mellitus Type 2 - Condition" ) 



define "Has Diabetes Mellitus Type 2 - Condition - Last" : 
	Last( "Has Diabetes Mellitus Type 2 - Condition" ) 



define "Has Diabetes Mellitus Type 2 - Condition - Kind Of" : 
	"Has Diabetes Mellitus Type 2 - Condition - Last".code 




//https://opencpg.org/ontology/covid19/ed/6ff5edf8-399a-3cfa-a52f-ab8f9069c90c
define "Has Heart Failure - Condition" : 
C3F.Confirmed( C3F.ActiveOrRecurring( [ Condition : CC."Heart Failure" ] ) ) 


define "Has Heart Failure - Condition - Is" : 
	Exists( "Has Heart Failure - Condition" ) 



define "Has Heart Failure - Condition - Last" : 
	Last( "Has Heart Failure - Condition" ) 



define "Has Heart Failure - Condition - Kind Of" : 
	"Has Heart Failure - Condition - Last".code 




//https://opencpg.org/ontology/covid19/ed/01da3ef2-5313-3ba8-bd4e-2123c9ae77eb
define "Has Hypertension - Condition" : 
C3F.Confirmed( C3F.ActiveOrRecurring( [ Condition : CC."Hypertension" ] ) ) 


define "Has Hypertension - Condition - Is" : 
	Exists( "Has Hypertension - Condition" ) 



define "Has Hypertension - Condition - Last" : 
	Last( "Has Hypertension - Condition" ) 



define "Has Hypertension - Condition - Kind Of" : 
	"Has Hypertension - Condition - Last".code 




//https://opencpg.org/ontology/covid19/ed/3dd9edb0-933b-39cf-b279-4bf515e224dc
define "Has Chronic Obstructive Pulmonary Disease (COPD) - Condition" : 
C3F.Confirmed( C3F.ActiveOrRecurring( [ Condition : CC."Chronic Obstructive Pulmonary Disease (COPD)" ] ) ) 


define "Has Chronic Obstructive Pulmonary Disease (COPD) - Condition - Is" : 
	Exists( "Has Chronic Obstructive Pulmonary Disease (COPD) - Condition" ) 



define "Has Chronic Obstructive Pulmonary Disease (COPD) - Condition - Last" : 
	Last( "Has Chronic Obstructive Pulmonary Disease (COPD) - Condition" ) 



define "Has Chronic Obstructive Pulmonary Disease (COPD) - Condition - Kind Of" : 
	"Has Chronic Obstructive Pulmonary Disease (COPD) - Condition - Last".code 




//https://opencpg.org/ontology/covid19/ed/6ef655d9-01f8-3763-a7d4-ad3e54504357
define "Has Cardiovascular Disease - Condition" : 
C3F.Confirmed( C3F.ActiveOrRecurring( [ Condition : CC."Cardiovascular Disease" ] ) ) 


define "Has Cardiovascular Disease - Condition - Is" : 
	Exists( "Has Cardiovascular Disease - Condition" ) 



define "Has Cardiovascular Disease - Condition - Last" : 
	Last( "Has Cardiovascular Disease - Condition" ) 



define "Has Cardiovascular Disease - Condition - Kind Of" : 
	"Has Cardiovascular Disease - Condition - Last".code 




//https://opencpg.org/ontology/covid19/ed/9fdb23fd-68c3-3b78-a52f-019db75d6f6e
define "Has Cerebrovascular disease - Condition" : 
C3F.Confirmed( C3F.ActiveOrRecurring( [ Condition : CC."Cerebrovascular disease" ] ) ) 


define "Has Cerebrovascular disease - Condition - Is" : 
	Exists( "Has Cerebrovascular disease - Condition" ) 



define "Has Cerebrovascular disease - Condition - Last" : 
	Last( "Has Cerebrovascular disease - Condition" ) 



define "Has Cerebrovascular disease - Condition - Kind Of" : 
	"Has Cerebrovascular disease - Condition - Last".code 




//https://opencpg.org/ontology/covid19/ed/9f825141-6f7e-3717-aa63-f96f1f10d7ac
define "Has Obesity - Condition" : 
C3F.Confirmed( C3F.ActiveOrRecurring( [ Condition : CC."Obesity" ] ) ) 


define "Has Obesity - Condition - Is" : 
	Exists( "Has Obesity - Condition" ) 



define "Has Obesity - Condition - Last" : 
	Last( "Has Obesity - Condition" ) 



define "Has Obesity - Condition - Kind Of" : 
	"Has Obesity - Condition - Last".code 



/*
//https://opencpg.org/ontology/covid19/ed/a1fcb0e0-c8f5-3d8c-a7ff-8a244b625669
define "Current Obese - Observation" : 
C3F.Verified( [ Observation : CC."Obese" ] )
*/

/*
define "Current Obese - Observation - Is" : 
	Exists( "Current Obese - Observation" ) 

*/

/*
define "Current Obese - Observation - Last" : 
	Last( "Current Obese - Observation" ) 

*/

/*
define "Current Obese - Observation - Kind Of" : 
	"Current Obese - Observation - Last".code 

*/

/*
define "Current Obese - Observation - Value of" : 
	"Current Obese - Observation - Last".value 

*/

/*
define "Current Obese - Observation - Quantity Of" : 
	"Current Obese - Observation - Last".value 

*/



//https://opencpg.org/ontology/covid19/ed/2c31b3cd-5258-36b2-b5e9-b49434ed3e3d
define "Current BMI - Observation" : 
C3F.Verified( [ Observation : CC."BMI" ] )


define "Current BMI - Observation - Is" : 
	Exists( "Current BMI - Observation" ) 



define "Current BMI - Observation - Last" : 
	Last( "Current BMI - Observation" ) 



define "Current BMI - Observation - Kind Of" : 
	"Current BMI - Observation - Last".code 



define "Current BMI - Observation - Value of" : 
	"Current BMI - Observation - Last".value 



define "Current BMI - Observation - Quantity Of" : 
	"Current BMI - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/4c5ef2ec-4498-3af9-91bb-f7556e025b41
define "Has Renal Disease - Condition" : 
C3F.Confirmed( C3F.ActiveOrRecurring( [ Condition : CC."Renal Disease" ] ) ) 


define "Has Renal Disease - Condition - Is" : 
	Exists( "Has Renal Disease - Condition" ) 



define "Has Renal Disease - Condition - Last" : 
	Last( "Has Renal Disease - Condition" ) 



define "Has Renal Disease - Condition - Kind Of" : 
	"Has Renal Disease - Condition - Last".code 




//https://opencpg.org/ontology/covid19/ed/955c689f-130b-33ab-b64a-3c43dc6d6f81
define "Current Long Term Care Facility Resident - Observation" : 
C3F.Verified( [ Observation : CC."Long Term Care Facility Resident" ] )


define "Current Long Term Care Facility Resident - Observation - Is" : 
	Exists( "Current Long Term Care Facility Resident - Observation" ) 



define "Current Long Term Care Facility Resident - Observation - Last" : 
	Last( "Current Long Term Care Facility Resident - Observation" ) 



define "Current Long Term Care Facility Resident - Observation - Kind Of" : 
	"Current Long Term Care Facility Resident - Observation - Last".code 



define "Current Long Term Care Facility Resident - Observation - Value of" : 
	"Current Long Term Care Facility Resident - Observation - Last".value 



define "Current Long Term Care Facility Resident - Observation - Quantity Of" : 
	"Current Long Term Care Facility Resident - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/986a4f4e-4179-3e81-9dc8-b2a297aa8c87
define "Has Malignancy - Condition" : 
C3F.Confirmed( C3F.ActiveOrRecurring( [ Condition : CC."Malignancy" ] ) ) 


define "Has Malignancy - Condition - Is" : 
	Exists( "Has Malignancy - Condition" ) 



define "Has Malignancy - Condition - Last" : 
	Last( "Has Malignancy - Condition" ) 



define "Has Malignancy - Condition - Kind Of" : 
	"Has Malignancy - Condition - Last".code 



/*
//https://opencpg.org/ontology/covid19/ed/f9a1cd68-c82a-3361-bdc6-58e7fe11c0c3
define "Current CXR Score - Observation" : 
C3F.Verified( [ Observation : CC."CXR Score" ] )
*/

/*
define "Current CXR Score - Observation - Is" : 
	Exists( "Current CXR Score - Observation" ) 

*/

/*
define "Current CXR Score - Observation - Last" : 
	Last( "Current CXR Score - Observation" ) 

*/

/*
define "Current CXR Score - Observation - Kind Of" : 
	"Current CXR Score - Observation - Last".code 

*/

/*
define "Current CXR Score - Observation - Value of" : 
	"Current CXR Score - Observation - Last".value 

*/

/*
define "Current CXR Score - Observation - Quantity Of" : 
	"Current CXR Score - Observation - Last".value 

*/


/*
//https://opencpg.org/ontology/covid19/ed/f9a1cd68-c82a-3361-bdc6-58e7fe11c0c3
define "Current CXR Score - DiagnosticReport" : 
 FIXME C3F.Verified( [ DiagnosticReport : CC."CXR Score" ] )
*/

/*
define "Current CXR Score - DiagnosticReport - Is" : 
	Exists( "Current CXR Score - DiagnosticReport" ) 

*/



//https://opencpg.org/ontology/covid19/ed/a5a5c081-4462-3dc8-99f3-e1471d1b4d4f
define "Has Bilateral Pneumonia - Observation" : 
C3F.Verified( [ Observation : CC."Bilateral Pneumonia" ] )


define "Has Bilateral Pneumonia - Observation - Is" : 
	Exists( "Has Bilateral Pneumonia - Observation" ) 



define "Has Bilateral Pneumonia - Observation - Last" : 
	Last( "Has Bilateral Pneumonia - Observation" ) 



define "Has Bilateral Pneumonia - Observation - Kind Of" : 
	"Has Bilateral Pneumonia - Observation - Last".code 



define "Has Bilateral Pneumonia - Observation - Value of" : 
	"Has Bilateral Pneumonia - Observation - Last".value 



define "Has Bilateral Pneumonia - Observation - Quantity Of" : 
	"Has Bilateral Pneumonia - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/a5a5c081-4462-3dc8-99f3-e1471d1b4d4f
define "Has Bilateral Pneumonia - DiagnosticReport" : 
 /*FIXME*/ C3F.Verified( [ DiagnosticReport : CC."Bilateral Pneumonia" ] )


define "Has Bilateral Pneumonia - DiagnosticReport - Is" : 
	Exists( "Has Bilateral Pneumonia - DiagnosticReport" ) 




//https://opencpg.org/ontology/covid19/ed/1e63bbb8-709a-300a-94e7-d47fcd830d85
define "Has RV dilatation - Observation" : 
C3F.Verified( [ Observation : CC."RV dilatation" ] )


define "Has RV dilatation - Observation - Is" : 
	Exists( "Has RV dilatation - Observation" ) 



define "Has RV dilatation - Observation - Last" : 
	Last( "Has RV dilatation - Observation" ) 



define "Has RV dilatation - Observation - Kind Of" : 
	"Has RV dilatation - Observation - Last".code 



define "Has RV dilatation - Observation - Value of" : 
	"Has RV dilatation - Observation - Last".value 



define "Has RV dilatation - Observation - Quantity Of" : 
	"Has RV dilatation - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/1e63bbb8-709a-300a-94e7-d47fcd830d85
define "Has RV dilatation - DiagnosticReport" : 
 /*FIXME*/ C3F.Verified( [ DiagnosticReport : CC."RV dilatation" ] )


define "Has RV dilatation - DiagnosticReport - Is" : 
	Exists( "Has RV dilatation - DiagnosticReport" ) 




//https://opencpg.org/ontology/covid19/ed/af583d30-4a8e-330d-a864-926fd59a0d0f
define "Has RV enlargement - Observation" : 
C3F.Verified( [ Observation : CC."RV enlargement" ] )


define "Has RV enlargement - Observation - Is" : 
	Exists( "Has RV enlargement - Observation" ) 



define "Has RV enlargement - Observation - Last" : 
	Last( "Has RV enlargement - Observation" ) 



define "Has RV enlargement - Observation - Kind Of" : 
	"Has RV enlargement - Observation - Last".code 



define "Has RV enlargement - Observation - Value of" : 
	"Has RV enlargement - Observation - Last".value 



define "Has RV enlargement - Observation - Quantity Of" : 
	"Has RV enlargement - Observation - Last".value 




//https://opencpg.org/ontology/covid19/ed/af583d30-4a8e-330d-a864-926fd59a0d0f
define "Has RV enlargement - DiagnosticReport" : 
 /*FIXME*/ C3F.Verified( [ DiagnosticReport : CC."RV enlargement" ] )


define "Has RV enlargement - DiagnosticReport - Is" : 
	Exists( "Has RV enlargement - DiagnosticReport" ) 



