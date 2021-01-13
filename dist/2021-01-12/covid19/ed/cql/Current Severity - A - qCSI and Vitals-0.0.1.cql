library Current_Severity_-_A_-_qCSI_and_Vitals version '0.0.1'



include COVID19_ED_CPG_Situational_Data version '0.0.1' called Situational Data Definitions

 /* Mapped from Decision 'Current qCSI Score - Value of' | expression type = TDecisionTable */ 


 define "Current qCSI Score - Value of_0": 
	if "Current qCSI Score - Value of_0_Precondition" then 1 else 0

 define "Current qCSI Score - Value of_0_Precondition": 
	"Current Documented Respiratory Rate - Value of" between 23.0 and 28.0


 define "Current qCSI Score - Value of_1": 
	if "Current qCSI Score - Value of_1_Precondition" then 2 else 0

 define "Current qCSI Score - Value of_1_Precondition": 
	"Current Documented Respiratory Rate - Value of" = > 28


 define "Current qCSI Score - Value of_2": 
	if "Current qCSI Score - Value of_2_Precondition" then 2 else 0

 define "Current qCSI Score - Value of_2_Precondition": 
	"Recent Documented SpO2 - Min Value of" between 89.0 and 92.0


 define "Current qCSI Score - Value of_3": 
	if "Current qCSI Score - Value of_3_Precondition" then 5 else 0

 define "Current qCSI Score - Value of_3_Precondition": 
	"Recent Documented SpO2 - Min Value of" = <= 88


 define "Current qCSI Score - Value of_4": 
	if "Current qCSI Score - Value of_4_Precondition" then 4 else 0

 define "Current qCSI Score - Value of_4_Precondition": 
	"Current NC O2 Flow Setpoint - Value of" between 3.0 and 5.0


 define "Current qCSI Score - Value of_5": 
	if "Current qCSI Score - Value of_5_Precondition" then 5 else 0

 define "Current qCSI Score - Value of_5_Precondition": 
	"Current NC O2 Flow Setpoint - Value of" = >= 5


 define "Current qCSI Score - Value of_6": 
	0
 define "Current qCSI Score - Value of" :
	"Current qCSI Score - Value of_0" + "Current qCSI Score - Value of_1" + "Current qCSI Score - Value of_2" + "Current qCSI Score - Value of_3" + "Current qCSI Score - Value of_4" + "Current qCSI Score - Value of_5" + "Current qCSI Score - Value of_6"



 /* Mapped from Decision 'Current Documented Heart Rate - Value of' | expression type = TLiteralExpression */ 


 define "Current Documented Heart Rate - Value of": 	
	Situational Data Definitions.Covid19 Clinical Features.currentHeartRateValue



 /* Mapped from Decision 'Current Documented Respiratory Rate - Value of' | expression type = TLiteralExpression */ 


 define "Current Documented Respiratory Rate - Value of": 	
	Situational Data Definitions.Covid19 Clinical Features.currentRRvalue



 /* Mapped from Decision 'Current Documented Systolic Blood Pressure - Value of' | expression type = TLiteralExpression */ 


 define "Current Documented Systolic Blood Pressure - Value of": 	
	Situational Data Definitions.Covid19 Clinical Features.currentSystolicBPValue



 /* Mapped from Decision 'Recent Documented SpO2 - Min Value of' | expression type = TLiteralExpression */ 


 define "Recent Documented SpO2 - Min Value of": 	
	Situational Data Definitions.Covid19 Clinical Features.mostRecentSPO2Value



 /* Mapped from Decision 'Current NC O2 Flow Setpoint - Value of' | expression type = TLiteralExpression */ 


 define "Current NC O2 Flow Setpoint - Value of": 	
	Situational Data Definitions.Covid19 Clinical Features.currentO2FlowValue



 /* Mapped from Decision 'Current Severity of Covid19 - Kind of -- qCSI' | expression type = TDecisionTable */ 


 define "Current Severity of Covid19 - Kind of -- qCSI_0": 
	if "Current Severity of Covid19 - Kind of -- qCSI_0_Precondition" then "Critical" else null

 define "Current Severity of Covid19 - Kind of -- qCSI_0_Precondition": 
	"Current qCSI Score - Value of" = >= 9


 define "Current Severity of Covid19 - Kind of -- qCSI_1": 
	if "Current Severity of Covid19 - Kind of -- qCSI_1_Precondition" then "Severe" else null

 define "Current Severity of Covid19 - Kind of -- qCSI_1_Precondition": 
	"Current qCSI Score - Value of" between 6.0 and 8.0


 define "Current Severity of Covid19 - Kind of -- qCSI_2": 
	if "Current Severity of Covid19 - Kind of -- qCSI_2_Precondition" then "Moderate" else null

 define "Current Severity of Covid19 - Kind of -- qCSI_2_Precondition": 
	"Current qCSI Score - Value of" between 3.0 and 5.0


 define "Current Severity of Covid19 - Kind of -- qCSI_3": 
	if "Current Severity of Covid19 - Kind of -- qCSI_3_Precondition" then "Mild at Risk" else null

 define "Current Severity of Covid19 - Kind of -- qCSI_3_Precondition": 
	"Current qCSI Score - Value of" between 1.0 and 2.0


 define "Current Severity of Covid19 - Kind of -- qCSI_4": 
	if "Current Severity of Covid19 - Kind of -- qCSI_4_Precondition" then "Mild Low Risk" else null

 define "Current Severity of Covid19 - Kind of -- qCSI_4_Precondition": 
	"Current qCSI Score - Value of" = 0


 define "Current Severity of Covid19 - Kind of -- qCSI" :
	Coalesce( "Current Severity of Covid19 - Kind of -- qCSI_0","Current Severity of Covid19 - Kind of -- qCSI_1","Current Severity of Covid19 - Kind of -- qCSI_2","Current Severity of Covid19 - Kind of -- qCSI_3","Current Severity of Covid19 - Kind of -- qCSI_4" )



 /* Mapped from Decision 'Current Severity of Covid19 - Kind of -- Vitals' | expression type = TDecisionTable */ 


 define "Current Severity of Covid19 - Kind of -- Vitals_0": 
	if "Current Severity of Covid19 - Kind of -- Vitals_0_Precondition" then "Critical" else null

 define "Current Severity of Covid19 - Kind of -- Vitals_0_Precondition": 
	"Current Documented Systolic Blood Pressure - Value of" = < 90


 define "Current Severity of Covid19 - Kind of -- Vitals_1": 
	if "Current Severity of Covid19 - Kind of -- Vitals_1_Precondition" then "Moderate" else null

 define "Current Severity of Covid19 - Kind of -- Vitals_1_Precondition": 
	"Current Documented Heart Rate - Value of" = >= 121


 define "Current Severity of Covid19 - Kind of -- Vitals_2": 
	if "Current Severity of Covid19 - Kind of -- Vitals_2_Precondition" then "Mild at Risk" else null

 define "Current Severity of Covid19 - Kind of -- Vitals_2_Precondition": 
	"Current Documented Heart Rate - Value of" between 100.0 and 120.0


 define "Current Severity of Covid19 - Kind of -- Vitals_3": 
	if "Current Severity of Covid19 - Kind of -- Vitals_3_Precondition" then "Mild Low Risk" else null

 define "Current Severity of Covid19 - Kind of -- Vitals_3_Precondition": 
	"Current Documented Heart Rate - Value of" = <= 99


 define "Current Severity of Covid19 - Kind of -- Vitals" :
	Coalesce( "Current Severity of Covid19 - Kind of -- Vitals_0","Current Severity of Covid19 - Kind of -- Vitals_1","Current Severity of Covid19 - Kind of -- Vitals_2","Current Severity of Covid19 - Kind of -- Vitals_3" )

