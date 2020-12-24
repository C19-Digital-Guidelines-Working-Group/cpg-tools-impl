package cpg.covid19.ed.cql;

import static cpg.util.fhir.CPGUtil.makeTechnicalLabel;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CQLRetrievesGenerator extends AbstractCQLRetrievesGenerator {

  Logger logger = LoggerFactory.getLogger(CQLRetrievesGenerator.class);

  @Override
  protected String getFileName() {
    return "retrieves.cql";
  }

  @Override
  protected String getLibraryName() {
    return "COVID19_ED_CPG_Retrieves";
  }

  @Override
  protected void buildCodeDeclarations(StringBuilder sb, List<SemanticDataElementInfo> infos) {
    infos.stream()
        .map(this::toTerm)
        .distinct()
        .map(this::toCodeDeclaration)
        .forEach(sb::append);
    sb.append("\n");

  }

  @Override
  protected String toConceptDeclaration(SemanticDataElementInfo trm) {
    return "concept "
        + "\"" + trm.dataElement + "\""
        + " : "
        + "{ "
        + makeTechnicalLabel(toTerm(trm))
        + " }"
        + "\n";
  }


}
