package cpg.covid19.ed.cql;

import static cpg.util.fhir.CPGUtil.makeTechnicalLabel;

import java.util.List;

public class CQLConceptsGenerator extends AbstractCQLConceptGenerator {

  public static final String NAME = "concepts";
  public static final String VERSION = "0.0.1";
  public static final String FILE_NAME = NAME + "-" + VERSION + ".cql";
  public static final String LIBRARY_NAME = "COVID19_ED_CPG_Concepts";

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
        .map(this::toTerm)
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

    return "concept "
        + "\"" + info.dataElement + "\""
        + " : "
        + "{ "
        + makeTechnicalLabel(toTerm(info))
        + " }"
        + "\n";
  }


}
