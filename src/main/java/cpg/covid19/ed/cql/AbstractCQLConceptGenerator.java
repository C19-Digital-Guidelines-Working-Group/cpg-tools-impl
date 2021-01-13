package cpg.covid19.ed.cql;

import static cpg.util.fhir.CPGUtil.getCodeSystem;
import static cpg.util.fhir.CPGUtil.lookupCodeSystem;
import static cpg.util.fhir.CPGUtil.sanitizeCQLIdentifier;

import cpg.covid19.ed.AbstractOntologyDrivenGenerator;
import cpg.util.fhir.CPGUtil;
import java.util.List;
import org.omg.spec.api4kp._20200801.id.Term;

public abstract class AbstractCQLConceptGenerator extends AbstractGlossaryCQLGenerator {



   public String toCQL(List<SemanticDataElementInfo> infos) {
    StringBuilder sb = new StringBuilder();

    buildHeader(infos, sb);
    buildCodeDeclarations(sb, infos);
    buildConceptDeclarations(sb, infos);

    return sb.toString();
  }


  protected void buildHeader(
      List<SemanticDataElementInfo> infos,
      StringBuilder sb) {
    sb.append("library ").append(getLibraryName(infos)).append(" version '0.0.1'").append("\n\n");
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
