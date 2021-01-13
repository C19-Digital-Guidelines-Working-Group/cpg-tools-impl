library Inclusion_Criteria version '0.0.1'



include COVID19_ED_CPG_Situational_Data version '0.0.1' called Situational Data Definitions

 /* Mapped from Decision 'Eligible for Covid ED Management - Is' | expression type = TContext */ 


 define "adult": 	
	Age - Value of > 18


 define "covidNotExcluded_0": 
	if "covidNotExcluded_0_Precondition" then true else null

 define "covidNotExcluded_0_Precondition": 
	"Having Covid19 - Belief in" = "present"


 define "covidNotExcluded_1": 
	if "covidNotExcluded_1_Precondition" then true else null

 define "covidNotExcluded_1_Precondition": 
	"Having Covid19 - Belief in" = "suspected"


 define "covidNotExcluded_2": 
	if "covidNotExcluded_2_Precondition" then false else null

 define "covidNotExcluded_2_Precondition": 
	"Having Covid19 - Belief in" = "absent"


 define "covidNotExcluded_3": 
	if "covidNotExcluded_3_Precondition" then true else null

 define "covidNotExcluded_3_Precondition": 
	"Having Covid19 - Belief in" = "unknown"




 define "Eligible for Covid ED Management - Is": 	
	adult and covidNotExcluded



 /* Mapped from Decision 'Age - Value of' | expression type = TLiteralExpression */ 


 define "Age - Value of": 	
	Situational Data Definitions.Covid19 Patient.currentAge



 /* Mapped from Decision 'Having Covid19 - Belief in' | expression type = TDecisionTable */ 


 define "Having Covid19 - Belief in_0": 
	if "Having Covid19 - Belief in_0_Precondition" then "present" else null

 define "Having Covid19 - Belief in_0_Precondition": 
	IsTrue( "Has Positive Covd19 Test - Is" )


 define "Having Covid19 - Belief in_1": 
	if "Having Covid19 - Belief in_1_Precondition" then "suspected" else null

 define "Having Covid19 - Belief in_1_Precondition": 
	IsTrue( "Has Pending Covid19 Test - Is" ) and IsFalse( "Has Positive Covd19 Test - Is" )


 define "Having Covid19 - Belief in_2": 
	if "Having Covid19 - Belief in_2_Precondition" then "unknown" else null

 define "Having Covid19 - Belief in_2_Precondition": 
	IsFalse( "Has Pending Covid19 Test - Is" ) and IsFalse( "Has Positive Covd19 Test - Is" )



 /* Mapped from Decision 'Confirmed Covid19 - Is' | expression type = TDecisionTable */ 


 define "Confirmed Covid19 - Is_0": 
	if "Confirmed Covid19 - Is_0_Precondition" then true else false

 define "Confirmed Covid19 - Is_0_Precondition": 
	"Having Covid19 - Belief in" = "present"


 define "Confirmed Covid19 - Is_1": 
	if "Confirmed Covid19 - Is_1_Precondition" then false else false

 define "Confirmed Covid19 - Is_1_Precondition": 
	"Having Covid19 - Belief in" = "unknown"


 define "Confirmed Covid19 - Is_2": 
	if "Confirmed Covid19 - Is_2_Precondition" then false else false

 define "Confirmed Covid19 - Is_2_Precondition": 
	"Having Covid19 - Belief in" = "suspected"


 define "Confirmed Covid19 - Is_3": 
	if "Confirmed Covid19 - Is_3_Precondition" then false else false

 define "Confirmed Covid19 - Is_3_Precondition": 
	"Having Covid19 - Belief in" = "absent"

