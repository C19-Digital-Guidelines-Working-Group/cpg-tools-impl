package cpg.covid19.ed.cql;

import static java.util.stream.Collectors.joining;

import cpg.util.fhir.CPGUtil;
import java.util.List;

public class CQLPreMappedConceptsGenerator extends AbstractCQLConceptGenerator {

  public static final String NAME = "concepts.premapped";
  public static final String VERSION = "0.0.1";
  public static final String FILE_NAME = NAME + "-" + VERSION + ".cql";
  public static final String LIBRARY_NAME = "COVID19_ED_CPG_Concepts_Premapped";

  @Override
  public String getFileName(List<SemanticDataElementInfo> source) {
    return FILE_NAME;
  }

  @Override
  public String getLibraryName(List<SemanticDataElementInfo> source) {
    return LIBRARY_NAME;
  }

  @Override
  public String getVersion(List<SemanticDataElementInfo> source) {
    return VERSION;
  }

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
  protected String toConceptDeclaration(SemanticDataElementInfo info) {
    if (info.kind == ConceptKind.DEMOGRAPHICS) {
      return "";
    }

    return needsLineComment(info) + "concept "
        + "\"" + info.dataElement + "\""
        + " : "
        + "{ "
        + info.allConcepts()
        .map(t -> "\"" + CPGUtil.makeTechnicalLabel(t) + "\"")
        .collect(joining(","))
        + " }"
        + "\n";
  }

}
