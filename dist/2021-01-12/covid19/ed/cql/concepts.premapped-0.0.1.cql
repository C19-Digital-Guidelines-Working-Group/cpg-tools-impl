library COVID19_ED_CPG_Concepts_Premapped version '0.0.1'

using FHIR version '4.0.1'

include FHIRHelpers version '4.0.1'

codesystem "C_19_ED_CPG": 'https://opencpg.org/ontology/covid19/ed/'
codesystem "HCPCS": 'urn:oid:2.16.840.1.113883.6.14'
codesystem "CPT": 'http://www.ama-assn.org/go/cpt'
codesystem "ICD_10_CM": 'http://hl7.org/fhir/sid/icd-10-cm'
codesystem "LOINC": 'http://loinc.org'
codesystem "SNOMED": 'http://snomed.info/sct'
codesystem "ICD_10_PCS": 'http://hl7.org/fhir/sid/icd-10-pcs'
codesystem "RX_NORM": 'http://www.nlm.nih.gov/research/umls/rxnorm'

code "LOINC_9269_2" : '9269-2' from LOINC
code "ICD_10_CM_R_04_2" : 'R04.2' from ICD_10_CM
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
code "ICD_10_PCS_5_A_09357" : '5A09357' from ICD_10_PCS
code "ICD_10_PCS_5_A_09358" : '5A09358' from ICD_10_PCS
code "ICD_10_PCS_5_A_09457" : '5A09457' from ICD_10_PCS
code "ICD_10_PCS_5_A_09458" : '5A09458' from ICD_10_PCS
code "ICD_10_PCS_5_A_09557" : '5A09557' from ICD_10_PCS
code "ICD_10_PCS_5_A_09558" : '5A09558' from ICD_10_PCS
code "HCPCS_J1250" : 'J1250' from HCPCS
code "RX_NORM_3616_0" : '3616.0' from RX_NORM
code "HCPCS_J0171" : 'J0171' from HCPCS
code "RX_NORM_3992_0" : '3992.0' from RX_NORM
code "RX_NORM_7512_0" : '7512.0' from RX_NORM
code "RX_NORM_11149_0" : '11149.0' from RX_NORM
code "HCPCS_J1265" : 'J1265' from HCPCS
code "RX_NORM_3628_0" : '3628.0' from RX_NORM
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
code "ICD_10_CM_Z_20_828" : 'Z20.828' from ICD_10_CM
code "ICD_10_CM_U_07_1" : 'U07.1' from ICD_10_CM
code "ICD_10_CM_E_10_9" : 'E10.9' from ICD_10_CM
code "ICD_10_CM_E_11_9" : 'E11.9' from ICD_10_CM
code "ICD_10_CM_I_50_9" : 'I50.9' from ICD_10_CM
code "ICD_10_CM_I10" : 'I10' from ICD_10_CM
code "ICD_10_CM_J_44_9" : 'J44.9' from ICD_10_CM
code "ICD_10_CM_I_25_10" : 'I25.10' from ICD_10_CM
code "ICD_10_CM_I_67_9" : 'I67.9' from ICD_10_CM
code "ICD_10_CM_E_66_9" : 'E66.9' from ICD_10_CM
code "LOINC_39156_5" : '39156-5' from LOINC
code "LOINC_59574_4" : '59574-4' from LOINC
code "LOINC_59575_1" : '59575-1' from LOINC
code "LOINC_59576_9" : '59576-9' from LOINC
code "LOINC_89270_3" : '89270-3' from LOINC
code "ICD_10_CM_N_28_9" : 'N28.9' from ICD_10_CM
code "ICD_10_CM_Z_59_3" : 'Z59.3' from ICD_10_CM
code "ICD_10_CM_C_80_1" : 'C80.1' from ICD_10_CM
code "ICD_10_CM_J_18_9" : 'J18.9' from ICD_10_CM
code "ICD_10_CM_I_51_7" : 'I51.7' from ICD_10_CM

// concept "Mental Status" : {  }
concept "GCS" : { "LOINC_9269_2" }
// concept "Work of Breathing" : {  }
// concept "Persistent Dyspnea" : {  }
concept "Hemoptysis" : { "ICD_10_CM_R_04_2" }
// concept "O2 Saturation" : {  }
concept "O2 Saturation (Ambulatory)" : { "CPT_94760","CPT_94761" }
concept "Respiratory Rate" : { "LOINC_9279_1" }
concept "Heart Rate" : { "LOINC_2544737_0" }
concept "Systolic BP" : { "LOINC_8480_6" }
concept "Diastolic BP" : { "LOINC_8462_4" }
concept "FiO2" : { "LOINC_3150_0","LOINC_3151_8","LOINC_71835_3" }
concept "Respiratory Support- Supplemental O2" : { "HCPCS_A4615" }
concept "Respiratory Support- High Flow Nasal Canula" : { "HCPCS_A4615" }
concept "Respiratory Support- Non Invasive Positive Pressure Ventilation" : { "CPT_94660_0","ICD_10_PCS_5_A_09357","ICD_10_PCS_5_A_09358","ICD_10_PCS_5_A_09457","ICD_10_PCS_5_A_09458","ICD_10_PCS_5_A_09557","ICD_10_PCS_5_A_09558" }
// concept "Respiratory Support- Mechanical Ventilation" : {  }
concept "Dobutamine" : { "HCPCS_J1250","RX_NORM_3616_0" }
concept "Epinephrine" : { "HCPCS_J0171","RX_NORM_3992_0" }
concept "Norepinephrine" : { "RX_NORM_7512_0" }
concept "Vasopressin" : { "RX_NORM_11149_0" }
concept "Dopamine" : { "HCPCS_J1265","RX_NORM_3628_0" }
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
concept "COVID-19 diagnosis (suspected)" : { "ICD_10_CM_Z_20_828" }
concept "COVID-19 diagnosis (confirmed)" : { "ICD_10_CM_U_07_1" }
concept "Diabetes Mellitus Type 1" : { "ICD_10_CM_E_10_9" }
concept "Diabetes Mellitus Type 2" : { "ICD_10_CM_E_11_9" }
concept "Heart Failure" : { "ICD_10_CM_I_50_9" }
concept "Hypertension" : { "ICD_10_CM_I10" }
concept "Chronic Obstructive Pulmonary Disease (COPD)" : { "ICD_10_CM_J_44_9" }
concept "Cardiovascular Disease" : { "ICD_10_CM_I_25_10" }
concept "Cerebrovascular disease" : { "ICD_10_CM_I_67_9" }
concept "Obesity" : { "ICD_10_CM_E_66_9" }
// concept "Obese" : {  }
concept "BMI" : { "LOINC_39156_5","LOINC_59574_4","LOINC_59575_1","LOINC_59576_9","LOINC_89270_3" }
concept "Renal Disease" : { "ICD_10_CM_N_28_9" }
concept "Long Term Care Facility Resident" : { "ICD_10_CM_Z_59_3" }
concept "Malignancy" : { "ICD_10_CM_C_80_1" }
// concept "CXR Score" : {  }
concept "Bilateral Pneumonia" : { "ICD_10_CM_J_18_9" }
concept "RV dilatation" : { "ICD_10_CM_I_51_7" }
concept "RV enlargement" : { "ICD_10_CM_I_51_7" }

