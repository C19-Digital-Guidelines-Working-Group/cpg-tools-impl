package cpg.covid19.ed.owl;

import cpg.util.fhir.CPGUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes;
import org.omg.spec.api4kp._20200801.id.Term;

public class CQLRetrievesGenerator extends AbstractOntologyDrivenGenerator {

  @Override
  public void run(Path srcPath, Path tgtPath) {
    List<SemanticDataElementInfo> infos = readDataElements(srcPath);

    String cqlRetrieves = toCQL(infos);
    System.out.println(cqlRetrieves);

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
    infos.stream()
        .flatMap(SemanticDataElementInfo::allConcepts)
        .distinct()
        .map(this::toCodeDeclaration)
        .forEach(sb::append);
    sb.append("\n");
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
            .forEach(res -> sb.append(toRetrieve(info, res.trim())).append("\n")));
  }


  private String toCodeDeclaration(Term trm) {
    return "code "
        + "\""
        + CPGUtil.lookupTermLabel(trm)
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
        + trm.allConcepts().map(t -> "\"" + CPGUtil.lookupTermLabel(t) + "\"").collect(Collectors.joining(","))
        + " }"
        + "\n";
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
        + info.label() + " - " + resourceType
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
          + info.temporal.getDisplay();
    }
    def += "\n\t\t\t"
        + "sort by X."
        + getDefaultTimeFilter(FHIRAllTypes.fromCode(resourceType))
        + " desc";
    def += "\n";
    return def;
  }



}
