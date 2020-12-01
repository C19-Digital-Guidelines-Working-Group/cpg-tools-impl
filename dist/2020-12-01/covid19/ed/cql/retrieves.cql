library COVID19_ED version '0.0.1'

using FHIR

codesystem "C19-ED-CPG": 'https://opencpg.org/ontology/covid19/ed/'
codesystem "HCPCS": 'urn:oid:2.16.840.1.113883.6.14'
codesystem "CPT": 'http://www.ama-assn.org/go/cpt'
codesystem "ICD-10-CM": 'http://hl7.org/fhir/sid/icd-10-cm'
codesystem "LOINC": 'http://loinc.org'
codesystem "SNOMED": 'http://snomed.info/sct'
codesystem "ICD-10-PCS": 'http://hl7.org/fhir/sid/icd-10-pcs'
codesystem "RxNorm": 'http://www.nlm.nih.gov/research/umls/rxnorm'

code "C19-ED-CPG_0d631b60-7489-3b5e-b816-dd4dd5d3b243" : '0d631b60-7489-3b5e-b816-dd4dd5d3b243' from C19-ED-CPG
code "C19-ED-CPG_0f783f73-2d10-3dbf-b0f2-55f311e83e67" : '0f783f73-2d10-3dbf-b0f2-55f311e83e67' from C19-ED-CPG
code "C19-ED-CPG_7d8d47da-454d-3f38-bee9-6e87d7e2657a" : '7d8d47da-454d-3f38-bee9-6e87d7e2657a' from C19-ED-CPG
code "C19-ED-CPG_78380b3f-a38e-39fe-ae3e-5ecfd7e8b086" : '78380b3f-a38e-39fe-ae3e-5ecfd7e8b086' from C19-ED-CPG
code "C19-ED-CPG_a3a8db59-2649-3876-9bf8-61f81878bef9" : 'a3a8db59-2649-3876-9bf8-61f81878bef9' from C19-ED-CPG
code "C19-ED-CPG_5ae3dc37-3287-398d-bf85-d8fdbfa36ba1" : '5ae3dc37-3287-398d-bf85-d8fdbfa36ba1' from C19-ED-CPG
code "C19-ED-CPG_e9c846d0-1f96-3181-8150-fb0ed1c149b1" : 'e9c846d0-1f96-3181-8150-fb0ed1c149b1' from C19-ED-CPG
code "C19-ED-CPG_79919c0e-1a3d-38ea-a212-e5db8ab30fca" : '79919c0e-1a3d-38ea-a212-e5db8ab30fca' from C19-ED-CPG
code "C19-ED-CPG_84a8ab48-fc39-307a-9f4f-99a89db68d73" : '84a8ab48-fc39-307a-9f4f-99a89db68d73' from C19-ED-CPG
code "C19-ED-CPG_98a2ce86-4178-3b16-b22e-d0471aca7a49" : '98a2ce86-4178-3b16-b22e-d0471aca7a49' from C19-ED-CPG
code "C19-ED-CPG_84aa13c4-6e2b-3997-9a98-8b16dc2ffdf3" : '84aa13c4-6e2b-3997-9a98-8b16dc2ffdf3' from C19-ED-CPG
code "C19-ED-CPG_019ec313-2cdf-3ee0-b2e2-a75cf5d3e459" : '019ec313-2cdf-3ee0-b2e2-a75cf5d3e459' from C19-ED-CPG
code "C19-ED-CPG_9d8d2d5a-b12b-3151-82a5-05f54db7f538" : '9d8d2d5a-b12b-3151-82a5-05f54db7f538' from C19-ED-CPG
code "C19-ED-CPG_4e221fc0-84bb-39a8-a315-a154d6dd7879" : '4e221fc0-84bb-39a8-a315-a154d6dd7879' from C19-ED-CPG
code "C19-ED-CPG_3bffa372-b64e-31de-8412-2300ceaf5910" : '3bffa372-b64e-31de-8412-2300ceaf5910' from C19-ED-CPG
code "C19-ED-CPG_f6e6247c-50c3-39f1-beba-de8703a55294" : 'f6e6247c-50c3-39f1-beba-de8703a55294' from C19-ED-CPG
code "C19-ED-CPG_7ac71826-bd37-3fc3-a8bf-2ecbe377e9fd" : '7ac71826-bd37-3fc3-a8bf-2ecbe377e9fd' from C19-ED-CPG
code "C19-ED-CPG_2b07c564-a2e2-393b-9fa4-99bce1f865c2" : '2b07c564-a2e2-393b-9fa4-99bce1f865c2' from C19-ED-CPG
code "C19-ED-CPG_270c354c-22ae-3077-8e0e-7f0ab0a2b483" : '270c354c-22ae-3077-8e0e-7f0ab0a2b483' from C19-ED-CPG
code "C19-ED-CPG_e491dfcc-cf77-35ef-b4c9-fde4027f33df" : 'e491dfcc-cf77-35ef-b4c9-fde4027f33df' from C19-ED-CPG
code "C19-ED-CPG_39e38c19-caa5-39eb-a485-2fea6166e9fd" : '39e38c19-caa5-39eb-a485-2fea6166e9fd' from C19-ED-CPG
code "C19-ED-CPG_f7ac0c35-613d-3796-b45c-13fa031adcdb" : 'f7ac0c35-613d-3796-b45c-13fa031adcdb' from C19-ED-CPG
code "C19-ED-CPG_7a1c6d5b-f54f-39cc-bb36-e6843a58d1e4" : '7a1c6d5b-f54f-39cc-bb36-e6843a58d1e4' from C19-ED-CPG
code "C19-ED-CPG_d7e7bc21-3b84-3a95-9946-ff0d9cb165cb" : 'd7e7bc21-3b84-3a95-9946-ff0d9cb165cb' from C19-ED-CPG
code "C19-ED-CPG_18ba15e1-65ae-3f63-b08c-42dd43e3676e" : '18ba15e1-65ae-3f63-b08c-42dd43e3676e' from C19-ED-CPG
code "C19-ED-CPG_4becaa45-8d21-383b-89b7-8e17f06e6712" : '4becaa45-8d21-383b-89b7-8e17f06e6712' from C19-ED-CPG
code "C19-ED-CPG_5959cf6f-9a5d-3640-947a-e81012372314" : '5959cf6f-9a5d-3640-947a-e81012372314' from C19-ED-CPG
code "C19-ED-CPG_c434158d-0aa9-3676-b699-ea3d00be1e1b" : 'c434158d-0aa9-3676-b699-ea3d00be1e1b' from C19-ED-CPG
code "C19-ED-CPG_95bfa631-4574-3a2b-8c16-e6b08ab7c989" : '95bfa631-4574-3a2b-8c16-e6b08ab7c989' from C19-ED-CPG
code "C19-ED-CPG_1a5f251e-6304-3d4c-b207-6fe2a5961eaa" : '1a5f251e-6304-3d4c-b207-6fe2a5961eaa' from C19-ED-CPG
code "C19-ED-CPG_9547fdae-e53d-36d2-8c8d-546b2822d40f" : '9547fdae-e53d-36d2-8c8d-546b2822d40f' from C19-ED-CPG
code "C19-ED-CPG_fc070a93-5c12-34c2-8b3f-3cc7115e1059" : 'fc070a93-5c12-34c2-8b3f-3cc7115e1059' from C19-ED-CPG
code "C19-ED-CPG_136be6a5-dd5c-3e80-bba9-aa1dd3c9b056" : '136be6a5-dd5c-3e80-bba9-aa1dd3c9b056' from C19-ED-CPG
code "C19-ED-CPG_cfb06246-ab5b-3871-8385-120d5664f14f" : 'cfb06246-ab5b-3871-8385-120d5664f14f' from C19-ED-CPG
code "C19-ED-CPG_256c1862-05d1-3c22-bfd8-b34c40206eb5" : '256c1862-05d1-3c22-bfd8-b34c40206eb5' from C19-ED-CPG
code "C19-ED-CPG_dfbfc9cc-7a23-3560-b04c-b1a5faf1ec3e" : 'dfbfc9cc-7a23-3560-b04c-b1a5faf1ec3e' from C19-ED-CPG
code "C19-ED-CPG_c77bb0f6-9e2a-30e0-86a8-dc7f0da9320f" : 'c77bb0f6-9e2a-30e0-86a8-dc7f0da9320f' from C19-ED-CPG
code "C19-ED-CPG_efd4ddff-ab84-30a9-9b02-796d87f769a3" : 'efd4ddff-ab84-30a9-9b02-796d87f769a3' from C19-ED-CPG
code "C19-ED-CPG_cfe98e01-a3dc-33f9-b6a5-94d1f28f99bc" : 'cfe98e01-a3dc-33f9-b6a5-94d1f28f99bc' from C19-ED-CPG
code "C19-ED-CPG_e4f842e1-d5c7-3b8c-9819-31c6aaeaddc5" : 'e4f842e1-d5c7-3b8c-9819-31c6aaeaddc5' from C19-ED-CPG
code "C19-ED-CPG_8d45f1b7-8513-3589-bd37-fe16739d9d89" : '8d45f1b7-8513-3589-bd37-fe16739d9d89' from C19-ED-CPG
code "C19-ED-CPG_6ff5edf8-399a-3cfa-a52f-ab8f9069c90c" : '6ff5edf8-399a-3cfa-a52f-ab8f9069c90c' from C19-ED-CPG
code "C19-ED-CPG_01da3ef2-5313-3ba8-bd4e-2123c9ae77eb" : '01da3ef2-5313-3ba8-bd4e-2123c9ae77eb' from C19-ED-CPG
code "C19-ED-CPG_3dd9edb0-933b-39cf-b279-4bf515e224dc" : '3dd9edb0-933b-39cf-b279-4bf515e224dc' from C19-ED-CPG
code "C19-ED-CPG_6ef655d9-01f8-3763-a7d4-ad3e54504357" : '6ef655d9-01f8-3763-a7d4-ad3e54504357' from C19-ED-CPG
code "C19-ED-CPG_9fdb23fd-68c3-3b78-a52f-019db75d6f6e" : '9fdb23fd-68c3-3b78-a52f-019db75d6f6e' from C19-ED-CPG
code "C19-ED-CPG_9f825141-6f7e-3717-aa63-f96f1f10d7ac" : '9f825141-6f7e-3717-aa63-f96f1f10d7ac' from C19-ED-CPG
code "C19-ED-CPG_a1fcb0e0-c8f5-3d8c-a7ff-8a244b625669" : 'a1fcb0e0-c8f5-3d8c-a7ff-8a244b625669' from C19-ED-CPG
code "C19-ED-CPG_2c31b3cd-5258-36b2-b5e9-b49434ed3e3d" : '2c31b3cd-5258-36b2-b5e9-b49434ed3e3d' from C19-ED-CPG
code "C19-ED-CPG_4c5ef2ec-4498-3af9-91bb-f7556e025b41" : '4c5ef2ec-4498-3af9-91bb-f7556e025b41' from C19-ED-CPG
code "C19-ED-CPG_955c689f-130b-33ab-b64a-3c43dc6d6f81" : '955c689f-130b-33ab-b64a-3c43dc6d6f81' from C19-ED-CPG
code "C19-ED-CPG_986a4f4e-4179-3e81-9dc8-b2a297aa8c87" : '986a4f4e-4179-3e81-9dc8-b2a297aa8c87' from C19-ED-CPG
code "C19-ED-CPG_f9a1cd68-c82a-3361-bdc6-58e7fe11c0c3" : 'f9a1cd68-c82a-3361-bdc6-58e7fe11c0c3' from C19-ED-CPG
code "C19-ED-CPG_a5a5c081-4462-3dc8-99f3-e1471d1b4d4f" : 'a5a5c081-4462-3dc8-99f3-e1471d1b4d4f' from C19-ED-CPG
code "C19-ED-CPG_1e63bbb8-709a-300a-94e7-d47fcd830d85" : '1e63bbb8-709a-300a-94e7-d47fcd830d85' from C19-ED-CPG
code "C19-ED-CPG_af583d30-4a8e-330d-a864-926fd59a0d0f" : 'af583d30-4a8e-330d-a864-926fd59a0d0f' from C19-ED-CPG

concept "Mental Status" : { C19-ED-CPG_0d631b60-7489-3b5e-b816-dd4dd5d3b243 }
concept "GCS" : { C19-ED-CPG_0f783f73-2d10-3dbf-b0f2-55f311e83e67 }
concept "Work of Breathing" : { C19-ED-CPG_7d8d47da-454d-3f38-bee9-6e87d7e2657a }
concept "Persistent Dyspnea" : { C19-ED-CPG_78380b3f-a38e-39fe-ae3e-5ecfd7e8b086 }
concept "Hemoptysis" : { C19-ED-CPG_a3a8db59-2649-3876-9bf8-61f81878bef9 }
concept "O2 Saturation" : { C19-ED-CPG_5ae3dc37-3287-398d-bf85-d8fdbfa36ba1 }
concept "O2 Saturation (Ambulatory)" : { C19-ED-CPG_e9c846d0-1f96-3181-8150-fb0ed1c149b1 }
concept "Respiratory Rate" : { C19-ED-CPG_79919c0e-1a3d-38ea-a212-e5db8ab30fca }
concept "Heart Rate" : { C19-ED-CPG_84a8ab48-fc39-307a-9f4f-99a89db68d73 }
concept "Systolic BP" : { C19-ED-CPG_98a2ce86-4178-3b16-b22e-d0471aca7a49 }
concept "Diastolic BP" : { C19-ED-CPG_84aa13c4-6e2b-3997-9a98-8b16dc2ffdf3 }
concept "Gender" : { C19-ED-CPG_019ec313-2cdf-3ee0-b2e2-a75cf5d3e459 }
concept "Age" : { C19-ED-CPG_9d8d2d5a-b12b-3151-82a5-05f54db7f538 }
concept "Race" : { C19-ED-CPG_4e221fc0-84bb-39a8-a315-a154d6dd7879 }
concept "FiO2" : { C19-ED-CPG_3bffa372-b64e-31de-8412-2300ceaf5910 }
concept "Respiratory Support- Supplemental O2" : { C19-ED-CPG_f6e6247c-50c3-39f1-beba-de8703a55294 }
concept "Respiratory Support- High Flow Nasal Canula" : { C19-ED-CPG_7ac71826-bd37-3fc3-a8bf-2ecbe377e9fd }
concept "Respiratory Support- Non Invasive Positive Pressure Ventilation" : { C19-ED-CPG_2b07c564-a2e2-393b-9fa4-99bce1f865c2 }
concept "Respiratory Support- Mechanical Ventilation" : { C19-ED-CPG_270c354c-22ae-3077-8e0e-7f0ab0a2b483 }
concept "Dobutamine " : { C19-ED-CPG_e491dfcc-cf77-35ef-b4c9-fde4027f33df }
concept "Epinephrine" : { C19-ED-CPG_39e38c19-caa5-39eb-a485-2fea6166e9fd }
concept "Norepinephrine" : { C19-ED-CPG_f7ac0c35-613d-3796-b45c-13fa031adcdb }
concept "Vasopressin" : { C19-ED-CPG_7a1c6d5b-f54f-39cc-bb36-e6843a58d1e4 }
concept "Dopamine" : { C19-ED-CPG_d7e7bc21-3b84-3a95-9946-ff0d9cb165cb }
concept "SARS-CoV-2 test" : { C19-ED-CPG_18ba15e1-65ae-3f63-b08c-42dd43e3676e }
concept "Troponin I" : { C19-ED-CPG_4becaa45-8d21-383b-89b7-8e17f06e6712 }
concept "D-dimer" : { C19-ED-CPG_5959cf6f-9a5d-3640-947a-e81012372314 }
concept "Lactate" : { C19-ED-CPG_c434158d-0aa9-3676-b699-ea3d00be1e1b }
concept "Lactate Dehydrogenase" : { C19-ED-CPG_95bfa631-4574-3a2b-8c16-e6b08ab7c989 }
concept "C-Reactive Peptide" : { C19-ED-CPG_1a5f251e-6304-3d4c-b207-6fe2a5961eaa }
concept "Neutrophils (#)" : { C19-ED-CPG_9547fdae-e53d-36d2-8c8d-546b2822d40f }
concept "Lymphocytes (#)" : { C19-ED-CPG_fc070a93-5c12-34c2-8b3f-3cc7115e1059 }
concept "Lymphocyte (%)" : { C19-ED-CPG_136be6a5-dd5c-3e80-bba9-aa1dd3c9b056 }
concept "Ferritin" : { C19-ED-CPG_cfb06246-ab5b-3871-8385-120d5664f14f }
concept "Platelets" : { C19-ED-CPG_256c1862-05d1-3c22-bfd8-b34c40206eb5 }
concept "ALT" : { C19-ED-CPG_dfbfc9cc-7a23-3560-b04c-b1a5faf1ec3e }
concept "AST" : { C19-ED-CPG_c77bb0f6-9e2a-30e0-86a8-dc7f0da9320f }
concept "COVID-19 diagnosis (suspected)" : { C19-ED-CPG_efd4ddff-ab84-30a9-9b02-796d87f769a3 }
concept "COVID-19 diagnosis (confirmed)" : { C19-ED-CPG_cfe98e01-a3dc-33f9-b6a5-94d1f28f99bc }
concept "Diabetes Mellitus Type 1" : { C19-ED-CPG_e4f842e1-d5c7-3b8c-9819-31c6aaeaddc5 }
concept "Diabetes Mellitus Type 2" : { C19-ED-CPG_8d45f1b7-8513-3589-bd37-fe16739d9d89 }
concept "Heart Failure" : { C19-ED-CPG_6ff5edf8-399a-3cfa-a52f-ab8f9069c90c }
concept "Hypertension" : { C19-ED-CPG_01da3ef2-5313-3ba8-bd4e-2123c9ae77eb }
concept "Chronic Obstructive Pulmonary Disease (COPD)" : { C19-ED-CPG_3dd9edb0-933b-39cf-b279-4bf515e224dc }
concept "Cardiovascular Disease" : { C19-ED-CPG_6ef655d9-01f8-3763-a7d4-ad3e54504357 }
concept "Cerebrovascular disease" : { C19-ED-CPG_9fdb23fd-68c3-3b78-a52f-019db75d6f6e }
concept "Obesity" : { C19-ED-CPG_9f825141-6f7e-3717-aa63-f96f1f10d7ac }
concept "Obese" : { C19-ED-CPG_a1fcb0e0-c8f5-3d8c-a7ff-8a244b625669 }
concept "BMI" : { C19-ED-CPG_2c31b3cd-5258-36b2-b5e9-b49434ed3e3d }
concept "Renal Disease" : { C19-ED-CPG_4c5ef2ec-4498-3af9-91bb-f7556e025b41 }
concept "Long Term Care Facility Resident" : { C19-ED-CPG_955c689f-130b-33ab-b64a-3c43dc6d6f81 }
concept "Malignancy" : { C19-ED-CPG_986a4f4e-4179-3e81-9dc8-b2a297aa8c87 }
concept "CXR Score" : { C19-ED-CPG_f9a1cd68-c82a-3361-bdc6-58e7fe11c0c3 }
concept "Bilateral Pneumonia" : { C19-ED-CPG_a5a5c081-4462-3dc8-99f3-e1471d1b4d4f }
concept "RV dilatation" : { C19-ED-CPG_1e63bbb8-709a-300a-94e7-d47fcd830d85 }
concept "RV enlargement" : { C19-ED-CPG_af583d30-4a8e-330d-a864-926fd59a0d0f }

//https://opencpg.org/ontology/covid19/ed/0d631b60-7489-3b5e-b816-dd4dd5d3b243
define "Current Mental Status - Observation" : 
	[ Observation:"Mental Status" ] X
			sort by X.effectiveDateTime desc

define "Current Mental Status - Observation -  - Present" : 
	Exists( "Current Mental Status - Observation") 

define "Current Mental Status - Observation -  - Kind Of" : 
	"Current Mental Status - Observation".code 

define "Current Mental Status - Observation -  - Value Of" : 
	"Current Mental Status - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/0f783f73-2d10-3dbf-b0f2-55f311e83e67
define "Current GCS - Observation" : 
	[ Observation:"GCS" ] X
			sort by X.effectiveDateTime desc

define "Current GCS - Observation -  - Present" : 
	Exists( "Current GCS - Observation") 

define "Current GCS - Observation -  - Kind Of" : 
	"Current GCS - Observation".code 

define "Current GCS - Observation -  - Value Of" : 
	"Current GCS - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/7d8d47da-454d-3f38-bee9-6e87d7e2657a
define "Current Work of Breathing - Observation" : 
	[ Observation:"Work of Breathing" ] X
			sort by X.effectiveDateTime desc

define "Current Work of Breathing - Observation -  - Present" : 
	Exists( "Current Work of Breathing - Observation") 

define "Current Work of Breathing - Observation -  - Kind Of" : 
	"Current Work of Breathing - Observation".code 

define "Current Work of Breathing - Observation -  - Value Of" : 
	"Current Work of Breathing - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/78380b3f-a38e-39fe-ae3e-5ecfd7e8b086
define "Current Persistent Dyspnea - Observation" : 
	[ Observation:"Persistent Dyspnea" ] X
			sort by X.effectiveDateTime desc

define "Current Persistent Dyspnea - Observation -  - Present" : 
	Exists( "Current Persistent Dyspnea - Observation") 

define "Current Persistent Dyspnea - Observation -  - Kind Of" : 
	"Current Persistent Dyspnea - Observation".code 

define "Current Persistent Dyspnea - Observation -  - Value Of" : 
	"Current Persistent Dyspnea - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/a3a8db59-2649-3876-9bf8-61f81878bef9
define "Has Hemoptysis - Observation" : 
	[ Observation:"Hemoptysis" ] X
			sort by X.effectiveDateTime desc

define "Has Hemoptysis - Observation -  - Present" : 
	Exists( "Has Hemoptysis - Observation") 

define "Has Hemoptysis - Observation -  - Kind Of" : 
	"Has Hemoptysis - Observation".code 

define "Has Hemoptysis - Observation -  - Value Of" : 
	"Has Hemoptysis - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/5ae3dc37-3287-398d-bf85-d8fdbfa36ba1
define "Current O2 Saturation - Observation" : 
	[ Observation:"O2 Saturation" ] X
		where X.effectiveDateTime between Now() and Now() - 4 hours
			sort by X.effectiveDateTime desc

define "Current O2 Saturation - Observation -  - Present" : 
	Exists( "Current O2 Saturation - Observation") 

define "Current O2 Saturation - Observation -  - Kind Of" : 
	"Current O2 Saturation - Observation".code 

define "Current O2 Saturation - Observation -  - Value Of" : 
	"Current O2 Saturation - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/e9c846d0-1f96-3181-8150-fb0ed1c149b1
define "Most Recent O2 Saturation (Ambulatory) - Observation" : 
	[ Observation:"O2 Saturation (Ambulatory)" ] X
			sort by X.effectiveDateTime desc

define "Most Recent O2 Saturation (Ambulatory) - Observation -  - Present" : 
	Exists( "Most Recent O2 Saturation (Ambulatory) - Observation") 

define "Most Recent O2 Saturation (Ambulatory) - Observation -  - Kind Of" : 
	"Most Recent O2 Saturation (Ambulatory) - Observation".code 

define "Most Recent O2 Saturation (Ambulatory) - Observation -  - Value Of" : 
	"Most Recent O2 Saturation (Ambulatory) - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/79919c0e-1a3d-38ea-a212-e5db8ab30fca
define "Current Respiratory Rate - Observation" : 
	[ Observation:"Respiratory Rate" ] X
			sort by X.effectiveDateTime desc

define "Current Respiratory Rate - Observation -  - Present" : 
	Exists( "Current Respiratory Rate - Observation") 

define "Current Respiratory Rate - Observation -  - Kind Of" : 
	"Current Respiratory Rate - Observation".code 

define "Current Respiratory Rate - Observation -  - Value Of" : 
	"Current Respiratory Rate - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/84a8ab48-fc39-307a-9f4f-99a89db68d73
define "Current Heart Rate - Observation" : 
	[ Observation:"Heart Rate" ] X
			sort by X.effectiveDateTime desc

define "Current Heart Rate - Observation -  - Present" : 
	Exists( "Current Heart Rate - Observation") 

define "Current Heart Rate - Observation -  - Kind Of" : 
	"Current Heart Rate - Observation".code 

define "Current Heart Rate - Observation -  - Value Of" : 
	"Current Heart Rate - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/98a2ce86-4178-3b16-b22e-d0471aca7a49
define "Current Systolic BP - Observation" : 
	[ Observation:"Systolic BP" ] X
			sort by X.effectiveDateTime desc

define "Current Systolic BP - Observation -  - Present" : 
	Exists( "Current Systolic BP - Observation") 

define "Current Systolic BP - Observation -  - Kind Of" : 
	"Current Systolic BP - Observation".code 

define "Current Systolic BP - Observation -  - Value Of" : 
	"Current Systolic BP - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/84aa13c4-6e2b-3997-9a98-8b16dc2ffdf3
define "Current Diastolic BP - Observation" : 
	[ Observation:"Diastolic BP" ] X
			sort by X.effectiveDateTime desc

define "Current Diastolic BP - Observation -  - Present" : 
	Exists( "Current Diastolic BP - Observation") 

define "Current Diastolic BP - Observation -  - Kind Of" : 
	"Current Diastolic BP - Observation".code 

define "Current Diastolic BP - Observation -  - Value Of" : 
	"Current Diastolic BP - Observation".valueQuantity 








//https://opencpg.org/ontology/covid19/ed/3bffa372-b64e-31de-8412-2300ceaf5910
define "Current FiO2 - Observation" : 
	[ Observation:"FiO2" ] X
			sort by X.effectiveDateTime desc

define "Current FiO2 - Observation -  - Present" : 
	Exists( "Current FiO2 - Observation") 

define "Current FiO2 - Observation -  - Kind Of" : 
	"Current FiO2 - Observation".code 

define "Current FiO2 - Observation -  - Value Of" : 
	"Current FiO2 - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/f6e6247c-50c3-39f1-beba-de8703a55294
define "Prior Respiratory Support- Supplemental O2 - Procedure" : 
	[ Procedure:"Respiratory Support- Supplemental O2" ] X
			sort by X.performedDateTime desc

define "Prior Respiratory Support- Supplemental O2 - Procedure -  - Present" : 
	Exists( "Prior Respiratory Support- Supplemental O2 - Procedure") 

define "Prior Respiratory Support- Supplemental O2 - Procedure -  - Kind Of" : 
	"Prior Respiratory Support- Supplemental O2 - Procedure".code 


//https://opencpg.org/ontology/covid19/ed/f6e6247c-50c3-39f1-beba-de8703a55294
define "Prior Respiratory Support- Supplemental O2 - DeviceRequest" : 
	[ DeviceRequest:"Respiratory Support- Supplemental O2" ] X
			sort by X.occurrenceDateTime desc

define "Prior Respiratory Support- Supplemental O2 - DeviceRequest -  - Present" : 
	Exists( "Prior Respiratory Support- Supplemental O2 - DeviceRequest") 


//https://opencpg.org/ontology/covid19/ed/f6e6247c-50c3-39f1-beba-de8703a55294
define "Prior Respiratory Support- Supplemental O2 - ServiceRequest" : 
	[ ServiceRequest:"Respiratory Support- Supplemental O2" ] X
			sort by X.occurrenceDateTime desc

define "Prior Respiratory Support- Supplemental O2 - ServiceRequest -  - Present" : 
	Exists( "Prior Respiratory Support- Supplemental O2 - ServiceRequest") 


//https://opencpg.org/ontology/covid19/ed/7ac71826-bd37-3fc3-a8bf-2ecbe377e9fd
define "Prior Respiratory Support- High Flow Nasal Canula - Procedure" : 
	[ Procedure:"Respiratory Support- High Flow Nasal Canula" ] X
			sort by X.performedDateTime desc

define "Prior Respiratory Support- High Flow Nasal Canula - Procedure -  - Present" : 
	Exists( "Prior Respiratory Support- High Flow Nasal Canula - Procedure") 

define "Prior Respiratory Support- High Flow Nasal Canula - Procedure -  - Kind Of" : 
	"Prior Respiratory Support- High Flow Nasal Canula - Procedure".code 


//https://opencpg.org/ontology/covid19/ed/7ac71826-bd37-3fc3-a8bf-2ecbe377e9fd
define "Prior Respiratory Support- High Flow Nasal Canula - DeviceRequest" : 
	[ DeviceRequest:"Respiratory Support- High Flow Nasal Canula" ] X
			sort by X.occurrenceDateTime desc

define "Prior Respiratory Support- High Flow Nasal Canula - DeviceRequest -  - Present" : 
	Exists( "Prior Respiratory Support- High Flow Nasal Canula - DeviceRequest") 


//https://opencpg.org/ontology/covid19/ed/7ac71826-bd37-3fc3-a8bf-2ecbe377e9fd
define "Prior Respiratory Support- High Flow Nasal Canula - ServiceRequest" : 
	[ ServiceRequest:"Respiratory Support- High Flow Nasal Canula" ] X
			sort by X.occurrenceDateTime desc

define "Prior Respiratory Support- High Flow Nasal Canula - ServiceRequest -  - Present" : 
	Exists( "Prior Respiratory Support- High Flow Nasal Canula - ServiceRequest") 


//https://opencpg.org/ontology/covid19/ed/2b07c564-a2e2-393b-9fa4-99bce1f865c2
define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - Procedure" : 
	[ Procedure:"Respiratory Support- Non Invasive Positive Pressure Ventilation" ] X
			sort by X.performedDateTime desc

define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - Procedure -  - Present" : 
	Exists( "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - Procedure") 

define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - Procedure -  - Kind Of" : 
	"Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - Procedure".code 


//https://opencpg.org/ontology/covid19/ed/2b07c564-a2e2-393b-9fa4-99bce1f865c2
define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - DeviceRequest" : 
	[ DeviceRequest:"Respiratory Support- Non Invasive Positive Pressure Ventilation" ] X
			sort by X.occurrenceDateTime desc

define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - DeviceRequest -  - Present" : 
	Exists( "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - DeviceRequest") 


//https://opencpg.org/ontology/covid19/ed/2b07c564-a2e2-393b-9fa4-99bce1f865c2
define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - ServiceRequest" : 
	[ ServiceRequest:"Respiratory Support- Non Invasive Positive Pressure Ventilation" ] X
			sort by X.occurrenceDateTime desc

define "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - ServiceRequest -  - Present" : 
	Exists( "Prior Respiratory Support- Non Invasive Positive Pressure Ventilation - ServiceRequest") 


//https://opencpg.org/ontology/covid19/ed/270c354c-22ae-3077-8e0e-7f0ab0a2b483
define "Prior Respiratory Support- Mechanical Ventilation - Procedure" : 
	[ Procedure:"Respiratory Support- Mechanical Ventilation" ] X
			sort by X.performedDateTime desc

define "Prior Respiratory Support- Mechanical Ventilation - Procedure -  - Present" : 
	Exists( "Prior Respiratory Support- Mechanical Ventilation - Procedure") 

define "Prior Respiratory Support- Mechanical Ventilation - Procedure -  - Kind Of" : 
	"Prior Respiratory Support- Mechanical Ventilation - Procedure".code 


//https://opencpg.org/ontology/covid19/ed/270c354c-22ae-3077-8e0e-7f0ab0a2b483
define "Prior Respiratory Support- Mechanical Ventilation - DeviceRequest" : 
	[ DeviceRequest:"Respiratory Support- Mechanical Ventilation" ] X
			sort by X.occurrenceDateTime desc

define "Prior Respiratory Support- Mechanical Ventilation - DeviceRequest -  - Present" : 
	Exists( "Prior Respiratory Support- Mechanical Ventilation - DeviceRequest") 


//https://opencpg.org/ontology/covid19/ed/270c354c-22ae-3077-8e0e-7f0ab0a2b483
define "Prior Respiratory Support- Mechanical Ventilation - ServiceRequest" : 
	[ ServiceRequest:"Respiratory Support- Mechanical Ventilation" ] X
			sort by X.occurrenceDateTime desc

define "Prior Respiratory Support- Mechanical Ventilation - ServiceRequest -  - Present" : 
	Exists( "Prior Respiratory Support- Mechanical Ventilation - ServiceRequest") 


//https://opencpg.org/ontology/covid19/ed/e491dfcc-cf77-35ef-b4c9-fde4027f33df
define "On Dobutamine  - MedicationStatement" : 
	[ MedicationStatement:"Dobutamine " ] X
			sort by X.effectiveDateTime desc

define "On Dobutamine  - MedicationStatement -  - Present" : 
	Exists( "On Dobutamine  - MedicationStatement") 


//https://opencpg.org/ontology/covid19/ed/e491dfcc-cf77-35ef-b4c9-fde4027f33df
define "On Dobutamine  - MedicationRequest" : 
	[ MedicationRequest:"Dobutamine " ] X
			sort by X.occurrenceDateTime desc

define "On Dobutamine  - MedicationRequest -  - Present" : 
	Exists( "On Dobutamine  - MedicationRequest") 


//https://opencpg.org/ontology/covid19/ed/39e38c19-caa5-39eb-a485-2fea6166e9fd
define "On Epinephrine - MedicationStatement" : 
	[ MedicationStatement:"Epinephrine" ] X
			sort by X.effectiveDateTime desc

define "On Epinephrine - MedicationStatement -  - Present" : 
	Exists( "On Epinephrine - MedicationStatement") 


//https://opencpg.org/ontology/covid19/ed/39e38c19-caa5-39eb-a485-2fea6166e9fd
define "On Epinephrine - MedicationRequest" : 
	[ MedicationRequest:"Epinephrine" ] X
			sort by X.occurrenceDateTime desc

define "On Epinephrine - MedicationRequest -  - Present" : 
	Exists( "On Epinephrine - MedicationRequest") 


//https://opencpg.org/ontology/covid19/ed/f7ac0c35-613d-3796-b45c-13fa031adcdb
define "On Norepinephrine - MedicationStatement" : 
	[ MedicationStatement:"Norepinephrine" ] X
			sort by X.effectiveDateTime desc

define "On Norepinephrine - MedicationStatement -  - Present" : 
	Exists( "On Norepinephrine - MedicationStatement") 


//https://opencpg.org/ontology/covid19/ed/f7ac0c35-613d-3796-b45c-13fa031adcdb
define "On Norepinephrine - MedicationRequest" : 
	[ MedicationRequest:"Norepinephrine" ] X
			sort by X.occurrenceDateTime desc

define "On Norepinephrine - MedicationRequest -  - Present" : 
	Exists( "On Norepinephrine - MedicationRequest") 


//https://opencpg.org/ontology/covid19/ed/7a1c6d5b-f54f-39cc-bb36-e6843a58d1e4
define "On Vasopressin - MedicationStatement" : 
	[ MedicationStatement:"Vasopressin" ] X
			sort by X.effectiveDateTime desc

define "On Vasopressin - MedicationStatement -  - Present" : 
	Exists( "On Vasopressin - MedicationStatement") 


//https://opencpg.org/ontology/covid19/ed/7a1c6d5b-f54f-39cc-bb36-e6843a58d1e4
define "On Vasopressin - MedicationRequest" : 
	[ MedicationRequest:"Vasopressin" ] X
			sort by X.occurrenceDateTime desc

define "On Vasopressin - MedicationRequest -  - Present" : 
	Exists( "On Vasopressin - MedicationRequest") 


//https://opencpg.org/ontology/covid19/ed/d7e7bc21-3b84-3a95-9946-ff0d9cb165cb
define "On Dopamine - MedicationStatement" : 
	[ MedicationStatement:"Dopamine" ] X
			sort by X.effectiveDateTime desc

define "On Dopamine - MedicationStatement -  - Present" : 
	Exists( "On Dopamine - MedicationStatement") 


//https://opencpg.org/ontology/covid19/ed/d7e7bc21-3b84-3a95-9946-ff0d9cb165cb
define "On Dopamine - MedicationRequest" : 
	[ MedicationRequest:"Dopamine" ] X
			sort by X.occurrenceDateTime desc

define "On Dopamine - MedicationRequest -  - Present" : 
	Exists( "On Dopamine - MedicationRequest") 


//https://opencpg.org/ontology/covid19/ed/18ba15e1-65ae-3f63-b08c-42dd43e3676e
define "Most Recent SARS-CoV-2 test - Observation" : 
	[ Observation:"SARS-CoV-2 test" ] X
			sort by X.effectiveDateTime desc

define "Most Recent SARS-CoV-2 test - Observation -  - Present" : 
	Exists( "Most Recent SARS-CoV-2 test - Observation") 

define "Most Recent SARS-CoV-2 test - Observation -  - Kind Of" : 
	"Most Recent SARS-CoV-2 test - Observation".code 

define "Most Recent SARS-CoV-2 test - Observation -  - Value Of" : 
	"Most Recent SARS-CoV-2 test - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/4becaa45-8d21-383b-89b7-8e17f06e6712
define "Most Recent Troponin I - Observation" : 
	[ Observation:"Troponin I" ] X
			sort by X.effectiveDateTime desc

define "Most Recent Troponin I - Observation -  - Present" : 
	Exists( "Most Recent Troponin I - Observation") 

define "Most Recent Troponin I - Observation -  - Kind Of" : 
	"Most Recent Troponin I - Observation".code 

define "Most Recent Troponin I - Observation -  - Value Of" : 
	"Most Recent Troponin I - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/5959cf6f-9a5d-3640-947a-e81012372314
define "Most Recent D-dimer - Observation" : 
	[ Observation:"D-dimer" ] X
			sort by X.effectiveDateTime desc

define "Most Recent D-dimer - Observation -  - Present" : 
	Exists( "Most Recent D-dimer - Observation") 

define "Most Recent D-dimer - Observation -  - Kind Of" : 
	"Most Recent D-dimer - Observation".code 

define "Most Recent D-dimer - Observation -  - Value Of" : 
	"Most Recent D-dimer - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/c434158d-0aa9-3676-b699-ea3d00be1e1b
define "Most Recent Lactate - Observation" : 
	[ Observation:"Lactate" ] X
			sort by X.effectiveDateTime desc

define "Most Recent Lactate - Observation -  - Present" : 
	Exists( "Most Recent Lactate - Observation") 

define "Most Recent Lactate - Observation -  - Kind Of" : 
	"Most Recent Lactate - Observation".code 

define "Most Recent Lactate - Observation -  - Value Of" : 
	"Most Recent Lactate - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/95bfa631-4574-3a2b-8c16-e6b08ab7c989
define "Most Recent Lactate Dehydrogenase - Observation" : 
	[ Observation:"Lactate Dehydrogenase" ] X
			sort by X.effectiveDateTime desc

define "Most Recent Lactate Dehydrogenase - Observation -  - Present" : 
	Exists( "Most Recent Lactate Dehydrogenase - Observation") 

define "Most Recent Lactate Dehydrogenase - Observation -  - Kind Of" : 
	"Most Recent Lactate Dehydrogenase - Observation".code 

define "Most Recent Lactate Dehydrogenase - Observation -  - Value Of" : 
	"Most Recent Lactate Dehydrogenase - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/1a5f251e-6304-3d4c-b207-6fe2a5961eaa
define "Most Recent C-Reactive Peptide - Observation" : 
	[ Observation:"C-Reactive Peptide" ] X
			sort by X.effectiveDateTime desc

define "Most Recent C-Reactive Peptide - Observation -  - Present" : 
	Exists( "Most Recent C-Reactive Peptide - Observation") 

define "Most Recent C-Reactive Peptide - Observation -  - Kind Of" : 
	"Most Recent C-Reactive Peptide - Observation".code 

define "Most Recent C-Reactive Peptide - Observation -  - Value Of" : 
	"Most Recent C-Reactive Peptide - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/9547fdae-e53d-36d2-8c8d-546b2822d40f
define "Most Recent Neutrophils (#) - Observation" : 
	[ Observation:"Neutrophils (#)" ] X
			sort by X.effectiveDateTime desc

define "Most Recent Neutrophils (#) - Observation -  - Present" : 
	Exists( "Most Recent Neutrophils (#) - Observation") 

define "Most Recent Neutrophils (#) - Observation -  - Kind Of" : 
	"Most Recent Neutrophils (#) - Observation".code 

define "Most Recent Neutrophils (#) - Observation -  - Value Of" : 
	"Most Recent Neutrophils (#) - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/fc070a93-5c12-34c2-8b3f-3cc7115e1059
define "Most Recent Lymphocytes (#) - Observation" : 
	[ Observation:"Lymphocytes (#)" ] X
			sort by X.effectiveDateTime desc

define "Most Recent Lymphocytes (#) - Observation -  - Present" : 
	Exists( "Most Recent Lymphocytes (#) - Observation") 

define "Most Recent Lymphocytes (#) - Observation -  - Kind Of" : 
	"Most Recent Lymphocytes (#) - Observation".code 

define "Most Recent Lymphocytes (#) - Observation -  - Value Of" : 
	"Most Recent Lymphocytes (#) - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/136be6a5-dd5c-3e80-bba9-aa1dd3c9b056
define "Most Recent Lymphocyte (%) - Observation" : 
	[ Observation:"Lymphocyte (%)" ] X
			sort by X.effectiveDateTime desc

define "Most Recent Lymphocyte (%) - Observation -  - Present" : 
	Exists( "Most Recent Lymphocyte (%) - Observation") 

define "Most Recent Lymphocyte (%) - Observation -  - Kind Of" : 
	"Most Recent Lymphocyte (%) - Observation".code 

define "Most Recent Lymphocyte (%) - Observation -  - Value Of" : 
	"Most Recent Lymphocyte (%) - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/cfb06246-ab5b-3871-8385-120d5664f14f
define "Most Recent Ferritin - Observation" : 
	[ Observation:"Ferritin" ] X
			sort by X.effectiveDateTime desc

define "Most Recent Ferritin - Observation -  - Present" : 
	Exists( "Most Recent Ferritin - Observation") 

define "Most Recent Ferritin - Observation -  - Kind Of" : 
	"Most Recent Ferritin - Observation".code 

define "Most Recent Ferritin - Observation -  - Value Of" : 
	"Most Recent Ferritin - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/256c1862-05d1-3c22-bfd8-b34c40206eb5
define "Most Recent Platelets - Observation" : 
	[ Observation:"Platelets" ] X
			sort by X.effectiveDateTime desc

define "Most Recent Platelets - Observation -  - Present" : 
	Exists( "Most Recent Platelets - Observation") 

define "Most Recent Platelets - Observation -  - Kind Of" : 
	"Most Recent Platelets - Observation".code 

define "Most Recent Platelets - Observation -  - Value Of" : 
	"Most Recent Platelets - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/dfbfc9cc-7a23-3560-b04c-b1a5faf1ec3e
define "Most Recent ALT - Observation" : 
	[ Observation:"ALT" ] X
			sort by X.effectiveDateTime desc

define "Most Recent ALT - Observation -  - Present" : 
	Exists( "Most Recent ALT - Observation") 

define "Most Recent ALT - Observation -  - Kind Of" : 
	"Most Recent ALT - Observation".code 

define "Most Recent ALT - Observation -  - Value Of" : 
	"Most Recent ALT - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/c77bb0f6-9e2a-30e0-86a8-dc7f0da9320f
define "Most Recent AST - Observation" : 
	[ Observation:"AST" ] X
			sort by X.effectiveDateTime desc

define "Most Recent AST - Observation -  - Present" : 
	Exists( "Most Recent AST - Observation") 

define "Most Recent AST - Observation -  - Kind Of" : 
	"Most Recent AST - Observation".code 

define "Most Recent AST - Observation -  - Value Of" : 
	"Most Recent AST - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/efd4ddff-ab84-30a9-9b02-796d87f769a3
define "Has COVID-19 diagnosis (suspected) - Condition" : 
	[ Condition:"COVID-19 diagnosis (suspected)" ] X
			sort by X.onsetDateTime desc

define "Has COVID-19 diagnosis (suspected) - Condition -  - Present" : 
	Exists( "Has COVID-19 diagnosis (suspected) - Condition") 


//https://opencpg.org/ontology/covid19/ed/cfe98e01-a3dc-33f9-b6a5-94d1f28f99bc
define "Has COVID-19 diagnosis (confirmed) - Condition" : 
	[ Condition:"COVID-19 diagnosis (confirmed)" ] X
			sort by X.onsetDateTime desc

define "Has COVID-19 diagnosis (confirmed) - Condition -  - Present" : 
	Exists( "Has COVID-19 diagnosis (confirmed) - Condition") 


//https://opencpg.org/ontology/covid19/ed/e4f842e1-d5c7-3b8c-9819-31c6aaeaddc5
define "Has Diabetes Mellitus Type 1 - Condition" : 
	[ Condition:"Diabetes Mellitus Type 1" ] X
			sort by X.onsetDateTime desc

define "Has Diabetes Mellitus Type 1 - Condition -  - Present" : 
	Exists( "Has Diabetes Mellitus Type 1 - Condition") 


//https://opencpg.org/ontology/covid19/ed/8d45f1b7-8513-3589-bd37-fe16739d9d89
define "Has Diabetes Mellitus Type 2 - Condition" : 
	[ Condition:"Diabetes Mellitus Type 2" ] X
			sort by X.onsetDateTime desc

define "Has Diabetes Mellitus Type 2 - Condition -  - Present" : 
	Exists( "Has Diabetes Mellitus Type 2 - Condition") 


//https://opencpg.org/ontology/covid19/ed/6ff5edf8-399a-3cfa-a52f-ab8f9069c90c
define "Has Heart Failure - Condition" : 
	[ Condition:"Heart Failure" ] X
			sort by X.onsetDateTime desc

define "Has Heart Failure - Condition -  - Present" : 
	Exists( "Has Heart Failure - Condition") 


//https://opencpg.org/ontology/covid19/ed/01da3ef2-5313-3ba8-bd4e-2123c9ae77eb
define "Has Hypertension - Condition" : 
	[ Condition:"Hypertension" ] X
			sort by X.onsetDateTime desc

define "Has Hypertension - Condition -  - Present" : 
	Exists( "Has Hypertension - Condition") 


//https://opencpg.org/ontology/covid19/ed/3dd9edb0-933b-39cf-b279-4bf515e224dc
define "Has Chronic Obstructive Pulmonary Disease (COPD) - Condition" : 
	[ Condition:"Chronic Obstructive Pulmonary Disease (COPD)" ] X
			sort by X.onsetDateTime desc

define "Has Chronic Obstructive Pulmonary Disease (COPD) - Condition -  - Present" : 
	Exists( "Has Chronic Obstructive Pulmonary Disease (COPD) - Condition") 


//https://opencpg.org/ontology/covid19/ed/6ef655d9-01f8-3763-a7d4-ad3e54504357
define "Has Cardiovascular Disease - Condition" : 
	[ Condition:"Cardiovascular Disease" ] X
			sort by X.onsetDateTime desc

define "Has Cardiovascular Disease - Condition -  - Present" : 
	Exists( "Has Cardiovascular Disease - Condition") 


//https://opencpg.org/ontology/covid19/ed/9fdb23fd-68c3-3b78-a52f-019db75d6f6e
define "Has Cerebrovascular disease - Condition" : 
	[ Condition:"Cerebrovascular disease" ] X
			sort by X.onsetDateTime desc

define "Has Cerebrovascular disease - Condition -  - Present" : 
	Exists( "Has Cerebrovascular disease - Condition") 


//https://opencpg.org/ontology/covid19/ed/9f825141-6f7e-3717-aa63-f96f1f10d7ac
define "Has Obesity - Condition" : 
	[ Condition:"Obesity" ] X
			sort by X.onsetDateTime desc

define "Has Obesity - Condition -  - Present" : 
	Exists( "Has Obesity - Condition") 


//https://opencpg.org/ontology/covid19/ed/a1fcb0e0-c8f5-3d8c-a7ff-8a244b625669
define "Current Obese - Observation" : 
	[ Observation:"Obese" ] X
			sort by X.effectiveDateTime desc

define "Current Obese - Observation -  - Present" : 
	Exists( "Current Obese - Observation") 

define "Current Obese - Observation -  - Kind Of" : 
	"Current Obese - Observation".code 

define "Current Obese - Observation -  - Value Of" : 
	"Current Obese - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/2c31b3cd-5258-36b2-b5e9-b49434ed3e3d
define "Current BMI - Observation" : 
	[ Observation:"BMI" ] X
			sort by X.effectiveDateTime desc

define "Current BMI - Observation -  - Present" : 
	Exists( "Current BMI - Observation") 

define "Current BMI - Observation -  - Kind Of" : 
	"Current BMI - Observation".code 

define "Current BMI - Observation -  - Value Of" : 
	"Current BMI - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/4c5ef2ec-4498-3af9-91bb-f7556e025b41
define "Has Renal Disease - Condition" : 
	[ Condition:"Renal Disease" ] X
			sort by X.onsetDateTime desc

define "Has Renal Disease - Condition -  - Present" : 
	Exists( "Has Renal Disease - Condition") 


//https://opencpg.org/ontology/covid19/ed/955c689f-130b-33ab-b64a-3c43dc6d6f81
define "Current Long Term Care Facility Resident - Observation" : 
	[ Observation:"Long Term Care Facility Resident" ] X
			sort by X.effectiveDateTime desc

define "Current Long Term Care Facility Resident - Observation -  - Present" : 
	Exists( "Current Long Term Care Facility Resident - Observation") 

define "Current Long Term Care Facility Resident - Observation -  - Kind Of" : 
	"Current Long Term Care Facility Resident - Observation".code 

define "Current Long Term Care Facility Resident - Observation -  - Value Of" : 
	"Current Long Term Care Facility Resident - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/986a4f4e-4179-3e81-9dc8-b2a297aa8c87
define "Has Malignancy - Condition" : 
	[ Condition:"Malignancy" ] X
			sort by X.onsetDateTime desc

define "Has Malignancy - Condition -  - Present" : 
	Exists( "Has Malignancy - Condition") 


//https://opencpg.org/ontology/covid19/ed/f9a1cd68-c82a-3361-bdc6-58e7fe11c0c3
define "Current CXR Score - Observation" : 
	[ Observation:"CXR Score" ] X
			sort by X.effectiveDateTime desc

define "Current CXR Score - Observation -  - Present" : 
	Exists( "Current CXR Score - Observation") 

define "Current CXR Score - Observation -  - Kind Of" : 
	"Current CXR Score - Observation".code 

define "Current CXR Score - Observation -  - Value Of" : 
	"Current CXR Score - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/f9a1cd68-c82a-3361-bdc6-58e7fe11c0c3
define "Current CXR Score - DiagnosticReport" : 
	[ DiagnosticReport:"CXR Score" ] X
			sort by X.effectiveDateTime desc

define "Current CXR Score - DiagnosticReport -  - Present" : 
	Exists( "Current CXR Score - DiagnosticReport") 


//https://opencpg.org/ontology/covid19/ed/a5a5c081-4462-3dc8-99f3-e1471d1b4d4f
define "Has Bilateral Pneumonia - Observation" : 
	[ Observation:"Bilateral Pneumonia" ] X
			sort by X.effectiveDateTime desc

define "Has Bilateral Pneumonia - Observation -  - Present" : 
	Exists( "Has Bilateral Pneumonia - Observation") 

define "Has Bilateral Pneumonia - Observation -  - Kind Of" : 
	"Has Bilateral Pneumonia - Observation".code 

define "Has Bilateral Pneumonia - Observation -  - Value Of" : 
	"Has Bilateral Pneumonia - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/a5a5c081-4462-3dc8-99f3-e1471d1b4d4f
define "Has Bilateral Pneumonia - DiagnosticReport" : 
	[ DiagnosticReport:"Bilateral Pneumonia" ] X
			sort by X.effectiveDateTime desc

define "Has Bilateral Pneumonia - DiagnosticReport -  - Present" : 
	Exists( "Has Bilateral Pneumonia - DiagnosticReport") 


//https://opencpg.org/ontology/covid19/ed/1e63bbb8-709a-300a-94e7-d47fcd830d85
define "Has RV dilatation - Observation" : 
	[ Observation:"RV dilatation" ] X
			sort by X.effectiveDateTime desc

define "Has RV dilatation - Observation -  - Present" : 
	Exists( "Has RV dilatation - Observation") 

define "Has RV dilatation - Observation -  - Kind Of" : 
	"Has RV dilatation - Observation".code 

define "Has RV dilatation - Observation -  - Value Of" : 
	"Has RV dilatation - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/1e63bbb8-709a-300a-94e7-d47fcd830d85
define "Has RV dilatation - DiagnosticReport" : 
	[ DiagnosticReport:"RV dilatation" ] X
			sort by X.effectiveDateTime desc

define "Has RV dilatation - DiagnosticReport -  - Present" : 
	Exists( "Has RV dilatation - DiagnosticReport") 


//https://opencpg.org/ontology/covid19/ed/af583d30-4a8e-330d-a864-926fd59a0d0f
define "Has RV enlargement - Observation" : 
	[ Observation:"RV enlargement" ] X
			sort by X.effectiveDateTime desc

define "Has RV enlargement - Observation -  - Present" : 
	Exists( "Has RV enlargement - Observation") 

define "Has RV enlargement - Observation -  - Kind Of" : 
	"Has RV enlargement - Observation".code 

define "Has RV enlargement - Observation -  - Value Of" : 
	"Has RV enlargement - Observation".valueQuantity 


//https://opencpg.org/ontology/covid19/ed/af583d30-4a8e-330d-a864-926fd59a0d0f
define "Has RV enlargement - DiagnosticReport" : 
	[ DiagnosticReport:"RV enlargement" ] X
			sort by X.effectiveDateTime desc

define "Has RV enlargement - DiagnosticReport -  - Present" : 
	Exists( "Has RV enlargement - DiagnosticReport") 


