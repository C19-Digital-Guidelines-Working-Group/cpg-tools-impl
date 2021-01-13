library Exclusion_Criteria version '0.0.1'



include COVID19_ED_CPG_Situational_Data version '0.0.1' called Situational Data Definitions

 /* Mapped from Decision 'On Advanced Respiratory Support - Is' | expression type = TDecisionTable */ 


 define "On Advanced Respiratory Support - Is_0": 
	if "On Advanced Respiratory Support - Is_0_Precondition" then true else false

 define "On Advanced Respiratory Support - Is_0_Precondition": 
	"Currently Used Breathing Support Equipment - Kind of" = "Mechanical Ventilator"


 define "On Advanced Respiratory Support - Is_1": 
	if "On Advanced Respiratory Support - Is_1_Precondition" then true else false

 define "On Advanced Respiratory Support - Is_1_Precondition": 
	"Currently Used Breathing Support Equipment - Kind of" = "Non Invasive Positive Pressure Ventilator"


 define "On Advanced Respiratory Support - Is_2": 
	if "On Advanced Respiratory Support - Is_2_Precondition" then true else false

 define "On Advanced Respiratory Support - Is_2_Precondition": 
	"Currently Used Breathing Support Equipment - Kind of" = "High Flow Nasal Cannula"


 define "On Advanced Respiratory Support - Is_3": 
	if "On Advanced Respiratory Support - Is_3_Precondition" then false else false

 define "On Advanced Respiratory Support - Is_3_Precondition": 
	"Currently Used Breathing Support Equipment - Kind of" = "Nasal Cannula"


 define "On Advanced Respiratory Support - Is_4": 
	if "On Advanced Respiratory Support - Is_4_Precondition" then false else false

 define "On Advanced Respiratory Support - Is_4_Precondition": 
	"Currently Used Breathing Support Equipment - Kind of" = "None (Room Air)"



 /* Mapped from Decision 'On Vasopressors - Is' | expression type = TLiteralExpression */ 


 define "On Vasopressors - Is": 	
	Situational Data Definitions.Covid19 Clinical Features.currentlyIsOnVasopressors



 /* Mapped from Decision 'Currently Used Breathing Support Equipment - Kind of' | expression type = TLiteralExpression */ 


 define "Currently Used Breathing Support Equipment - Kind of": 	
	Situational Data Definitions.Covid19 Clinical Features.currentlyUsedBreathingEquipment



 /* Mapped from Decision 'On Hemodynamic Support - Is' | expression type = TLiteralExpression */ 


 define "On Hemodynamic Support - Is": 	
	On Advanced Respiratory Support - Is or On Vasopressors - Is

