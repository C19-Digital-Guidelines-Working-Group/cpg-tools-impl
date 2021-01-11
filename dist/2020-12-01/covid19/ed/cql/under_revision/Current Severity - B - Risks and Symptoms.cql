library "Current Severity - B - Risks and Symptoms"
using FHIR 


 define "Current Severity of Covid-19 - Kind of -- Symptoms_0": 
	if "Current Severity of Covid-19 - Kind of -- Symptoms_0_Precondition" then "Critical" else null

 define "Current Severity of Covid-19 - Kind of -- Symptoms_0_Precondition": 
	IsTrue( "Has Altered LOC - Is" )


 define "Current Severity of Covid-19 - Kind of -- Symptoms_1": 
	if "Current Severity of Covid-19 - Kind of -- Symptoms_1_Precondition" then "Severe" else null

 define "Current Severity of Covid-19 - Kind of -- Symptoms_1_Precondition": 
	IsTrue( "Has Hemoptysis - Is" )


 define "Current Severity of Covid-19 - Kind of -- Symptoms_2": 
	if "Current Severity of Covid-19 - Kind of -- Symptoms_2_Precondition" then "Moderate" else null

 define "Current Severity of Covid-19 - Kind of -- Symptoms_2_Precondition": 
	IsTrue( "Has Dyspnea - Is" )


 define "Current Severity of Covid-19 - Kind of -- Symptoms_3": 
	"Mild Low Risk"
 define "Current Severity of Covid-19 - Kind of -- Symptoms" :
	Coalesce( "Current Severity of Covid-19 - Kind of -- Symptoms_0","Current Severity of Covid-19 - Kind of -- Symptoms_1","Current Severity of Covid-19 - Kind of -- Symptoms_2","Current Severity of Covid-19 - Kind of -- Symptoms_3" )




 define "Has Exacerbating Factor of Covid19 - Count of_0": 
	if "Has Exacerbating Factor of Covid19 - Count of_0_Precondition" then 1 else null

 define "Has Exacerbating Factor of Covid19 - Count of_0_Precondition": 
	"Current Patient Age - Value of" = > 60


 define "Has Exacerbating Factor of Covid19 - Count of_1": 
	if "Has Exacerbating Factor of Covid19 - Count of_1_Precondition" then 1 else null

 define "Has Exacerbating Factor of Covid19 - Count of_1_Precondition": 
	"Patient Sex - Kind of" = "Male"


 define "Has Exacerbating Factor of Covid19 - Count of_2": 
	if "Has Exacerbating Factor of Covid19 - Count of_2_Precondition" then 1 else null

 define "Has Exacerbating Factor of Covid19 - Count of_2_Precondition": 
	"Patient Race - Kind of" = "Black or African American"


 define "Has Exacerbating Factor of Covid19 - Count of_3": 
	if "Has Exacerbating Factor of Covid19 - Count of_3_Precondition" then 1 else null

 define "Has Exacerbating Factor of Covid19 - Count of_3_Precondition": 
	IsTrue( "Has Diabetes Mellitus - Is" )


 define "Has Exacerbating Factor of Covid19 - Count of_4": 
	if "Has Exacerbating Factor of Covid19 - Count of_4_Precondition" then 1 else null

 define "Has Exacerbating Factor of Covid19 - Count of_4_Precondition": 
	IsTrue( "Has Cardiovascular Disease - Is" )


 define "Has Exacerbating Factor of Covid19 - Count of_5": 
	if "Has Exacerbating Factor of Covid19 - Count of_5_Precondition" then 1 else null

 define "Has Exacerbating Factor of Covid19 - Count of_5_Precondition": 
	IsTrue( "Has Chronic Obstructive Pulmonary Disease - Is" )


 define "Has Exacerbating Factor of Covid19 - Count of_6": 
	if "Has Exacerbating Factor of Covid19 - Count of_6_Precondition" then 1 else null

 define "Has Exacerbating Factor of Covid19 - Count of_6_Precondition": 
	IsTrue( "Has Hypertension - Is" )


 define "Has Exacerbating Factor of Covid19 - Count of_7": 
	if "Has Exacerbating Factor of Covid19 - Count of_7_Precondition" then 1 else null

 define "Has Exacerbating Factor of Covid19 - Count of_7_Precondition": 
	IsTrue( "Has Malignancy - Is" )


 define "Has Exacerbating Factor of Covid19 - Count of_8": 
	if "Has Exacerbating Factor of Covid19 - Count of_8_Precondition" then 1 else null

 define "Has Exacerbating Factor of Covid19 - Count of_8_Precondition": 
	IsTrue( "Has Kidney Disease - Is" )


 define "Has Exacerbating Factor of Covid19 - Count of_9": 
	if "Has Exacerbating Factor of Covid19 - Count of_9_Precondition" then 1 else null

 define "Has Exacerbating Factor of Covid19 - Count of_9_Precondition": 
	IsTrue( "Has Obesity - Is" )


 define "Has Exacerbating Factor of Covid19 - Count of_10": 
	if "Has Exacerbating Factor of Covid19 - Count of_10_Precondition" then 1 else null

 define "Has Exacerbating Factor of Covid19 - Count of_10_Precondition": 
	IsTrue( "Has Cerebrovascular Disease - Is" )


 define "Has Exacerbating Factor of Covid19 - Count of" :
	"Has Exacerbating Factor of Covid19 - Count of_0" + "Has Exacerbating Factor of Covid19 - Count of_1" + "Has Exacerbating Factor of Covid19 - Count of_2" + "Has Exacerbating Factor of Covid19 - Count of_3" + "Has Exacerbating Factor of Covid19 - Count of_4" + "Has Exacerbating Factor of Covid19 - Count of_5" + "Has Exacerbating Factor of Covid19 - Count of_6" + "Has Exacerbating Factor of Covid19 - Count of_7" + "Has Exacerbating Factor of Covid19 - Count of_8" + "Has Exacerbating Factor of Covid19 - Count of_9" + "Has Exacerbating Factor of Covid19 - Count of_10"




 define "Current Severity of Covid19 - Kind of -- Risk Factors_0": 
	if "Current Severity of Covid19 - Kind of -- Risk Factors_0_Precondition" then "Mild at Risk" else null

 define "Current Severity of Covid19 - Kind of -- Risk Factors_0_Precondition": 
	"Has Exacerbating Factor of Covid19 - Count of" = >=2


 define "Current Severity of Covid19 - Kind of -- Risk Factors_1": 
	if "Current Severity of Covid19 - Kind of -- Risk Factors_1_Precondition" then "Mild Low Risk" else null

 define "Current Severity of Covid19 - Kind of -- Risk Factors_1_Precondition": 
	"Has Exacerbating Factor of Covid19 - Count of" between 0.0 and 1.0

