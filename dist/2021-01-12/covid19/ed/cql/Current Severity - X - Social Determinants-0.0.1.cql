library Current_Severity_-_X_-_Social_Determinants version '0.0.1'



include COVID19_ED_CPG_Situational_Data version '0.0.1' called Situational Data Definitions

 /* Mapped from Decision 'Current Covid Severity - Kind of -- Social Factors' | expression type = TDecisionTable */ 


 define "Current Covid Severity - Kind of -- Social Factors_0": 
	if "Current Covid Severity - Kind of -- Social Factors_0_Precondition" then "Moderate" else null

 define "Current Covid Severity - Kind of -- Social Factors_0_Precondition": 
	IsTrue( "Long Term Care Resident - Is" )


 define "Current Covid Severity - Kind of -- Social Factors_1": 
	if "Current Covid Severity - Kind of -- Social Factors_1_Precondition" then null else null

 define "Current Covid Severity - Kind of -- Social Factors_1_Precondition": 
	IsFalse( "Long Term Care Resident - Is" )

