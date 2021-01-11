package org.opencpg.covid19.ed.cql;

import static java.nio.charset.Charset.defaultCharset;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.omg.spec.api4kp._20200801.AbstractCarrier.rep;
import static org.omg.spec.api4kp._20200801.taxonomy.krformat.SerializationFormatSeries.TXT;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.HL7_CQL_1_4_1;

import cpg.api4kp.tranx.validators.cql.fhir.r4.CQLR4Validator;
import cpg.api4kp.tranx.validators.cql.fhir.r4.CQLR4Validator.CQLValidatorConfig;
import cpg.api4kp.tranx.validators.cql.fhir.r4.CQLR4Validator.CQLValidatorConfig.CQLValidatorParams;
import cpg.api4kp.tranx.validators.cql.fhir.r4.CQLR4Validator.ErrorLevels;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.omg.spec.api4kp._20200801.AbstractCarrier;
import org.omg.spec.api4kp._20200801.AbstractCarrier.Encodings;
import org.omg.spec.api4kp._20200801.Answer;
import org.omg.spec.api4kp._20200801.services.KnowledgeCarrier;

public class CQLTest {

  @Test
  void testValidateRetrieves() {
    InputStream is = CQLTest.class.getResourceAsStream("/opencpg/covid19/ed/cql/retrieves-0.0.1.cql");
    validate(is);
  }

  @Test
  void testValidateConceptsPremapped() {
    InputStream is = CQLTest.class.getResourceAsStream("/opencpg/covid19/ed/cql/concepts.premapped-0.0.1.cql");
    validate(is);
  }

  @Test
  void testValidateConcepts() {
    InputStream is = CQLTest.class.getResourceAsStream("/opencpg/covid19/ed/cql/concepts-0.0.1.cql");
    validate(is);
  }

  @Test
  void testValidateCommon() {
    InputStream is = CQLTest.class.getResourceAsStream(
        "/opencpg/covid19/ed/cql/CDS_Connect_Commons_for_FHIRv400-1.0.2.cql");
    //validate(is);
  }

  @Test
  void testValidateDemographics() {
    InputStream is = CQLTest.class.getResourceAsStream(
        "/opencpg/covid19/ed/cql/Patient_Demographics_FHIRv400-1.0.0.cql");
    validate(is);
  }

  void validate(InputStream is) {
    KnowledgeCarrier kc = AbstractCarrier.of(is)
        .withRepresentation(rep(HL7_CQL_1_4_1, TXT, defaultCharset(), Encodings.DEFAULT));

    Answer<Void> ans = new CQLR4Validator()
        .applyValidate(kc, new CQLValidatorConfig()
            .with(CQLValidatorParams.LEVEL, ErrorLevels.ERROR).encode());
    if (ans.getExplanation() != null) {
      System.out.println(ans.getExplanation().asString().orElse("FAIL"));
    }
    assertNull(ans.getExplanation());
  }
}
