package cpg.covid19.ed.cql;

import static cpg.util.fhir.CPGUtil.makeTechnicalLabel;

import java.util.List;
import net.sf.saxon.trans.SymbolicName.F;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CQLConceptsGenerator extends AbstractCQLConceptGenerator {

  Logger logger = LoggerFactory.getLogger(CQLConceptsGenerator.class);

  public static final String NAME = "concepts";
  public static final String VERSION = "0.0.1";
  public static final String FILE_NAME = NAME + "-" + VERSION + ".cql";
  public static final String LIBRARY_NAME = "COVID19_ED_CPG_Concepts";

  @Override
  protected String getFileName() {
    return FILE_NAME;
  }

  @Override
  protected String getLibraryName() {
    return LIBRARY_NAME;
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
