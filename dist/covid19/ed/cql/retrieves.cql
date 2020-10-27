library COVID19_ED version '0.0.1'

using FHIR

codesystem "HCPCS": 'urn:oid:2.16.840.1.113883.6.14'
codesystem "CPT": 'http://www.ama-assn.org/go/cpt'
codesystem "ICD-10-CM": 'http://hl7.org/fhir/sid/icd-10-cm'
codesystem "LOINC": 'http://loinc.org'
codesystem "SNOMED": 'http://snomed.info/sct'
codesystem "ICD-10-PCS": 'http://hl7.org/fhir/sid/icd-10-pcs'
codesystem "RxNorm": 'http://www.nlm.nih.gov/research/umls/rxnorm'

code "LOINC_9269_2" : '9269-2' from LOINC
code "ICD-10-CM_R_04_2" : 'R04.2' from ICD-10-CM
code "CPT_94760" : '94760' from CPT
code "CPT_94761" : '94761' from CPT
code "LOINC_9279_1" : '9279-1' from LOINC
code "LOINC_2544737_0" : '2544737.0' from LOINC
code "LOINC_8480_6" : '8480-6' from LOINC
code "LOINC_8462_4" : '8462-4' from LOINC
code "LOINC_3150_0" : '3150-0' from LOINC
code "LOINC_3151_8" : '3151-8' from LOINC
code "LOINC_71835_3" : '71835-3' from LOINC
code "HCPCS_A4615" : 'A4615' from HCPCS
code "CPT_94660_0" : '94660.0' from CPT
code "ICD-10-PCS_5_A_09357" : '5A09357' from ICD-10-PCS
code "ICD-10-PCS_5_A_09358" : '5A09358' from ICD-10-PCS
code "ICD-10-PCS_5_A_09457" : '5A09457' from ICD-10-PCS
code "ICD-10-PCS_5_A_09458" : '5A09458' from ICD-10-PCS
code "ICD-10-PCS_5_A_09557" : '5A09557' from ICD-10-PCS
code "ICD-10-PCS_5_A_09558" : '5A09558' from ICD-10-PCS
code "HCPCS_J1250" : 'J1250' from HCPCS
code "RxNorm_3616_0" : '3616.0' from RxNorm
code "HCPCS_J0171" : 'J0171' from HCPCS
code "RxNorm_3992_0" : '3992.0' from RxNorm
code "RxNorm_7512_0" : '7512.0' from RxNorm
code "RxNorm_11149_0" : '11149.0' from RxNorm
code "HCPCS_J1265" : 'J1265' from HCPCS
code "RxNorm_3628_0" : '3628.0' from RxNorm
code "HCPCS_U0002" : 'U0002' from HCPCS
code "CPT_84484_0" : '84484.0' from CPT
code "LOINC_10839_9" : '10839-9' from LOINC
code "LOINC_42757_5" : '42757-5' from LOINC
code "LOINC_49563_0" : '49563-0' from LOINC
code "LOINC_76399_5" : '76399-5' from LOINC
code "CPT_85379_0" : '85379.0' from CPT
code "LOINC_29280_5" : '29280-5' from LOINC
code "LOINC_38898_3" : '38898-3' from LOINC
code "LOINC_48065_7" : '48065-7' from LOINC
code "LOINC_48066_5" : '48066-5' from LOINC
code "LOINC_7799_0" : '7799-0' from LOINC
code "LOINC_91556_1" : '91556-1' from LOINC
code "CPT_83605_0" : '83605.0' from CPT
code "LOINC_14118_4" : '14118-4' from LOINC
code "LOINC_19239_3" : '19239-3' from LOINC
code "LOINC_2518_9" : '2518-9' from LOINC
code "LOINC_2519_7" : '2519-7' from LOINC
code "LOINC_2524_7" : '2524-7' from LOINC
code "LOINC_30241_4" : '30241-4' from LOINC
code "LOINC_30242_2" : '30242-2' from LOINC
code "LOINC_32132_3" : '32132-3' from LOINC
code "LOINC_32133_1" : '32133-1' from LOINC
code "LOINC_32693_4" : '32693-4' from LOINC
code "LOINC_51829_0" : '51829-0' from LOINC
code "LOINC_55932_8" : '55932-8' from LOINC
code "LOINC_59032_3" : '59032-3' from LOINC
code "LOINC_75366_5" : '75366-5' from LOINC
code "LOINC_19240_1" : '19240-1' from LOINC
code "CPT_83615_0" : '83615.0' from CPT
code "LOINC_14804_9" : '14804-9' from LOINC
code "LOINC_14805_6" : '14805-6' from LOINC
code "CPT_86140_0" : '86140.0' from CPT
code "LOINC_11039_5" : '11039-5' from LOINC
code "LOINC_14634_0" : '14634-0' from LOINC
code "LOINC_16503_5" : '16503-5' from LOINC
code "LOINC_1988_5" : '1988-5' from LOINC
code "LOINC_30522_7" : '30522-7' from LOINC
code "LOINC_35648_5" : '35648-5' from LOINC
code "LOINC_45062_7" : '45062-7' from LOINC
code "LOINC_48421_2" : '48421-2' from LOINC
code "LOINC_59182_6" : '59182-6' from LOINC
code "LOINC_71426_1" : '71426-1' from LOINC
code "LOINC_76485_2" : '76485-2' from LOINC
code "LOINC_76486_0" : '76486-0' from LOINC
code "CPT_85025_0" : '85025.0' from CPT
code "LOINC_751_8" : '751-8' from LOINC
code "CPT_85004" : '85004' from CPT
code "CPT_85048" : '85048' from CPT
code "LOINC_26474_7" : '26474-7' from LOINC
code "LOINC_30364_4" : '30364-4' from LOINC
code "LOINC_66140_5" : '66140-5' from LOINC
code "LOINC_731_0" : '731-0' from LOINC
code "LOINC_732_8" : '732-8' from LOINC
code "LOINC_74401_1" : '74401-1' from LOINC
code "CPT_88184_0" : '88184.0' from CPT
code "LOINC_26508_2" : '26508-2' from LOINC
code "LOINC_26509_0" : '26509-0' from LOINC
code "LOINC_26510_8" : '26510-8' from LOINC
code "LOINC_33361_7" : '33361-7' from LOINC
code "LOINC_33388_0" : '33388-0' from LOINC
code "CPT_82728_0" : '82728.0' from CPT
code "LOINC_20567_4" : '20567-4' from LOINC
code "LOINC_2276_4" : '2276-4' from LOINC
code "LOINC_48141_6" : '48141-6' from LOINC
code "LOINC_53048_5" : '53048-5' from LOINC
code "CPT_85049_0" : '85049.0' from CPT
code "HCPCS_P9019" : 'P9019' from HCPCS
code "HCPCS_P9020" : 'P9020' from HCPCS
code "LOINC_13057_5" : '13057-5' from LOINC
code "LOINC_26515_7" : '26515-7' from LOINC
code "LOINC_26516_5" : '26516-5' from LOINC
code "LOINC_47284_5" : '47284-5' from LOINC
code "CPT_84460_0" : '84460.0' from CPT
code "LOINC_16324_6" : '16324-6' from LOINC
code "LOINC_1741_8" : '1741-8' from LOINC
code "LOINC_1742_6" : '1742-6' from LOINC
code "LOINC_1743_4" : '1743-4' from LOINC
code "LOINC_1744_2" : '1744-2' from LOINC
code "LOINC_25302_1" : '25302-1' from LOINC
code "LOINC_44785_4" : '44785-4' from LOINC
code "LOINC_50168_4" : '50168-4' from LOINC
code "LOINC_54491_6" : '54491-6' from LOINC
code "LOINC_54492_4" : '54492-4' from LOINC
code "LOINC_76625_3" : '76625-3' from LOINC
code "LOINC_77144_4" : '77144-4' from LOINC
code "CPT_84450_0" : '84450.0' from CPT
code "LOINC_IMO0002" : 'IMO0002' from LOINC
code "ICD-10-CM_Z_20_828" : 'Z20.828' from ICD-10-CM
code "ICD-10-CM_U_07_1" : 'U07.1' from ICD-10-CM
code "ICD-10-CM_E_10_9" : 'E10.9' from ICD-10-CM
code "ICD-10-CM_E_11_9" : 'E11.9' from ICD-10-CM
code "ICD-10-CM_I_50_9" : 'I50.9' from ICD-10-CM
code "ICD-10-CM_I10" : 'I10' from ICD-10-CM
code "ICD-10-CM_J_44_9" : 'J44.9' from ICD-10-CM
code "ICD-10-CM_I_25_10" : 'I25.10' from ICD-10-CM
code "ICD-10-CM_I_67_9" : 'I67.9' from ICD-10-CM
code "ICD-10-CM_E_66_9" : 'E66.9' from ICD-10-CM
code "LOINC_39156_5" : '39156-5' from LOINC
code "LOINC_59574_4" : '59574-4' from LOINC
code "LOINC_59575_1" : '59575-1' from LOINC
code "LOINC_59576_9" : '59576-9' from LOINC
code "LOINC_89270_3" : '89270-3' from LOINC
code "ICD-10-CM_N_28_9" : 'N28.9' from ICD-10-CM
code "ICD-10-CM_Z_59_3" : 'Z59.3' from ICD-10-CM
code "ICD-10-CM_C_80_1" : 'C80.1' from ICD-10-CM
code "ICD-10-CM_J_18_9" : 'J18.9' from ICD-10-CM
code "ICD-10-CM_I_51_7" : 'I51.7' from ICD-10-CM

concept "Mental Status" : {  }
concept "GCS" : { "LOINC_9269_2" }
concept "Work of Breathing" : {  }
concept "Persistent Dyspnea" : {  }
concept "Hemoptysis" : { "ICD-10-CM_R_04_2" }
concept "O2 Saturation" : {  }
concept "O2 Saturation (Ambulatory)" : { "CPT_94760","CPT_94761" }
concept "Respiratory Rate" : { "LOINC_9279_1" }
concept "Heart Rate" : { "LOINC_2544737_0" }
concept "Systolic BP" : { "LOINC_8480_6" }
concept "Diastolic BP" : { "LOINC_8462_4" }
concept "Gender" : {  }
concept "Age" : {  }
concept "Race" : {  }
concept "FiO2" : { "LOINC_3150_0","LOINC_3151_8","LOINC_71835_3" }
concept "Respiratory Support- Supplemental O2" : { "HCPCS_A4615" }
concept "Respiratory Support- High Flow Nasal Canula" : { "HCPCS_A4615" }
concept "Respiratory Support- Non Invasive Positive Pressure Ventilation" : { "CPT_94660_0","ICD-10-PCS_5_A_09357","ICD-10-PCS_5_A_09358","ICD-10-PCS_5_A_09457","ICD-10-PCS_5_A_09458","ICD-10-PCS_5_A_09557","ICD-10-PCS_5_A_09558" }
concept "Respiratory Support- Mechanical Ventilation" : {  }
concept "Dobutamine " : { "HCPCS_J1250","RxNorm_3616_0" }
concept "Epinephrine" : { "HCPCS_J0171","RxNorm_3992_0" }
concept "Norepinephrine" : { "RxNorm_7512_0" }
concept "Vasopressin" : { "RxNorm_11149_0" }
concept "Dopamine" : { "HCPCS_J1265","RxNorm_3628_0" }
concept "SARS-CoV-2 test" : { "HCPCS_U0002" }
concept "Troponin I" : { "CPT_84484_0","LOINC_10839_9","LOINC_42757_5","LOINC_49563_0","LOINC_76399_5" }
concept "D-dimer" : { "CPT_85379_0","LOINC_29280_5","LOINC_29280_5","LOINC_38898_3","LOINC_48065_7","LOINC_48066_5","LOINC_7799_0","LOINC_91556_1" }
concept "Lactate" : { "CPT_83605_0","LOINC_14118_4","LOINC_19239_3","LOINC_2518_9","LOINC_2519_7","LOINC_2524_7","LOINC_30241_4","LOINC_30242_2","LOINC_32132_3","LOINC_32133_1","LOINC_32693_4","LOINC_51829_0","LOINC_55932_8","LOINC_59032_3","LOINC_75366_5","LOINC_19240_1" }
concept "Lactate Dehydrogenase" : { "CPT_83615_0","LOINC_14804_9","LOINC_14805_6" }
concept "C-Reactive Peptide" : { "CPT_86140_0","LOINC_11039_5","LOINC_14634_0","LOINC_16503_5","LOINC_1988_5","LOINC_30522_7","LOINC_35648_5","LOINC_45062_7","LOINC_48421_2","LOINC_59182_6","LOINC_71426_1","LOINC_76485_2","LOINC_76486_0","LOINC_45062_7","LOINC_35648_5","LOINC_48421_2" }
concept "Neutrophils (#)" : { "CPT_85025_0","LOINC_751_8" }
concept "Lymphocytes (#)" : { "CPT_85004","CPT_85048","LOINC_26474_7","LOINC_30364_4","LOINC_66140_5","LOINC_731_0","LOINC_732_8","LOINC_74401_1" }
concept "Lymphocyte (%)" : { "CPT_88184_0","LOINC_26508_2","LOINC_26509_0","LOINC_26510_8","LOINC_33361_7","LOINC_33388_0" }
concept "Ferritin" : { "CPT_82728_0","LOINC_20567_4","LOINC_2276_4","LOINC_48141_6","LOINC_53048_5" }
concept "Platelets" : { "CPT_85049_0","HCPCS_P9019","HCPCS_P9020","LOINC_13057_5","LOINC_26515_7","LOINC_26516_5","LOINC_47284_5" }
concept "ALT" : { "CPT_84460_0","LOINC_16324_6","LOINC_1741_8","LOINC_1742_6","LOINC_1743_4","LOINC_1744_2","LOINC_25302_1","LOINC_44785_4","LOINC_50168_4","LOINC_54491_6","LOINC_54492_4","LOINC_76625_3","LOINC_77144_4" }
concept "AST" : { "CPT_84450_0","LOINC_IMO0002" }
concept "COVID-19 diagnosis (suspected)" : { "ICD-10-CM_Z_20_828" }
concept "COVID-19 diagnosis (confirmed)" : { "ICD-10-CM_U_07_1" }
concept "Diabetes Mellitus Type 1" : { "ICD-10-CM_E_10_9" }
concept "Diabetes Mellitus Type 2" : { "ICD-10-CM_E_11_9" }
concept "Heart Failure" : { "ICD-10-CM_I_50_9" }
concept "Hypertension" : { "ICD-10-CM_I10" }
concept "Chronic Obstructive Pulmonary Disease (COPD)" : { "ICD-10-CM_J_44_9" }
concept "Cardiovascular Disease" : { "ICD-10-CM_I_25_10" }
concept "Cerebrovascular disease" : { "ICD-10-CM_I_67_9" }
concept "Obesity" : { "ICD-10-CM_E_66_9" }
concept "Obese" : {  }
concept "BMI" : { "LOINC_39156_5","LOINC_59574_4","LOINC_59575_1","LOINC_59576_9","LOINC_89270_3" }
concept "Renal Disease" : { "ICD-10-CM_N_28_9" }
concept "Long Term Care Facility Resident" : { "ICD-10-CM_Z_59_3" }
concept "Malignancy" : { "ICD-10-CM_C_80_1" }
concept "CXR Score" : {  }
concept "Bilateral Pneumonia" : { "ICD-10-CM_J_18_9" }
concept "RV dilatation" : { "ICD-10-CM_I_51_7" }
concept "RV enlargement" : { "ICD-10-CM_I_51_7" }

//https://c19hcc.org/ontologies/covid19/ed/0d631b60-7489-3b5e-b816-dd4dd5d3b243
define "Current Mental Status - Observation" : 
	[ Observation:"Mental Status" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/0f783f73-2d10-3dbf-b0f2-55f311e83e67
define "Current GCS - Observation" : 
	[ Observation:"GCS" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/7d8d47da-454d-3f38-bee9-6e87d7e2657a
define "Current Work of Breathing - Observation" : 
	[ Observation:"Work of Breathing" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/78380b3f-a38e-39fe-ae3e-5ecfd7e8b086
define "Current Persistent Dyspnea - Observation" : 
	[ Observation:"Persistent Dyspnea" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/a3a8db59-2649-3876-9bf8-61f81878bef9
define "Has Hemoptysis - Observation" : 
	[ Observation:"Hemoptysis" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/5ae3dc37-3287-398d-bf85-d8fdbfa36ba1
define "Current O2 Saturation - Observation" : 
	[ Observation:"O2 Saturation" ] X
		where X.effectiveDateTime between Now() and Now() - 3 null
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/e9c846d0-1f96-3181-8150-fb0ed1c149b1
define "Most RecentO2 Saturation (Ambulatory) - Observation" : 
	[ Observation:"O2 Saturation (Ambulatory)" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/79919c0e-1a3d-38ea-a212-e5db8ab30fca
define "Current Respiratory Rate - Observation" : 
	[ Observation:"Respiratory Rate" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/84a8ab48-fc39-307a-9f4f-99a89db68d73
define "Current Heart Rate - Observation" : 
	[ Observation:"Heart Rate" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/98a2ce86-4178-3b16-b22e-d0471aca7a49
define "Current Systolic BP - Observation" : 
	[ Observation:"Systolic BP" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/84aa13c4-6e2b-3997-9a98-8b16dc2ffdf3
define "Current Diastolic BP - Observation" : 
	[ Observation:"Diastolic BP" ] X
			sort by X.effectiveDateTime desc




//https://c19hcc.org/ontologies/covid19/ed/3bffa372-b64e-31de-8412-2300ceaf5910
define "Current FiO2 - Observation" : 
	[ Observation:"FiO2" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/f6e6247c-50c3-39f1-beba-de8703a55294
define "Prior Respiratory Support- Supplemental O2 - Procedure" : 
	[ Procedure:"Respiratory Support- Supplemental O2" ] X
			sort by X.performedDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/f6e6247c-50c3-39f1-beba-de8703a55294
define "Prior Respiratory Support- Supplemental O2 - DeviceRequest" : 
	[ DeviceRequest:"Respiratory Support- Supplemental O2" ] X
			sort by X.occurrenceDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/f6e6247c-50c3-39f1-beba-de8703a55294
define "Prior Respiratory Support- Supplemental O2 - ServiceRequest" : 
	[ ServiceRequest:"Respiratory Support- Supplemental O2" ] X
			sort by X.occurrenceDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/7ac71826-bd37-3fc3-a8bf-2ecbe377e9fd
define "Prior Respiratory Support- High Flow Nasal Canula - Procedure" : 
	[ Procedure:"Respiratory Support- High Flow Nasal Canula" ] X
			sort by X.performedDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/7ac71826-bd37-3fc3-a8bf-2ecbe377e9fd
define "Prior Respiratory Support- High Flow Nasal Canula - DeviceRequest" : 
	[ DeviceRequest:"Respiratory Support- High Flow Nasal Canula" ] X
			sort by X.occurrenceDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/7ac71826-bd37-3fc3-a8bf-2ecbe377e9fd
define "Prior Respiratory Support- High Flow Nasal Canula - ServiceRequest" : 
	[ ServiceRequest:"Respiratory Support- High Flow Nasal Canula" ] X
			sort by X.occurrenceDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/2b07c564-a2e2-393b-9fa4-99bce1f865c2
define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - Procedure" : 
	[ Procedure:"Respiratory Support- Non Invasive Positive Pressure Ventilation" ] X
			sort by X.performedDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/2b07c564-a2e2-393b-9fa4-99bce1f865c2
define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - DeviceRequest" : 
	[ DeviceRequest:"Respiratory Support- Non Invasive Positive Pressure Ventilation" ] X
			sort by X.occurrenceDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/2b07c564-a2e2-393b-9fa4-99bce1f865c2
define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - ServiceRequest" : 
	[ ServiceRequest:"Respiratory Support- Non Invasive Positive Pressure Ventilation" ] X
			sort by X.occurrenceDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/270c354c-22ae-3077-8e0e-7f0ab0a2b483
define "Prior Respiratory Support- Mechanical Ventilation - Procedure" : 
	[ Procedure:"Respiratory Support- Mechanical Ventilation" ] X
			sort by X.performedDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/270c354c-22ae-3077-8e0e-7f0ab0a2b483
define "Prior Respiratory Support- Mechanical Ventilation - DeviceRequest" : 
	[ DeviceRequest:"Respiratory Support- Mechanical Ventilation" ] X
			sort by X.occurrenceDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/270c354c-22ae-3077-8e0e-7f0ab0a2b483
define "Prior Respiratory Support- Mechanical Ventilation - ServiceRequest" : 
	[ ServiceRequest:"Respiratory Support- Mechanical Ventilation" ] X
			sort by X.occurrenceDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/e491dfcc-cf77-35ef-b4c9-fde4027f33df
define "On Dobutamine  - MedicationStatement" : 
	[ MedicationStatement:"Dobutamine " ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/e491dfcc-cf77-35ef-b4c9-fde4027f33df
define "On Dobutamine  - MedicationRequest" : 
	[ MedicationRequest:"Dobutamine " ] X
			sort by X.occurrenceDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/39e38c19-caa5-39eb-a485-2fea6166e9fd
define "On Epinephrine - MedicationStatement" : 
	[ MedicationStatement:"Epinephrine" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/39e38c19-caa5-39eb-a485-2fea6166e9fd
define "On Epinephrine - MedicationRequest" : 
	[ MedicationRequest:"Epinephrine" ] X
			sort by X.occurrenceDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/f7ac0c35-613d-3796-b45c-13fa031adcdb
define "On Norepinephrine - MedicationStatement" : 
	[ MedicationStatement:"Norepinephrine" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/f7ac0c35-613d-3796-b45c-13fa031adcdb
define "On Norepinephrine - MedicationRequest" : 
	[ MedicationRequest:"Norepinephrine" ] X
			sort by X.occurrenceDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/7a1c6d5b-f54f-39cc-bb36-e6843a58d1e4
define "On Vasopressin - MedicationStatement" : 
	[ MedicationStatement:"Vasopressin" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/7a1c6d5b-f54f-39cc-bb36-e6843a58d1e4
define "On Vasopressin - MedicationRequest" : 
	[ MedicationRequest:"Vasopressin" ] X
			sort by X.occurrenceDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/d7e7bc21-3b84-3a95-9946-ff0d9cb165cb
define "On Dopamine - MedicationStatement" : 
	[ MedicationStatement:"Dopamine" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/d7e7bc21-3b84-3a95-9946-ff0d9cb165cb
define "On Dopamine - MedicationRequest" : 
	[ MedicationRequest:"Dopamine" ] X
			sort by X.occurrenceDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/18ba15e1-65ae-3f63-b08c-42dd43e3676e
define "Most RecentSARS-CoV-2 test - Observation" : 
	[ Observation:"SARS-CoV-2 test" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/4becaa45-8d21-383b-89b7-8e17f06e6712
define "Most RecentTroponin I - Observation" : 
	[ Observation:"Troponin I" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/5959cf6f-9a5d-3640-947a-e81012372314
define "Most RecentD-dimer - Observation" : 
	[ Observation:"D-dimer" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/c434158d-0aa9-3676-b699-ea3d00be1e1b
define "Most RecentLactate - Observation" : 
	[ Observation:"Lactate" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/95bfa631-4574-3a2b-8c16-e6b08ab7c989
define "Most RecentLactate Dehydrogenase - Observation" : 
	[ Observation:"Lactate Dehydrogenase" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/1a5f251e-6304-3d4c-b207-6fe2a5961eaa
define "Most RecentC-Reactive Peptide - Observation" : 
	[ Observation:"C-Reactive Peptide" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/9547fdae-e53d-36d2-8c8d-546b2822d40f
define "Most RecentNeutrophils (#) - Observation" : 
	[ Observation:"Neutrophils (#)" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/fc070a93-5c12-34c2-8b3f-3cc7115e1059
define "Most RecentLymphocytes (#) - Observation" : 
	[ Observation:"Lymphocytes (#)" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/136be6a5-dd5c-3e80-bba9-aa1dd3c9b056
define "Most RecentLymphocyte (%) - Observation" : 
	[ Observation:"Lymphocyte (%)" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/cfb06246-ab5b-3871-8385-120d5664f14f
define "Most RecentFerritin - Observation" : 
	[ Observation:"Ferritin" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/256c1862-05d1-3c22-bfd8-b34c40206eb5
define "Most RecentPlatelets - Observation" : 
	[ Observation:"Platelets" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/dfbfc9cc-7a23-3560-b04c-b1a5faf1ec3e
define "Most RecentALT - Observation" : 
	[ Observation:"ALT" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/c77bb0f6-9e2a-30e0-86a8-dc7f0da9320f
define "Most RecentAST - Observation" : 
	[ Observation:"AST" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/efd4ddff-ab84-30a9-9b02-796d87f769a3
define "Has COVID-19 diagnosis (suspected) - Condition" : 
	[ Condition:"COVID-19 diagnosis (suspected)" ] X
			sort by X.onsetDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/cfe98e01-a3dc-33f9-b6a5-94d1f28f99bc
define "Has COVID-19 diagnosis (confirmed) - Condition" : 
	[ Condition:"COVID-19 diagnosis (confirmed)" ] X
			sort by X.onsetDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/e4f842e1-d5c7-3b8c-9819-31c6aaeaddc5
define "Has Diabetes Mellitus Type 1 - Condition" : 
	[ Condition:"Diabetes Mellitus Type 1" ] X
			sort by X.onsetDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/8d45f1b7-8513-3589-bd37-fe16739d9d89
define "Has Diabetes Mellitus Type 2 - Condition" : 
	[ Condition:"Diabetes Mellitus Type 2" ] X
			sort by X.onsetDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/6ff5edf8-399a-3cfa-a52f-ab8f9069c90c
define "Has Heart Failure - Condition" : 
	[ Condition:"Heart Failure" ] X
			sort by X.onsetDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/01da3ef2-5313-3ba8-bd4e-2123c9ae77eb
define "Has Hypertension - Condition" : 
	[ Condition:"Hypertension" ] X
			sort by X.onsetDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/3dd9edb0-933b-39cf-b279-4bf515e224dc
define "Has Chronic Obstructive Pulmonary Disease (COPD) - Condition" : 
	[ Condition:"Chronic Obstructive Pulmonary Disease (COPD)" ] X
			sort by X.onsetDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/6ef655d9-01f8-3763-a7d4-ad3e54504357
define "Has Cardiovascular Disease - Condition" : 
	[ Condition:"Cardiovascular Disease" ] X
			sort by X.onsetDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/9fdb23fd-68c3-3b78-a52f-019db75d6f6e
define "Has Cerebrovascular disease - Condition" : 
	[ Condition:"Cerebrovascular disease" ] X
			sort by X.onsetDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/9f825141-6f7e-3717-aa63-f96f1f10d7ac
define "Has Obesity - Condition" : 
	[ Condition:"Obesity" ] X
			sort by X.onsetDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/a1fcb0e0-c8f5-3d8c-a7ff-8a244b625669
define "Current Obese - Observation" : 
	[ Observation:"Obese" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/2c31b3cd-5258-36b2-b5e9-b49434ed3e3d
define "Current BMI - Observation" : 
	[ Observation:"BMI" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/4c5ef2ec-4498-3af9-91bb-f7556e025b41
define "Has Renal Disease - Condition" : 
	[ Condition:"Renal Disease" ] X
			sort by X.onsetDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/955c689f-130b-33ab-b64a-3c43dc6d6f81
define "Current Long Term Care Facility Resident - Observation" : 
	[ Observation:"Long Term Care Facility Resident" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/986a4f4e-4179-3e81-9dc8-b2a297aa8c87
define "Has Malignancy - Condition" : 
	[ Condition:"Malignancy" ] X
			sort by X.onsetDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/f9a1cd68-c82a-3361-bdc6-58e7fe11c0c3
define "Current CXR Score - Observation" : 
	[ Observation:"CXR Score" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/f9a1cd68-c82a-3361-bdc6-58e7fe11c0c3
define "Current CXR Score - DiagnosticReport" : 
	[ DiagnosticReport:"CXR Score" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/a5a5c081-4462-3dc8-99f3-e1471d1b4d4f
define "Has Bilateral Pneumonia - Observation" : 
	[ Observation:"Bilateral Pneumonia" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/a5a5c081-4462-3dc8-99f3-e1471d1b4d4f
define "Has Bilateral Pneumonia - DiagnosticReport" : 
	[ DiagnosticReport:"Bilateral Pneumonia" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/1e63bbb8-709a-300a-94e7-d47fcd830d85
define "Has RV dilatation - Observation" : 
	[ Observation:"RV dilatation" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/1e63bbb8-709a-300a-94e7-d47fcd830d85
define "Has RV dilatation - DiagnosticReport" : 
	[ DiagnosticReport:"RV dilatation" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/af583d30-4a8e-330d-a864-926fd59a0d0f
define "Has RV enlargement - Observation" : 
	[ Observation:"RV enlargement" ] X
			sort by X.effectiveDateTime desc

//https://c19hcc.org/ontologies/covid19/ed/af583d30-4a8e-330d-a864-926fd59a0d0f
define "Has RV enlargement - DiagnosticReport" : 
	[ DiagnosticReport:"RV enlargement" ] X
			sort by X.effectiveDateTime desc

