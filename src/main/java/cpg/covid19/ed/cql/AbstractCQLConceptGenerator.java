package cpg.covid19.ed.cql;

import static cpg.util.fhir.CPGUtil.getCodeSystem;
import static cpg.util.fhir.CPGUtil.lookupCodeSystem;
import static cpg.util.fhir.CPGUtil.sanitizeCQLIdentifier;

import cpg.covid19.ed.AbstractOntologyDrivenGenerator;
import cpg.util.fhir.CPGUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes;
import org.omg.spec.api4kp._20200801.id.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCQLConceptGenerator extends AbstractCQLGenerator {

  Logger logger = LoggerFactory.getLogger(AbstractCQLConceptGenerator.class);

  protected String toCQL(List<SemanticDataElementInfo> infos) {
    StringBuilder sb = new StringBuilder();

    buildHeader(sb);
    buildCodeDeclarations(sb, infos);
    buildConceptDeclarations(sb, infos);

    return sb.toString();
  }


  protected void buildHeader(StringBuilder sb) {
    sb.append("library " + getLibraryName() + " version '0.0.1'").append("\n\n");
    sb.append("using FHIR version '4.0.1'").append("\n\n");
    sb.append("include FHIRHelpers version '4.0.1'").append("\n\n");

    CPGUtil.getCodeSystems().forEach(cs ->
        sb.append("codesystem \"")
            .append(sanitizeCQLIdentifier(cs))
            .append("\": '")
            .append(getCodeSystem(cs))
            .append("'").append("\n")
    );
    sb.append("\n");
  }

  protected Term toTerm(SemanticDataElementInfo semanticDataElementInfo) {
    return Term.newTerm(
        AbstractOntologyDrivenGenerator.getSituationUri(semanticDataElementInfo));
  }

  protected void buildConceptDeclarations(StringBuilder sb, List<SemanticDataElementInfo> infos) {
    infos.stream()
        .map(this::toConceptDeclaration)
        .forEach(sb::append);
    sb.append("\n");
  }


  protected String toCodeDeclaration(Term trm) {
    return "code "
        + "\""
        + CPGUtil.makeTechnicalLabel(trm)
        + "\""
        + " : "
        + "'"
        + trm.getTag()
        + "'"
        + " from "
        + sanitizeCQLIdentifier(lookupCodeSystem(trm.getNamespaceUri()))
        + "\n";
  }

  protected abstract void buildCodeDeclarations(StringBuilder sb, List<SemanticDataElementInfo> infos);

  protected abstract String toConceptDeclaration(SemanticDataElementInfo trm);

}
