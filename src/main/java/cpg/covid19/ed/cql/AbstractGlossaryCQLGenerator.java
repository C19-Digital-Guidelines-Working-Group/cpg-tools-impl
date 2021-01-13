package cpg.covid19.ed.cql;

import cpg.covid19.ed.AbstractOntologyDrivenGenerator;
import cpg.covid19.ed.AbstractOntologyDrivenGenerator.SemanticDataElementInfo;
import java.nio.file.Path;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractGlossaryCQLGenerator extends AbstractOntologyDrivenGenerator
  implements CQLGenerator<List<SemanticDataElementInfo>> {

  @Override
  public List<SemanticDataElementInfo> readSources(Path srcPath) {
    return readDataElements(srcPath);
  }

  protected String needsBlockComment(SemanticDataElementInfo info) {
    return info.isEmpty() ? "/*\n" : "\n";
  }

  protected String needsCloseBlockComment(SemanticDataElementInfo info) {
    return info.isEmpty() ? "\n*/\n" : "\n";
  }

  protected String needsLineComment(SemanticDataElementInfo trm) {
    return trm.isEmpty() ? "// " : "";
  }

  protected String fixme(SemanticDataElementInfo info) {
    return info.isEmpty() ? " FIXME " : " /*FIXME*/ ";
  }

}
