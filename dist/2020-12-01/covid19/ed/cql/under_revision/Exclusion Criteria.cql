library "Exclusion Criteria"
using FHIR 


 define "On Advanced Respiratory Support - Is_0": 
	if "On Advanced Respiratory Support - Is_0_Precondition" then true else null

 define "On Advanced Respiratory Support - Is_0_Precondition": 
	"Currently Used Breathing Support Equipment - Kind of" = "Mechanical Ventilator"


 define "On Advanced Respiratory Support - Is_1": 
	if "On Advanced Respiratory Support - Is_1_Precondition" then true else null

 define "On Advanced Respiratory Support - Is_1_Precondition": 
	"Currently Used Breathing Support Equipment - Kind of" = "Non Invasive Positive Pressure Ventilator"


 define "On Advanced Respiratory Support - Is_2": 
	if "On Advanced Respiratory Support - Is_2_Precondition" then true else null

 define "On Advanced Respiratory Support - Is_2_Precondition": 
	"Currently Used Breathing Support Equipment - Kind of" = "High Flow Nasal Cannula"


 define "On Advanced Respiratory Support - Is_3": 
	if "On Advanced Respiratory Support - Is_3_Precondition" then false else null

 define "On Advanced Respiratory Support - Is_3_Precondition": 
	"Currently Used Breathing Support Equipment - Kind of" = "Nasal Cannula"


 define "On Advanced Respiratory Support - Is_4": 
	if "On Advanced Respiratory Support - Is_4_Precondition" then false else null

 define "On Advanced Respiratory Support - Is_4_Precondition": 
	"Currently Used Breathing Support Equipment - Kind of" = "None (Room Air)"




 define "On Vasopressors - Is": 	
	Situational Data Definitions.Covid19 Clinical Features.currentlyIsOnVasopressors




 define "On Hemodynamic Support - Is": 	
	On Advanced Respiratory Support - Is or On Vasopressors - Is

