package cpg.covid19.ed.cql;

import static org.omg.spec.api4kp._20200801.id.Term.newTerm;

import cpg.covid19.ed.AbstractOntologyDrivenGenerator;
import cpg.util.fhir.CPGUtil;
import edu.mayo.kmdp.terms.TermsHelper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes;
import org.omg.spec.api4kp._20200801.id.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CQLRetrievesGenerator extends AbstractOntologyDrivenGenerator {

  Logger logger = LoggerFactory.getLogger(CQLRetrievesGenerator.class);

  @Override
  public void run(Path srcPath, Path tgtPath) {
    logger.info("Generate CQL Retrieves from Data Glossary...");
    List<SemanticDataElementInfo> infos = readDataElements(srcPath);

    String cqlRetrieves = toCQL(infos);

    save(cqlRetrieves, tgtPath);
  }

  private void save(String cql, Path tgtPath) {
    Path file = tgtPath.resolve("retrieves.cql");
    try {
      if (! Files.exists(file.getParent())) {
        Files.createDirectories(file.getParent());
      }
      Files.write(file,cql.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String toCQL(List<SemanticDataElementInfo> infos) {
    StringBuilder sb = new StringBuilder();

    buildHeader(sb);
    buildCodeDeclarations(sb, infos);
    buildConceptDeclarations(sb, infos);
    buildRetrieves(sb, infos);

    return sb.toString();
  }


  private void buildHeader(StringBuilder sb) {
    sb.append("library COVID19_ED version '0.0.1'").append("\n\n");
    sb.append("using FHIR").append("\n\n");

    CPGUtil.getCodeSystems().forEach(cs ->
        sb.append("codesystem \"").append(cs).append("\": '").append(CPGUtil.getCodeSystem(cs)).append("'").append("\n")
    );
    sb.append("\n");
  }

  private void buildCodeDeclarations(StringBuilder sb, List<SemanticDataElementInfo> infos) {
//    infos.stream()
//        .flatMap(SemanticDataElementInfo::allConcepts)
//        .distinct()
//        .map(this::toCodeDeclaration)
//        .forEach(sb::append);
//    sb.append("\n");

    infos.stream()
        .map(this::toTerm)
        .distinct()
        .map(this::toCodeDeclaration)
        .forEach(sb::append);
    sb.append("\n");

  }

  private Term toTerm(SemanticDataElementInfo semanticDataElementInfo) {
    return Term.newTerm(
        AbstractOntologyDrivenGenerator.getSituationUri(semanticDataElementInfo));
  }

  private void buildConceptDeclarations(StringBuilder sb, List<SemanticDataElementInfo> infos) {
    infos.stream()
        .map(this::toConceptDeclaration)
        .forEach(sb::append);
    sb.append("\n");
  }

  private void buildRetrieves(StringBuilder sb, List<SemanticDataElementInfo> infos) {
    infos.forEach(
        info -> info.fhirResources.stream()
            .forEach(res ->  {
              sb.append(toRetrieve(info, res.trim())).append("\n");
              sb.append(toLifts(info, res.trim())).append("\n");
            }));
  }


  private String toCodeDeclaration(Term trm) {
    return "code "
        + "\""
        + CPGUtil.makeTechnicalLabel(trm)
        + "\""
        + " : "
        + "'"
        + trm.getTag()
        + "'"
        + " from "
        + CPGUtil.lookupCodeSystem(trm.getNamespaceUri().toString())
        + "\n";
  }

  private String toConceptDeclaration(SemanticDataElementInfo trm) {
    return "concept "
        + "\"" + trm.dataElement + "\""
        + " : "
        + "{ "
//        + trm.allConcepts().map(t -> "\"" + CPGUtil.makeTechnicalLabel(t) + "\"").collect(Collectors.joining(","))
        + CPGUtil.makeTechnicalLabel(toTerm(trm))
        + " }"
        + "\n";
  }


  private String toLifts(SemanticDataElementInfo info, String resourceType) {
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
          def.append("\"" + baseExpr + "\".valueQuantity " + "\n");
          break;
        default:
          throw new UnsupportedOperationException();
      }
      def.append("\n");
    });

    return def.toString();
  }

  private List<Interrogatives> getAdmissibleInterrogatives(String resourceType) {
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

  private String toRetrieve(SemanticDataElementInfo info, String resourceType) {
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
    if (info.temporal != null) {
      def += "\n\t\t"
          + "where X."
          + getDefaultTimeFilter(FHIRAllTypes.fromCode(resourceType))
          + " between Now() and Now() - "
          + info.temporal.getValue().intValue()
          + " "
          + info.temporal.getUnit();
    }
    def += "\n\t\t\t"
        + "sort by X."
        + getDefaultTimeFilter(FHIRAllTypes.fromCode(resourceType))
        + " desc";
    def += "\n";
    return def;
  }

  public static String getDefaultRetrieveName(String label, String resourceType) {
    return label + " - " + resourceType;
  }

  public static String getDefaultLiftName(String baseExpr, String label) {
    return baseExpr + " - " + label;
  }


}
