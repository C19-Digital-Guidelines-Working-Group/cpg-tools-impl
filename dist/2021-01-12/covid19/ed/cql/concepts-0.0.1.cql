library COVID19_ED_CPG_Concepts version '0.0.1'

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

code "C_19_ED_CPG_0d631b60_7489_3b5e_b816_dd4dd5d3b243" : '0d631b60-7489-3b5e-b816-dd4dd5d3b243' from C_19_ED_CPG
code "C_19_ED_CPG_0f783f73_2d10_3dbf_b0f2_55f311e83e67" : '0f783f73-2d10-3dbf-b0f2-55f311e83e67' from C_19_ED_CPG
code "C_19_ED_CPG_7d8d47da_454d_3f38_bee9_6e87d7e2657a" : '7d8d47da-454d-3f38-bee9-6e87d7e2657a' from C_19_ED_CPG
code "C_19_ED_CPG_78380b3f_a38e_39fe_ae3e_5ecfd7e8b086" : '78380b3f-a38e-39fe-ae3e-5ecfd7e8b086' from C_19_ED_CPG
code "C_19_ED_CPG_a3a8db59_2649_3876_9bf8_61f81878bef9" : 'a3a8db59-2649-3876-9bf8-61f81878bef9' from C_19_ED_CPG
code "C_19_ED_CPG_5ae3dc37_3287_398d_bf85_d8fdbfa36ba1" : '5ae3dc37-3287-398d-bf85-d8fdbfa36ba1' from C_19_ED_CPG
code "C_19_ED_CPG_e9c846d0_1f96_3181_8150_fb0ed1c149b1" : 'e9c846d0-1f96-3181-8150-fb0ed1c149b1' from C_19_ED_CPG
code "C_19_ED_CPG_79919c0e_1a3d_38ea_a212_e5db8ab30fca" : '79919c0e-1a3d-38ea-a212-e5db8ab30fca' from C_19_ED_CPG
code "C_19_ED_CPG_84a8ab48_fc39_307a_9f4f_99a89db68d73" : '84a8ab48-fc39-307a-9f4f-99a89db68d73' from C_19_ED_CPG
code "C_19_ED_CPG_98a2ce86_4178_3b16_b22e_d0471aca7a49" : '98a2ce86-4178-3b16-b22e-d0471aca7a49' from C_19_ED_CPG
code "C_19_ED_CPG_84aa13c4_6e2b_3997_9a98_8b16dc2ffdf3" : '84aa13c4-6e2b-3997-9a98-8b16dc2ffdf3' from C_19_ED_CPG
code "C_19_ED_CPG_019ec313_2cdf_3ee0_b2e2_a75cf5d3e459" : '019ec313-2cdf-3ee0-b2e2-a75cf5d3e459' from C_19_ED_CPG
code "C_19_ED_CPG_9d8d2d5a_b12b_3151_82a5_05f54db7f538" : '9d8d2d5a-b12b-3151-82a5-05f54db7f538' from C_19_ED_CPG
code "C_19_ED_CPG_4e221fc0_84bb_39a8_a315_a154d6dd7879" : '4e221fc0-84bb-39a8-a315-a154d6dd7879' from C_19_ED_CPG
code "C_19_ED_CPG_3bffa372_b64e_31de_8412_2300ceaf5910" : '3bffa372-b64e-31de-8412-2300ceaf5910' from C_19_ED_CPG
code "C_19_ED_CPG_f6e6247c_50c3_39f1_beba_de8703a55294" : 'f6e6247c-50c3-39f1-beba-de8703a55294' from C_19_ED_CPG
code "C_19_ED_CPG_7ac71826_bd37_3fc3_a8bf_2ecbe377e9fd" : '7ac71826-bd37-3fc3-a8bf-2ecbe377e9fd' from C_19_ED_CPG
code "C_19_ED_CPG_2b07c564_a2e2_393b_9fa4_99bce1f865c2" : '2b07c564-a2e2-393b-9fa4-99bce1f865c2' from C_19_ED_CPG
code "C_19_ED_CPG_270c354c_22ae_3077_8e0e_7f0ab0a2b483" : '270c354c-22ae-3077-8e0e-7f0ab0a2b483' from C_19_ED_CPG
code "C_19_ED_CPG_311eb6b1_c1c8_389c_8826_d8673cc4d623" : '311eb6b1-c1c8-389c-8826-d8673cc4d623' from C_19_ED_CPG
code "C_19_ED_CPG_39e38c19_caa5_39eb_a485_2fea6166e9fd" : '39e38c19-caa5-39eb-a485-2fea6166e9fd' from C_19_ED_CPG
code "C_19_ED_CPG_f7ac0c35_613d_3796_b45c_13fa031adcdb" : 'f7ac0c35-613d-3796-b45c-13fa031adcdb' from C_19_ED_CPG
code "C_19_ED_CPG_7a1c6d5b_f54f_39cc_bb36_e6843a58d1e4" : '7a1c6d5b-f54f-39cc-bb36-e6843a58d1e4' from C_19_ED_CPG
code "C_19_ED_CPG_d7e7bc21_3b84_3a95_9946_ff0d9cb165cb" : 'd7e7bc21-3b84-3a95-9946-ff0d9cb165cb' from C_19_ED_CPG
code "C_19_ED_CPG_18ba15e1_65ae_3f63_b08c_42dd43e3676e" : '18ba15e1-65ae-3f63-b08c-42dd43e3676e' from C_19_ED_CPG
code "C_19_ED_CPG_4becaa45_8d21_383b_89b7_8e17f06e6712" : '4becaa45-8d21-383b-89b7-8e17f06e6712' from C_19_ED_CPG
code "C_19_ED_CPG_5959cf6f_9a5d_3640_947a_e81012372314" : '5959cf6f-9a5d-3640-947a-e81012372314' from C_19_ED_CPG
code "C_19_ED_CPG_c434158d_0aa9_3676_b699_ea3d00be1e1b" : 'c434158d-0aa9-3676-b699-ea3d00be1e1b' from C_19_ED_CPG
code "C_19_ED_CPG_95bfa631_4574_3a2b_8c16_e6b08ab7c989" : '95bfa631-4574-3a2b-8c16-e6b08ab7c989' from C_19_ED_CPG
code "C_19_ED_CPG_1a5f251e_6304_3d4c_b207_6fe2a5961eaa" : '1a5f251e-6304-3d4c-b207-6fe2a5961eaa' from C_19_ED_CPG
code "C_19_ED_CPG_9547fdae_e53d_36d2_8c8d_546b2822d40f" : '9547fdae-e53d-36d2-8c8d-546b2822d40f' from C_19_ED_CPG
code "C_19_ED_CPG_fc070a93_5c12_34c2_8b3f_3cc7115e1059" : 'fc070a93-5c12-34c2-8b3f-3cc7115e1059' from C_19_ED_CPG
code "C_19_ED_CPG_136be6a5_dd5c_3e80_bba9_aa1dd3c9b056" : '136be6a5-dd5c-3e80-bba9-aa1dd3c9b056' from C_19_ED_CPG
code "C_19_ED_CPG_cfb06246_ab5b_3871_8385_120d5664f14f" : 'cfb06246-ab5b-3871-8385-120d5664f14f' from C_19_ED_CPG
code "C_19_ED_CPG_256c1862_05d1_3c22_bfd8_b34c40206eb5" : '256c1862-05d1-3c22-bfd8-b34c40206eb5' from C_19_ED_CPG
code "C_19_ED_CPG_dfbfc9cc_7a23_3560_b04c_b1a5faf1ec3e" : 'dfbfc9cc-7a23-3560-b04c-b1a5faf1ec3e' from C_19_ED_CPG
code "C_19_ED_CPG_c77bb0f6_9e2a_30e0_86a8_dc7f0da9320f" : 'c77bb0f6-9e2a-30e0-86a8-dc7f0da9320f' from C_19_ED_CPG
code "C_19_ED_CPG_efd4ddff_ab84_30a9_9b02_796d87f769a3" : 'efd4ddff-ab84-30a9-9b02-796d87f769a3' from C_19_ED_CPG
code "C_19_ED_CPG_cfe98e01_a3dc_33f9_b6a5_94d1f28f99bc" : 'cfe98e01-a3dc-33f9-b6a5-94d1f28f99bc' from C_19_ED_CPG
code "C_19_ED_CPG_e4f842e1_d5c7_3b8c_9819_31c6aaeaddc5" : 'e4f842e1-d5c7-3b8c-9819-31c6aaeaddc5' from C_19_ED_CPG
code "C_19_ED_CPG_8d45f1b7_8513_3589_bd37_fe16739d9d89" : '8d45f1b7-8513-3589-bd37-fe16739d9d89' from C_19_ED_CPG
code "C_19_ED_CPG_6ff5edf8_399a_3cfa_a52f_ab8f9069c90c" : '6ff5edf8-399a-3cfa-a52f-ab8f9069c90c' from C_19_ED_CPG
code "C_19_ED_CPG_01da3ef2_5313_3ba8_bd4e_2123c9ae77eb" : '01da3ef2-5313-3ba8-bd4e-2123c9ae77eb' from C_19_ED_CPG
code "C_19_ED_CPG_3dd9edb0_933b_39cf_b279_4bf515e224dc" : '3dd9edb0-933b-39cf-b279-4bf515e224dc' from C_19_ED_CPG
code "C_19_ED_CPG_6ef655d9_01f8_3763_a7d4_ad3e54504357" : '6ef655d9-01f8-3763-a7d4-ad3e54504357' from C_19_ED_CPG
code "C_19_ED_CPG_9fdb23fd_68c3_3b78_a52f_019db75d6f6e" : '9fdb23fd-68c3-3b78-a52f-019db75d6f6e' from C_19_ED_CPG
code "C_19_ED_CPG_9f825141_6f7e_3717_aa63_f96f1f10d7ac" : '9f825141-6f7e-3717-aa63-f96f1f10d7ac' from C_19_ED_CPG
code "C_19_ED_CPG_a1fcb0e0_c8f5_3d8c_a7ff_8a244b625669" : 'a1fcb0e0-c8f5-3d8c-a7ff-8a244b625669' from C_19_ED_CPG
code "C_19_ED_CPG_2c31b3cd_5258_36b2_b5e9_b49434ed3e3d" : '2c31b3cd-5258-36b2-b5e9-b49434ed3e3d' from C_19_ED_CPG
code "C_19_ED_CPG_4c5ef2ec_4498_3af9_91bb_f7556e025b41" : '4c5ef2ec-4498-3af9-91bb-f7556e025b41' from C_19_ED_CPG
code "C_19_ED_CPG_955c689f_130b_33ab_b64a_3c43dc6d6f81" : '955c689f-130b-33ab-b64a-3c43dc6d6f81' from C_19_ED_CPG
code "C_19_ED_CPG_986a4f4e_4179_3e81_9dc8_b2a297aa8c87" : '986a4f4e-4179-3e81-9dc8-b2a297aa8c87' from C_19_ED_CPG
code "C_19_ED_CPG_f9a1cd68_c82a_3361_bdc6_58e7fe11c0c3" : 'f9a1cd68-c82a-3361-bdc6-58e7fe11c0c3' from C_19_ED_CPG
code "C_19_ED_CPG_a5a5c081_4462_3dc8_99f3_e1471d1b4d4f" : 'a5a5c081-4462-3dc8-99f3-e1471d1b4d4f' from C_19_ED_CPG
code "C_19_ED_CPG_1e63bbb8_709a_300a_94e7_d47fcd830d85" : '1e63bbb8-709a-300a-94e7-d47fcd830d85' from C_19_ED_CPG
code "C_19_ED_CPG_af583d30_4a8e_330d_a864_926fd59a0d0f" : 'af583d30-4a8e-330d-a864-926fd59a0d0f' from C_19_ED_CPG

concept "Mental Status" : { C_19_ED_CPG_0d631b60_7489_3b5e_b816_dd4dd5d3b243 }
concept "GCS" : { C_19_ED_CPG_0f783f73_2d10_3dbf_b0f2_55f311e83e67 }
concept "Work of Breathing" : { C_19_ED_CPG_7d8d47da_454d_3f38_bee9_6e87d7e2657a }
concept "Persistent Dyspnea" : { C_19_ED_CPG_78380b3f_a38e_39fe_ae3e_5ecfd7e8b086 }
concept "Hemoptysis" : { C_19_ED_CPG_a3a8db59_2649_3876_9bf8_61f81878bef9 }
concept "O2 Saturation" : { C_19_ED_CPG_5ae3dc37_3287_398d_bf85_d8fdbfa36ba1 }
concept "O2 Saturation (Ambulatory)" : { C_19_ED_CPG_e9c846d0_1f96_3181_8150_fb0ed1c149b1 }
concept "Respiratory Rate" : { C_19_ED_CPG_79919c0e_1a3d_38ea_a212_e5db8ab30fca }
concept "Heart Rate" : { C_19_ED_CPG_84a8ab48_fc39_307a_9f4f_99a89db68d73 }
concept "Systolic BP" : { C_19_ED_CPG_98a2ce86_4178_3b16_b22e_d0471aca7a49 }
concept "Diastolic BP" : { C_19_ED_CPG_84aa13c4_6e2b_3997_9a98_8b16dc2ffdf3 }
concept "FiO2" : { C_19_ED_CPG_3bffa372_b64e_31de_8412_2300ceaf5910 }
concept "Respiratory Support- Supplemental O2" : { C_19_ED_CPG_f6e6247c_50c3_39f1_beba_de8703a55294 }
concept "Respiratory Support- High Flow Nasal Canula" : { C_19_ED_CPG_7ac71826_bd37_3fc3_a8bf_2ecbe377e9fd }
concept "Respiratory Support- Non Invasive Positive Pressure Ventilation" : { C_19_ED_CPG_2b07c564_a2e2_393b_9fa4_99bce1f865c2 }
concept "Respiratory Support- Mechanical Ventilation" : { C_19_ED_CPG_270c354c_22ae_3077_8e0e_7f0ab0a2b483 }
concept "Dobutamine" : { C_19_ED_CPG_311eb6b1_c1c8_389c_8826_d8673cc4d623 }
concept "Epinephrine" : { C_19_ED_CPG_39e38c19_caa5_39eb_a485_2fea6166e9fd }
concept "Norepinephrine" : { C_19_ED_CPG_f7ac0c35_613d_3796_b45c_13fa031adcdb }
concept "Vasopressin" : { C_19_ED_CPG_7a1c6d5b_f54f_39cc_bb36_e6843a58d1e4 }
concept "Dopamine" : { C_19_ED_CPG_d7e7bc21_3b84_3a95_9946_ff0d9cb165cb }
concept "SARS-CoV-2 test" : { C_19_ED_CPG_18ba15e1_65ae_3f63_b08c_42dd43e3676e }
concept "Troponin I" : { C_19_ED_CPG_4becaa45_8d21_383b_89b7_8e17f06e6712 }
concept "D-dimer" : { C_19_ED_CPG_5959cf6f_9a5d_3640_947a_e81012372314 }
concept "Lactate" : { C_19_ED_CPG_c434158d_0aa9_3676_b699_ea3d00be1e1b }
concept "Lactate Dehydrogenase" : { C_19_ED_CPG_95bfa631_4574_3a2b_8c16_e6b08ab7c989 }
concept "C-Reactive Peptide" : { C_19_ED_CPG_1a5f251e_6304_3d4c_b207_6fe2a5961eaa }
concept "Neutrophils (#)" : { C_19_ED_CPG_9547fdae_e53d_36d2_8c8d_546b2822d40f }
concept "Lymphocytes (#)" : { C_19_ED_CPG_fc070a93_5c12_34c2_8b3f_3cc7115e1059 }
concept "Lymphocyte (%)" : { C_19_ED_CPG_136be6a5_dd5c_3e80_bba9_aa1dd3c9b056 }
concept "Ferritin" : { C_19_ED_CPG_cfb06246_ab5b_3871_8385_120d5664f14f }
concept "Platelets" : { C_19_ED_CPG_256c1862_05d1_3c22_bfd8_b34c40206eb5 }
concept "ALT" : { C_19_ED_CPG_dfbfc9cc_7a23_3560_b04c_b1a5faf1ec3e }
concept "AST" : { C_19_ED_CPG_c77bb0f6_9e2a_30e0_86a8_dc7f0da9320f }
concept "COVID-19 diagnosis (suspected)" : { C_19_ED_CPG_efd4ddff_ab84_30a9_9b02_796d87f769a3 }
concept "COVID-19 diagnosis (confirmed)" : { C_19_ED_CPG_cfe98e01_a3dc_33f9_b6a5_94d1f28f99bc }
concept "Diabetes Mellitus Type 1" : { C_19_ED_CPG_e4f842e1_d5c7_3b8c_9819_31c6aaeaddc5 }
concept "Diabetes Mellitus Type 2" : { C_19_ED_CPG_8d45f1b7_8513_3589_bd37_fe16739d9d89 }
concept "Heart Failure" : { C_19_ED_CPG_6ff5edf8_399a_3cfa_a52f_ab8f9069c90c }
concept "Hypertension" : { C_19_ED_CPG_01da3ef2_5313_3ba8_bd4e_2123c9ae77eb }
concept "Chronic Obstructive Pulmonary Disease (COPD)" : { C_19_ED_CPG_3dd9edb0_933b_39cf_b279_4bf515e224dc }
concept "Cardiovascular Disease" : { C_19_ED_CPG_6ef655d9_01f8_3763_a7d4_ad3e54504357 }
concept "Cerebrovascular disease" : { C_19_ED_CPG_9fdb23fd_68c3_3b78_a52f_019db75d6f6e }
concept "Obesity" : { C_19_ED_CPG_9f825141_6f7e_3717_aa63_f96f1f10d7ac }
concept "Obese" : { C_19_ED_CPG_a1fcb0e0_c8f5_3d8c_a7ff_8a244b625669 }
concept "BMI" : { C_19_ED_CPG_2c31b3cd_5258_36b2_b5e9_b49434ed3e3d }
concept "Renal Disease" : { C_19_ED_CPG_4c5ef2ec_4498_3af9_91bb_f7556e025b41 }
concept "Long Term Care Facility Resident" : { C_19_ED_CPG_955c689f_130b_33ab_b64a_3c43dc6d6f81 }
concept "Malignancy" : { C_19_ED_CPG_986a4f4e_4179_3e81_9dc8_b2a297aa8c87 }
concept "CXR Score" : { C_19_ED_CPG_f9a1cd68_c82a_3361_bdc6_58e7fe11c0c3 }
concept "Bilateral Pneumonia" : { C_19_ED_CPG_a5a5c081_4462_3dc8_99f3_e1471d1b4d4f }
concept "RV dilatation" : { C_19_ED_CPG_1e63bbb8_709a_300a_94e7_d47fcd830d85 }
concept "RV enlargement" : { C_19_ED_CPG_af583d30_4a8e_330d_a864_926fd59a0d0f }

