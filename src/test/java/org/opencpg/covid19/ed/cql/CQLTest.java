package org.opencpg.covid19.ed.cql;

import static java.nio.charset.Charset.defaultCharset;
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
  void testValidateRetrieve() {
    InputStream is = CQLTest.class.getResourceAsStream("/opencpg/covid19/ed/cql/retrieves.cql");
    KnowledgeCarrier kc = AbstractCarrier.of(is)
        .withRepresentation(rep(HL7_CQL_1_4_1, TXT, defaultCharset(), Encodings.DEFAULT));

    Answer<Void> ans = new CQLR4Validator()
        .applyValidate(kc, new CQLValidatorConfig()
            .with(CQLValidatorParams.LEVEL, ErrorLevels.ERROR).encode());
    System.out.println(ans.getExplanation().asString().orElse("FAIL"));
  }
}
