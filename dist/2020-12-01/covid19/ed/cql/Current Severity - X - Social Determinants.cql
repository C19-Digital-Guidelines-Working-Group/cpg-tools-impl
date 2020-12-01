library "Current Severity - X - Social Determinants"
using FHIR 


 define "Current Covid Severity - Kind of -- Social Factors_0": 
	if "Current Covid Severity - Kind of -- Social Factors_0_Precondition" then "Moderate" else null

 define "Current Covid Severity - Kind of -- Social Factors_0_Precondition": 
	IsTrue( "Long Term Care Resident - Is" )


 define "Current Covid Severity - Kind of -- Social Factors_1": 
	if "Current Covid Severity - Kind of -- Social Factors_1_Precondition" then null else null

 define "Current Covid Severity - Kind of -- Social Factors_1_Precondition": 
	IsFalse( "Long Term Care Resident - Is" )

