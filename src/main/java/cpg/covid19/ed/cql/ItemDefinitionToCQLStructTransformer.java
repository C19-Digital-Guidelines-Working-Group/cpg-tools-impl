package cpg.covid19.ed.cql;

import static cpg.covid19.ed.cql.CQLRetrieveGenerator.getDefaultLiftName;
import static cpg.covid19.ed.cql.CQLRetrieveGenerator.getDefaultRetrieveName;
import static cpg.util.fhir.IOUtil.readDMNModels;

import com.google.common.io.Files;
import edu.mayo.kmdp.util.NameUtils;
import edu.mayo.kmdp.util.StreamUtil;
import edu.mayo.ontology.taxonomies.clinicalinterrogatives.ClinicalInterrogative;
import edu.mayo.ontology.taxonomies.clinicalinterrogatives.ClinicalInterrogativeSeries;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.bind.JAXBElement;
import org.omg.spec.api4kp._20200801.id.ResourceIdentifier;
import org.omg.spec.api4kp._20200801.surrogate.Annotation;
import org.omg.spec.dmn._20180521.model.TDMNElement.ExtensionElements;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.omg.spec.dmn._20180521.model.TInformationItem;
import org.omg.spec.dmn._20180521.model.TInputData;
import org.omg.spec.dmn._20180521.model.TItemDefinition;

public class ItemDefinitionToCQLStructTransformer extends AbstractCQLGenerator {

  public static final String NAME = "situational_data";
  public static final String VERSION = "0.0.1";
  public static final String FILE_NAME = NAME + "-" + VERSION + ".cql";
  public static final String LIBRARY_NAME = "COVID19_ED_CPG_Situational_Data";

  TDefinitions definitions;

  public ItemDefinitionToCQLStructTransformer(String definitionsSource, Path dmnFolder) {
    definitions = readDMNModels(dmnFolder.resolve(definitionsSource)).get(0)
        .as(TDefinitions.class).orElseThrow();
  }

  public void run(Path dataElements, Path cqlFolder) {
    List<SemanticDataElementInfo> infos = readDataElements(dataElements);

    String cqlRetrieves = toCQL(infos);

    save(cqlRetrieves, cqlFolder);
  }

  @Override
  protected String toCQL(List<SemanticDataElementInfo> infos) {
    StringBuilder sb = new StringBuilder();

    buildHeader(sb);

    definitions.getDrgElement().stream()
        .map(JAXBElement::getValue)
        .flatMap(StreamUtil.filterAs(TInputData.class))
        .forEach(in -> sb.append(buildTuple(in, definitions, infos)));

    return sb.toString();
  }


  protected void buildHeader(StringBuilder sb) {
    sb.append("library ").append(getLibraryName()).append(" version '0.0.1'")
        .append("\n\n");

    sb.append("using FHIR version '4.0.1'")
        .append("\n\n");

    sb.append("include FHIRHelpers version '4.0.1'")
        .append("\n\n");

    sb.append("include " + CQLRetrieveGenerator.LIBRARY_NAME +
        " version '" + CQLRetrieveGenerator.VERSION + "'")
        .append("\n\n");
  }

  private String buildTuple(TInputData in, TDefinitions definitions,
      List<SemanticDataElementInfo> infos) {
    TInformationItem var = in.getVariable();
    TItemDefinition type = resolveType(var.getTypeRef(), definitions);

    StringBuilder declare = new StringBuilder();
    declare.append("declare \"").append(var.getName()).append("\": \n");
    declare.append("\t").append("Tuple {\n\t\t");

    declare.append(
        type.getItemComponent().stream()
            .map(comp -> getSlot(comp, definitions, infos))
            .collect(Collectors.joining(",\n\t\t")));

    declare.append("\n\t}\n");
    return declare.toString();
  }

  private String getSlot(TItemDefinition comp, TDefinitions definitions,
      List<SemanticDataElementInfo> infos) {
    return comp.getName()
        + ": "
        + resolveExpression(comp.getTypeRef(), definitions, infos);
  }

  private String resolveExpression(String slotType, TDefinitions definitions,
      List<SemanticDataElementInfo> infos) {
    TItemDefinition slotDef = resolveType(slotType, definitions);

    Optional<SemanticDataElementInfo> infoOpt = mapToRetrieve(slotDef, infos);
    Optional<ClinicalInterrogative> interrOpt = mapToInterrogative(slotDef);

    if (infoOpt.isEmpty() || interrOpt.isEmpty()) {
      return "null";
    }

    SemanticDataElementInfo info = infoOpt.get();
    List<String> elements = info.fhirResources.stream()
        .map(res -> getDefaultRetrieveName(info.label(),res))
        .map(retrieve -> getDefaultLiftName(retrieve,interrOpt.get().getLabel()))
        .map(expr -> "\"" + expr + "\"")
        .collect(Collectors.toList());

    switch (elements.size()) {
      case 0:
        return "null";
      case 1:
        return elements.get(0);
      default:
        return combine(elements, interrOpt.get());
    }
  }

  private String combine(List<String> elements, ClinicalInterrogative clinicalInterrogative) {
    switch (ClinicalInterrogativeSeries.asEnum(clinicalInterrogative)) {
      case Is:
          return String.join(" or ", elements);
      case Last:
      case Kind_Of:
      case Value_Of:
      default:
        return "Coalesce( " + String.join(", ", elements) + " )";
    }
  }

  private Optional<ClinicalInterrogative> mapToInterrogative(TItemDefinition slotDef) {
    return getAnnotations(slotDef)
        .map(Annotation::getRef)
        .map(ResourceIdentifier::getUuid)
        .map(ClinicalInterrogativeSeries::resolveUUID)
        .flatMap(StreamUtil::trimStream)
        .findFirst();
  }

  private Stream<Annotation> getAnnotations(TItemDefinition slotDef) {
    return Optional.ofNullable(slotDef.getExtensionElements())
        .map(ExtensionElements::getAny)
        .orElse(Collections.emptyList())
        .stream()
        .flatMap(StreamUtil.filterAs(Annotation.class));
  }

  private Optional<SemanticDataElementInfo> mapToRetrieve(
      TItemDefinition slotDef,
      List<SemanticDataElementInfo> infos) {
    return getAnnotations(slotDef)
        .map(Annotation::getRef)
        .map(ResourceIdentifier::getUuid)
        .map(uuid -> mapToInfo(uuid, infos))
        .flatMap(StreamUtil::trimStream)
        .findFirst();
  }

  private Optional<SemanticDataElementInfo> mapToInfo(UUID uuid,
      List<SemanticDataElementInfo> infos) {
    return infos.stream()
        .filter(info -> NameUtils.getTrailingPart(getSituationUri(info).toString())
            .equals(uuid.toString()))
        .findFirst();
  }

  private TItemDefinition resolveType(String typeRef, TDefinitions definitions) {
    return definitions.getItemDefinition().stream()
        .filter(def -> def.getName().equals(typeRef))
        .findFirst()
//        .orElseThrow(() -> new IllegalStateException("Unable to resolve type " + typeRef));
    .orElse(new TItemDefinition());
  }

  @Override
  protected String getFileName() {
    return FILE_NAME;
  }

  @Override
  protected String getLibraryName() {
    return LIBRARY_NAME;
  }
}
