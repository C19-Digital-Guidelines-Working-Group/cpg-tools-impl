library COVID19_ED_CPG_Situational_Data version '0.0.1'

using FHIR version '4.0.1'

include FHIRHelpers version '4.0.1'

include COVID19_ED_CPG_Retrieves version '0.0.1'

declare "Covid19 Patient": 
	Tuple {
		currentAge: "Age - Patient - Quantity Of",
		birthSex: "Gender - Patient - Kind Of",
		race: "Race - Patient - Kind Of"
	}
declare "Covid19 Clinical Features": 
	Tuple {
		mostRecentSPO2Value: "Most Recent O2 Saturation (Ambulatory) - Observation - Quantity Of",
		currentExertionalO2Saturation: "Most Recent O2 Saturation (Ambulatory) - Observation - Quantity Of",
		currentO2FlowValue: null,
		currentRRvalue: "Current Respiratory Rate - Observation - Quantity Of",
		currentSystolicBPValue: "Current Systolic BP - Observation - Quantity Of",
		currentHeartRateValue: "Current Heart Rate - Observation - Quantity Of",
		isLongTermCareResident: "Current Long Term Care Facility Resident - Observation - Is",
		hasDispneaPresent: "Current Persistent Dyspnea - Observation - Is",
		hasHemoptysisPresent: "Has Hemoptysis - Observation - Is",
		hasAlteredLOC: "Current Mental Status - Observation - Kind Of",
		hasObesityPresent: "Has Obesity - Condition - Is",
		hasMalignancyPresent: "Has Malignancy - Condition - Is",
		hasCOPDPresent: "Has Chronic Obstructive Pulmonary Disease (COPD) - Condition - Is",
		hasKidneyDiseasePresent: "Has Renal Disease - Condition - Is",
		hasHypertensionPresent: "Has Hypertension - Condition - Is",
		hasCerebrovascularDiseasePresent: "Has Cerebrovascular disease - Condition - Is",
		hasCardioVascularDiseasePresent: null,
		hasDIabetesMellitusPresent: "Has Diabetes Mellitus Type 1 - Condition - Is" or "Has Diabetes Mellitus Type 2 - Condition - Is",
		currentlyIsOnVasopressors: "On Vasopressin - MedicationStatement  - Is" or "On Vasopressin -  MedicationRequest - Is" or "On Epinephrine - MedicationStatement  - Is" or "On Epinephrine -  MedicationRequest - Is" or "On Dopamine - MedicationStatement  - Is" or "On Dopamine -  MedicationRequest - Is" or "On Norepinephrine - MedicationStatement  - Is" or "On Norepinephrine -  MedicationRequest - Is",
		currentlyUsedBreathingEquipment: null,
		mostRecentCovid19Test: null,
		isBreathingNormally: null,
		isBloodPressureUsual: null,
		isGenerallyHealthy: null,
		hasConditionWarrantingAdmission: null,
		hasConditionWarrantingWorkup: null
	}
declare "Covid19 Diagnostic Features": 
	Tuple {
		hasRVEnlargementPresent: "Has RV enlargement - Observation  - Is" or "Has RV enlargement -  DiagnosticReport - Is",
		hasBilateralPneumoniaPresent: "Has Bilateral Pneumonia - Observation  - Is" or "Has Bilateral Pneumonia -  DiagnosticReport - Is",
		currentCXRScoreValue: null,
		currentLactateValue: null,
		currentTroponinLevelValue: null,
		currentDDimerValue: "Most Recent D-dimer - Observation - Quantity Of",
		currentLymphocyteCountValue: "Most Recent Lymphocytes (#) - Observation - Quantity Of",
		currentLDHValue: "Most Recent Lactate Dehydrogenase - Observation - Quantity Of",
		currentCRPValue: "Most Recent C-Reactive Peptide - Observation - Quantity Of",
		currentCreatinineValue: null,
		currentALTValue: "Most Recent ALT - Observation - Quantity Of",
		currentASTValue: "Most Recent AST - Observation - Quantity Of",
		currentWBCCountValue: null,
		currentNeutrophilsCountValue: "Most Recent Neutrophils (#) - Observation - Quantity Of",
		currentPlateletCountValue: "Most Recent Platelets - Observation - Quantity Of",
		currentFerritinValue: "Most Recent Ferritin - Observation - Quantity Of"
	}
