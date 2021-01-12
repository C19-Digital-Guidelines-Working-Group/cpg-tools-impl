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
		currentRRvalue: null,
		currentSystolicBPValue: null,
		currentHeartRateValue: null,
		isLongTermCareResident: "Current Long Term Care Facility Resident - Observation - Is",
		hasDispneaPresent: null,
		hasHemoptysisPresent: null,
		hasAlteredLOC: null,
		hasObesityPresent: null,
		hasMalignancyPresent: null,
		hasCOPDPresent: null,
		hasKidneyDiseasePresent: null,
		hasHypertensionPresent: null,
		hasCerebrovascularDiseasePresent: null,
		hasCardioVascularDiseasePresent: null,
		hasDIabetesMellitusPresent: null,
		currentlyIsOnVasopressors: null,
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
		currentDDimerValue: null,
		currentLymphocyteCountValue: null,
		currentLDHValue: null,
		currentCRPValue: "Most Recent C-Reactive Peptide - Observation - Quantity Of",
		currentCreatinineValue: null,
		currentALTValue: "Most Recent ALT - Observation - Quantity Of",
		currentASTValue: "Most Recent AST - Observation - Quantity Of",
		currentWBCCountValue: null,
		currentNeutrophilsCountValue: "Most Recent Neutrophils (#) - Observation - Quantity Of",
		currentPlateletCountValue: "Most Recent Platelets - Observation - Quantity Of",
		currentFerritinValue: "Most Recent Ferritin - Observation - Quantity Of"
	}
