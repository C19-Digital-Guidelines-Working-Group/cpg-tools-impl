package cpg.covid19.ed.cql;

import static org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes.CONDITION;
import static org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes.DEVICEREQUEST;
import static org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes.DIAGNOSTICREPORT;
import static org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes.MEDICATIONREQUEST;
import static org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes.MEDICATIONSTATEMENT;
import static org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes.OBSERVATION;
import static org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes.PROCEDURE;
import static org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes.SERVICEREQUEST;

import cpg.covid19.ed.AbstractOntologyDrivenGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCQLGenerator extends AbstractOntologyDrivenGenerator {

  Logger logger = LoggerFactory.getLogger(AbstractCQLGenerator.class);

  @Override
  public void run(Path srcPath, Path tgtPath) {
    logger.info("Generate CQL from Data Glossary..." + getLibraryName());
    List<SemanticDataElementInfo> infos = readDataElements(srcPath);

    String cqlRetrieves = toCQL(infos);

    save(cqlRetrieves, tgtPath);
  }

  protected void save(String cql, Path tgtPath) {
    Path file = tgtPath.resolve(getFileName());
    try {
      if (! Files.exists(file.getParent())) {
        Files.createDirectories(file.getParent());
      }
      Files.write(file,cql.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  protected abstract String toCQL(List<SemanticDataElementInfo> infos);


  protected abstract String getFileName();

  protected abstract String getLibraryName();


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
