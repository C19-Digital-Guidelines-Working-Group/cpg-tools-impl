library Disposition_-_Diagnostics version '0.0.1'



include COVID19_ED_CPG_Situational_Data version '0.0.1' called Situational Data Definitions

 /* Mapped from Decision 'Current Covid19 Severity - Kind of' | expression type = TDecisionTable */ 


 define "Current Covid19 Severity - Kind of_0": 
	Current Severity - C - Clinical.Current Severity of Covid19 - Kind of -- Clinical
 define "Current Covid19 Severity - Kind of_1": 
	Current Severity - D - Labs and Dx.Current Severity of Covid19 - Kind of -- Diagnostics
 define "Current Covid19 Severity - Kind of" :
	Coalesce( "Current Covid19 Severity - Kind of_0","Current Covid19 Severity - Kind of_1" )

