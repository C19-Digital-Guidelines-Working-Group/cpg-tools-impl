package cpg.api4kp.tranx.validators.cql.fhir.r4;

import static org.omg.spec.api4kp._20200801.taxonomy.parsinglevel.ParsingLevelSeries.Encoded_Knowledge_Expression;

import cpg.api4kp.common.cql.CQL2ELMHelper;
import cpg.api4kp.tranx.validators.cql.fhir.r4.CQLR4Validator.CQLValidatorConfig.CQLValidatorParams;
import edu.mayo.kmdp.ConfigProperties;
import edu.mayo.kmdp.Opt;
import edu.mayo.kmdp.Option;
import edu.mayo.kmdp.util.PropertiesUtil;
import edu.mayo.kmdp.util.Util;
import edu.mayo.ontology.taxonomies.ws.responsecodes.ResponseCodeSeries;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.CqlTranslatorException;
import org.omg.spec.api4kp._20200801.Answer;
import org.omg.spec.api4kp._20200801.api.transrepresentation.v4.server.ValidateApiInternal._applyValidate;
import org.omg.spec.api4kp._20200801.services.KnowledgeCarrier;

public class CQLR4Validator implements _applyValidate {

  CQL2ELMHelper helper = new CQL2ELMHelper();

  public enum ErrorLevels {
    ERROR, EXCEPTION, MESSAGE;
  }

  @Override
  public Answer<Void> applyValidate(KnowledgeCarrier knowledgeCarrier, String config) {
    if (!Encoded_Knowledge_Expression.sameAs(knowledgeCarrier.getLevel())) {
      return Answer.unsupported();
    }

    try {
      CqlTranslator translator = helper.tryTranslate(knowledgeCarrier);
      Optional<String> errors = validate(translator, parseConfig(config));
      return errors.isEmpty()
          ? Answer.succeed()
          : explain(errors.get());
    } catch (IOException e) {
      return Answer.failed(e);
    }

  }

  private CQLValidatorConfig parseConfig(String config) {
    return Util.isEmpty(config)
        ? new CQLValidatorConfig()
        : new CQLValidatorConfig(PropertiesUtil.parseProperties(config));
  }

  private Answer<Void> explain(String errors) {
    Answer<Void> ans = Answer.failed(ResponseCodeSeries.BadRequest);
    ans.withExplanation(errors);
    return ans;
  }

  private Optional<String> validate(CqlTranslator translator,
      CQLValidatorConfig cfg) {
    StringBuilder sb = new StringBuilder();
    ErrorLevels level = getLevel(cfg);

    if (level.ordinal() >= ErrorLevels.ERROR.ordinal()) {
      translator.getErrors()
          .forEach(err -> sb.append("ERR #")
              .append(err.getLocator().getStartLine())
              .append(" :")
              .append(err.getMessage()).append("\n"));
    }
    if (level.ordinal() >= ErrorLevels.EXCEPTION.ordinal()) {
      translator.getExceptions()
          .forEach(err -> sb.append("EXC #")
          .append(err.getLocator().getStartLine())
          .append(" :")
          .append(err.getMessage()).append("\n"));
    }
    if (level.ordinal() >= ErrorLevels.MESSAGE.ordinal()) {
      translator.getMessages().
          forEach(err -> sb.append("MSG #")
          .append(err.getLocator().getStartLine())
          .append(" :")
          .append(err.getMessage()).append("\n"));
    }
    String str = sb.toString();
    return Util.isEmpty(str) ? Optional.empty() : Optional.of(str);
  }

  private ErrorLevels getLevel(CQLValidatorConfig cfg) {
    return cfg.get(CQLValidatorParams.LEVEL)
        .map(String::toUpperCase)
        .map(ErrorLevels::valueOf)
        .orElse(ErrorLevels.MESSAGE);
  }

  public static class CQLValidatorConfig extends
      ConfigProperties<CQLValidatorConfig, CQLValidatorParams> {

    private static final Properties DEFAULTS = defaulted(
        CQLValidatorConfig.CQLValidatorParams.class);

    public CQLValidatorConfig() {
      super(DEFAULTS);
    }

    public CQLValidatorConfig(Properties defaults) {
      super(defaults);
    }

    @Override
    public CQLValidatorConfig.CQLValidatorParams[] properties() {
      return CQLValidatorConfig.CQLValidatorParams.values();
    }

    public String encode() {
      return PropertiesUtil.serializeProps(this);
    }

    public enum CQLValidatorParams implements
        Option<CQLValidatorConfig.CQLValidatorParams> {

      LEVEL(Opt.of(
          "level",
          Boolean.TRUE.toString(),
          "Error level",
          String.class,
          true)),

      ;

      private Opt<CQLValidatorConfig.CQLValidatorParams> opt;

      CQLValidatorParams(Opt<CQLValidatorConfig.CQLValidatorParams> opt) {
        this.opt = opt;
      }

      @Override
      public Opt<CQLValidatorConfig.CQLValidatorParams> getOption() {
        return opt;
      }
    }
  }
}
