library "Inclusion Criteria"
using FHIR 


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




 define "Confirmed Covid19 - Is_0": 
	if "Confirmed Covid19 - Is_0_Precondition" then true else null

 define "Confirmed Covid19 - Is_0_Precondition": 
	"Having Covid19 - Belief in" = "present"


 define "Confirmed Covid19 - Is_1": 
	if "Confirmed Covid19 - Is_1_Precondition" then false else null

 define "Confirmed Covid19 - Is_1_Precondition": 
	"Having Covid19 - Belief in" = "unknown"


 define "Confirmed Covid19 - Is_2": 
	if "Confirmed Covid19 - Is_2_Precondition" then false else null

 define "Confirmed Covid19 - Is_2_Precondition": 
	"Having Covid19 - Belief in" = "suspected"


 define "Confirmed Covid19 - Is_3": 
	if "Confirmed Covid19 - Is_3_Precondition" then false else null

 define "Confirmed Covid19 - Is_3_Precondition": 
	"Having Covid19 - Belief in" = "absent"

