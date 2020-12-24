package cpg.api4kp.tranx.parsers.cql.fhir.r4;

import static org.omg.spec.api4kp._20200801.AbstractCarrier.rep;
import static org.omg.spec.api4kp._20200801.taxonomy.krformat.SerializationFormatSeries.TXT;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.HL7_CQL_1_4_1;

import edu.mayo.kmdp.language.parsers.AbstractDeSerializeOperator;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import org.cqframework.cql.cql2elm.CqlTranslator;
import org.omg.spec.api4kp._20200801.AbstractCarrier.Encodings;
import org.omg.spec.api4kp._20200801.api.transrepresentation.v4.server.DeserializeApiInternal._applyLift;
import org.omg.spec.api4kp._20200801.services.KnowledgeCarrier;
import org.omg.spec.api4kp._20200801.services.SyntacticRepresentation;
import org.omg.spec.api4kp._20200801.taxonomy.krformat.SerializationFormat;
import org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguage;

public class CQLR4Parser extends AbstractDeSerializeOperator
    implements _applyLift {

  @Override
  protected List<SyntacticRepresentation> getSupportedRepresentations() {
    return Arrays.asList(
        rep(HL7_CQL_1_4_1),
        rep(HL7_CQL_1_4_1, TXT),
        rep(HL7_CQL_1_4_1, TXT, Charset.defaultCharset()),
        rep(HL7_CQL_1_4_1, TXT, Charset.defaultCharset(), Encodings.DEFAULT)
    );
  }

  @Override
  protected SerializationFormat getDefaultFormat() {
    return TXT;
  }

  @Override
  public Optional<KnowledgeCarrier> innerDeserialize(KnowledgeCarrier carrier, Properties config) {
    return Optional.empty();
  }

  @Override
  public Optional<KnowledgeCarrier> innerParse(KnowledgeCarrier carrier, Properties config) {
    return Optional.empty();
  }

  @Override
  public Optional<KnowledgeCarrier> innerAbstract(KnowledgeCarrier carrier, Properties config) {
    return Optional.empty();
  }

  @Override
  public KnowledgeRepresentationLanguage getSupportedLanguage() {
    return HL7_CQL_1_4_1;
  }





  @Override
  public Optional<KnowledgeCarrier> innerEncode(KnowledgeCarrier carrier,
      SyntacticRepresentation into, Properties config) {
    return Optional.empty();
  }

  @Override
  public Optional<KnowledgeCarrier> innerExternalize(KnowledgeCarrier carrier,
      SyntacticRepresentation into, Properties config) {
    return Optional.empty();
  }

  @Override
  public Optional<KnowledgeCarrier> innerSerialize(KnowledgeCarrier carrier,
      SyntacticRepresentation into, Properties config) {
    return Optional.empty();
  }

  @Override
  public Optional<KnowledgeCarrier> innerConcretize(KnowledgeCarrier carrier,
      SyntacticRepresentation into, Properties config) {
    return Optional.empty();
  }
}
