library Current_Severity_-_C_-_Clinical version '0.0.1'



include COVID19_ED_CPG_Situational_Data version '0.0.1' called Situational Data Definitions

 /* Mapped from Decision 'Current Severity of Covid19 - Kind of -- Clinical' | expression type = TDecisionTable */ 


 define "Current Severity of Covid19 - Kind of -- Clinical_0": 
	if "Current Severity of Covid19 - Kind of -- Clinical_0_Precondition" then "Critical" else null

 define "Current Severity of Covid19 - Kind of -- Clinical_0_Precondition": 
	IsTrue( "Exclusion Criteria"."On Hemodynamic Support - Is" )


 define "Current Severity of Covid19 - Kind of -- Clinical_1": 
	if "Current Severity of Covid19 - Kind of -- Clinical_1_Precondition" then Current Severity of Covid19 - Kind of - Manifestations else null

 define "Current Severity of Covid19 - Kind of -- Clinical_1_Precondition": 
	IsFalse( "Exclusion Criteria"."On Hemodynamic Support - Is" )


 define "Current Severity of Covid19 - Kind of -- Clinical" :
	Coalesce( "Current Severity of Covid19 - Kind of -- Clinical_0","Current Severity of Covid19 - Kind of -- Clinical_1" )



 /* Mapped from Decision 'Current Severity of Covid19 - Kind of - Manifestations' | expression type = TDecisionTable */ 


 define "Current Severity of Covid19 - Kind of - Manifestations_0": 
	Current Severity - A - qCSI and Vitals.Current Severity of Covid19 - Kind of -- qCSI
 define "Current Severity of Covid19 - Kind of - Manifestations_1": 
	Current Severity - A - qCSI and Vitals.Current Severity of Covid19 - Kind of -- Vitals
 define "Current Severity of Covid19 - Kind of - Manifestations_2": 
	Current Severity - B - Risks and Symptoms.Current Severity of Covid-19 - Kind of -- Symptoms
 define "Current Severity of Covid19 - Kind of - Manifestations_3": 
	Current Severity - B - Risks and Symptoms.Current Severity of Covid19 - Kind of -- Risk Factors
 define "Current Severity of Covid19 - Kind of - Manifestations_4": 
	Current Severity - X - Social Determinants.Current Covid Severity - Kind of -- Social Factors
 define "Current Severity of Covid19 - Kind of - Manifestations" :
	Coalesce( "Current Severity of Covid19 - Kind of - Manifestations_0","Current Severity of Covid19 - Kind of - Manifestations_1","Current Severity of Covid19 - Kind of - Manifestations_2","Current Severity of Covid19 - Kind of - Manifestations_3","Current Severity of Covid19 - Kind of - Manifestations_4" )

