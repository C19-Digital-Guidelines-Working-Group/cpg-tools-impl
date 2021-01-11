package cpg.covid19.ed.cql;

import static java.util.stream.Collectors.joining;

import cpg.util.fhir.CPGUtil;
import java.util.List;

public class CQLPreMappedRetrievesGenerator extends AbstractCQLRetrievesGenerator {

  @Override
  protected void buildCodeDeclarations(StringBuilder sb, List<SemanticDataElementInfo> infos) {
    infos.stream()
        .flatMap(SemanticDataElementInfo::allConcepts)
        .distinct()
        .map(this::toCodeDeclaration)
        .forEach(sb::append);
    sb.append("\n");
  }

  @Override
  protected String toConceptDeclaration(SemanticDataElementInfo trm) {
    return needsLineComment(trm) + "concept "
        + "\"" + trm.dataElement + "\""
        + " : "
        + "{ "
        + trm.allConcepts()
        .map(t -> "\"" + CPGUtil.makeTechnicalLabel(t) + "\"")
        .collect(joining(","))
        + " }"
        + "\n";
  }

  @Override
  protected String getFileName() {
    return "retrieves.premapped.cql";
  }

  @Override
  protected String getLibraryName() {
    return "COVID19_ED_CPG_Retrieves_Premapped";
  }


}
