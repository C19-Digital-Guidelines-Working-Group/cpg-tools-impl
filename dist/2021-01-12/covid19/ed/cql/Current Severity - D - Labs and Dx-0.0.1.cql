library Current_Severity_-_D_-_Labs_and_Dx version '0.0.1'



include COVID19_ED_CPG_Situational_Data version '0.0.1' called Situational Data Definitions

 /* Mapped from Decision 'Current Troponin - Value of' | expression type = TLiteralExpression */ 


 define "Current Troponin - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentTroponinLevelValue



 /* Mapped from Decision 'Current D-Dimer - Value of' | expression type = TLiteralExpression */ 


 define "Current D-Dimer - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentDDimerValue



 /* Mapped from Decision 'Current Lactate - Value of' | expression type = TLiteralExpression */ 


 define "Current Lactate - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentLactateValue



 /* Mapped from Decision 'Current LDH - Value of' | expression type = TLiteralExpression */ 


 define "Current LDH - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentLDHValue



 /* Mapped from Decision 'Current CRP - Value of' | expression type = TLiteralExpression */ 


 define "Current CRP - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentCRPValue



 /* Mapped from Decision 'Current Lymphocyte Count - Value of' | expression type = TLiteralExpression */ 


 define "Current Lymphocyte Count - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentLymphocyteCountValue



 /* Mapped from Decision 'Current CXR Score - Value of' | expression type = TLiteralExpression */ 


 define "Current CXR Score - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentCXRScoreValue



 /* Mapped from Decision 'Current Severity of Covid19 - Kind of -- Diagnostics' | expression type = TDecisionTable */ 


 define "Current Severity of Covid19 - Kind of -- Diagnostics_0": 
	if "Current Severity of Covid19 - Kind of -- Diagnostics_0_Precondition" then "Critical" else null

 define "Current Severity of Covid19 - Kind of -- Diagnostics_0_Precondition": 
	"Current Lactate - Value of" = >= 4


 define "Current Severity of Covid19 - Kind of -- Diagnostics_1": 
	if "Current Severity of Covid19 - Kind of -- Diagnostics_1_Precondition" then "Severe" else null

 define "Current Severity of Covid19 - Kind of -- Diagnostics_1_Precondition": 
	"Current CXR Score - Value of" = >= 3


 define "Current Severity of Covid19 - Kind of -- Diagnostics_2": 
	if "Current Severity of Covid19 - Kind of -- Diagnostics_2_Precondition" then "Severe" else null

 define "Current Severity of Covid19 - Kind of -- Diagnostics_2_Precondition": 
	IsTrue( "Has Bilateral Pneumonia - Is" )


 define "Current Severity of Covid19 - Kind of -- Diagnostics_3": 
	if "Current Severity of Covid19 - Kind of -- Diagnostics_3_Precondition" then "Severe" else null

 define "Current Severity of Covid19 - Kind of -- Diagnostics_3_Precondition": 
	IsTrue( "Has RV Enlargement - Is" )


 define "Current Severity of Covid19 - Kind of -- Diagnostics_4": 
	if "Current Severity of Covid19 - Kind of -- Diagnostics_4_Precondition" then "Severe" else null

 define "Current Severity of Covid19 - Kind of -- Diagnostics_4_Precondition": 
	"Abnormal Labs - Count of" = >= 1


 define "Current Severity of Covid19 - Kind of -- Diagnostics_5": 
	if "Current Severity of Covid19 - Kind of -- Diagnostics_5_Precondition" then "Moderate" else null

 define "Current Severity of Covid19 - Kind of -- Diagnostics_5_Precondition": 
	"Current CXR Score - Value of" = 2


 define "Current Severity of Covid19 - Kind of -- Diagnostics_6": 
	"Mild Low Risk"
 define "Current Severity of Covid19 - Kind of -- Diagnostics" :
	Coalesce( "Current Severity of Covid19 - Kind of -- Diagnostics_0","Current Severity of Covid19 - Kind of -- Diagnostics_1","Current Severity of Covid19 - Kind of -- Diagnostics_2","Current Severity of Covid19 - Kind of -- Diagnostics_3","Current Severity of Covid19 - Kind of -- Diagnostics_4","Current Severity of Covid19 - Kind of -- Diagnostics_5","Current Severity of Covid19 - Kind of -- Diagnostics_6" )



 /* Mapped from Decision 'Abnormal Labs - Count of' | expression type = TContext */ 


 define "Abnormal Labs_0": 
	if "Abnormal Labs_0_Precondition" then "Elevated Troponin" else null

 define "Abnormal Labs_0_Precondition": 
	"Current Troponin - Value of" = > 99


 define "Abnormal Labs_1": 
	if "Abnormal Labs_1_Precondition" then "Elevated D-Dimer" else null

 define "Abnormal Labs_1_Precondition": 
	"Current D-Dimer - Value of" = >= 1


 define "Abnormal Labs_2": 
	if "Abnormal Labs_2_Precondition" then "Lymphopenia" else null

 define "Abnormal Labs_2_Precondition": 
	"Current Lymphocyte Count - Value of" = < 0.8


 define "Abnormal Labs_3": 
	if "Abnormal Labs_3_Precondition" then "Low LDH" else null

 define "Abnormal Labs_3_Precondition": 
	"Current LDH - Value of" = > 250


 define "Abnormal Labs_4": 
	if "Abnormal Labs_4_Precondition" then "Elevated CRP" else null

 define "Abnormal Labs_4_Precondition": 
	"Current CRP - Value of" = >= 125


 define "Abnormal Labs_5": 
	if "Abnormal Labs_5_Precondition" then "Elevated Creatinine" else null

 define "Abnormal Labs_5_Precondition": 
	"Current Creatinine - Value of" = > 133


 define "Abnormal Labs_6": 
	if "Abnormal Labs_6_Precondition" then "Elevated ALT" else null

 define "Abnormal Labs_6_Precondition": 
	"Current ALT - Value of" = > 40


 define "Abnormal Labs_7": 
	if "Abnormal Labs_7_Precondition" then "Elevated AST" else null

 define "Abnormal Labs_7_Precondition": 
	"Current AST - Value of" = > 40


 define "Abnormal Labs_8": 
	if "Abnormal Labs_8_Precondition" then "Neutrophils" else null

 define "Abnormal Labs_8_Precondition": 
	"Current Neutrophils Count - Value of" = > 8000


 define "Abnormal Labs_9": 
	if "Abnormal Labs_9_Precondition" then "Thrombocytopenia" else null

 define "Abnormal Labs_9_Precondition": 
	"Current Platelet Count - Value of" = < 150000


 define "Abnormal Labs_10": 
	if "Abnormal Labs_10_Precondition" then "Elevated WBC" else null

 define "Abnormal Labs_10_Precondition": 
	"Current WBC Count - Value of" = > 10000


 define "Abnormal Labs_11": 
	if "Abnormal Labs_11_Precondition" then "Elevated Ferritin" else null

 define "Abnormal Labs_11_Precondition": 
	"Current Ferritin - Value of" = > 300


 define "Abnormal Labs" :
	"Abnormal Labs_0","Abnormal Labs_1","Abnormal Labs_2","Abnormal Labs_3","Abnormal Labs_4","Abnormal Labs_5","Abnormal Labs_6","Abnormal Labs_7","Abnormal Labs_8","Abnormal Labs_9","Abnormal Labs_10","Abnormal Labs_11"




 define "Abnormal Labs - Count of": 	
	count(Abnormal Labs)



 /* Mapped from Decision 'Current Creatinine - Value of' | expression type = TLiteralExpression */ 


 define "Current Creatinine - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentCreatinineValue



 /* Mapped from Decision 'Current ALT - Value of' | expression type = TLiteralExpression */ 


 define "Current ALT - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentALTValue



 /* Mapped from Decision 'Current AST - Value of' | expression type = TLiteralExpression */ 


 define "Current AST - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentASTValue



 /* Mapped from Decision 'Current WBC Count - Value of' | expression type = TLiteralExpression */ 


 define "Current WBC Count - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentWBCCountValue



 /* Mapped from Decision 'Current Neutrophils Count - Value of' | expression type = TLiteralExpression */ 


 define "Current Neutrophils Count - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentNeutrophilsCountValue



 /* Mapped from Decision 'Current Platelet Count - Value of' | expression type = TLiteralExpression */ 


 define "Current Platelet Count - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentPlateletCountValue



 /* Mapped from Decision 'Current Ferritin - Value of' | expression type = TLiteralExpression */ 


 define "Current Ferritin - Value of": 	
	Situational Data Definitions.Covid19 Diagnostic Features.currentFerritinValue

