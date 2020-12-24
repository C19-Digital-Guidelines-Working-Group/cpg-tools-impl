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

public abstract class AbstractCQLRetrievesGenerator extends AbstractOntologyDrivenGenerator {

  Logger logger = LoggerFactory.getLogger(AbstractCQLRetrievesGenerator.class);

  @Override
  public void run(Path srcPath, Path tgtPath) {
    logger.info("Generate CQL Retrieves from Data Glossary...");
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


  protected String toCQL(List<SemanticDataElementInfo> infos) {
    StringBuilder sb = new StringBuilder();

    buildHeader(sb);
    buildCodeDeclarations(sb, infos);
    buildConceptDeclarations(sb, infos);
    buildRetrieves(sb, infos);

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

  protected void buildRetrieves(StringBuilder sb, List<SemanticDataElementInfo> infos) {
    infos.forEach(
        info -> info.fhirResources.stream()
            .forEach(res ->  {
              sb.append(toRetrieve(info, res.trim())).append("\n");
              sb.append(toLifts(info, res.trim())).append("\n");
            }));
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

  protected String toLifts(SemanticDataElementInfo info, String resourceType) {
    if (info.kind == ConceptKind.DEMOGRAPHICS) {
      return "";
    }

    StringBuilder def = new StringBuilder();
    List<Interrogatives> interrogatives = getAdmissibleInterrogatives(resourceType);
    String baseExpr =  getDefaultRetrieveName(info.label(), resourceType);

    interrogatives.forEach(interr -> {
      def.append("define "
          + "\"" + getDefaultLiftName(baseExpr, interr.label) + "\" : " + "\n"
          + "\t");
      switch (interr) {
        case IS:
          def.append("Exists( \"" + baseExpr + "\") " + "\n" );
          break;
        case KIND_OF:
          def.append("\"" + baseExpr + "\".code " + "\n");
          break;
        case VALUE_OF:
          def.append("\"" + baseExpr + "\".value " + "\n");
          break;
        default:
          throw new UnsupportedOperationException();
      }
      def.append("\n");
    });

    return def.toString();
  }

  protected List<Interrogatives> getAdmissibleInterrogatives(String resourceType) {
    switch (resourceType) {
      case "Observation":
        return Arrays.asList(Interrogatives.IS,Interrogatives.KIND_OF,Interrogatives.VALUE_OF);
      case "Procedure":
        return Arrays.asList(Interrogatives.IS,Interrogatives.KIND_OF);
      case "Patient":
        return Collections.emptyList();
      default:
        return Arrays.asList(Interrogatives.IS);
    }
  }

  protected String toRetrieve(SemanticDataElementInfo info, String resourceType) {
    if (info.kind == ConceptKind.DEMOGRAPHICS) {
      return "";
    }

    String def = "//"
        + getSituationUri(info)
        + "\n";
    def += "define "
        + "\""
        + getDefaultRetrieveName(info.label(),resourceType)
        + "\" : " + "\n"
        + "\t"
        + "[ " + resourceType
        + ":"
        + "\"" + info.dataElement + "\""
        + " ] X";
    def += "\n\t\t"
        + "where "
        + getValidityFilters(FHIRAllTypes.fromCode(resourceType));
    if (info.temporal != null) {
      def += "\n\t\t"
          + "and X."
          + getDefaultTimeFilter(FHIRAllTypes.fromCode(resourceType))
          + " between Now() and Now() - "
          + info.temporal.getValue().intValue()
          + " "
          + info.temporal.getUnit();
    }
    def += "\n\t\t\t"
        + "sort by "
        + getDefaultTimeFilter(FHIRAllTypes.fromCode(resourceType))
        + " desc";
    def += "\n";
    return def;
  }

  protected String getValidityFilters(FHIRAllTypes resourceType) {
    switch (resourceType) {
      case MEDICATIONSTATEMENT:
        return "X.status in { 'active' }";
      case DIAGNOSTICREPORT:
        return "X.status in { 'final', 'registered' }";
      case OBSERVATION:
        return "X.status in { 'final', 'registered', 'amended' }";
      case PROCEDURE:
        return "X.status in { 'completed' }";
      case CONDITION:
        return "X.clinicalStatus in { 'active', 'recurrence', 'relapse' }"
            + " and "
            + " X.verificationStatus in { 'confirmed' }";
      case DEVICEREQUEST:
        return "X.status in { 'active', 'completed' } ";
      case MEDICATIONREQUEST:
        return "X.status in { 'active', 'completed' } ";
      case SERVICEREQUEST:
        return "X.status in { 'active', 'completed' } ";
      default:
        throw new UnsupportedOperationException("Cannot define a status filter on " + resourceType);
    }
  }

  public static String getDefaultRetrieveName(String label, String resourceType) {
    return label + " - " + resourceType;
  }

  public static String getDefaultLiftName(String baseExpr, String label) {
    return baseExpr + " - " + label;
  }


  protected abstract void buildCodeDeclarations(StringBuilder sb, List<SemanticDataElementInfo> infos);

  protected abstract String toConceptDeclaration(SemanticDataElementInfo trm);

  protected abstract String getFileName();

  protected abstract String getLibraryName();


}
