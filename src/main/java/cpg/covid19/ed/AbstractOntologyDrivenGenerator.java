package cpg.covid19.ed;

import static org.omg.spec.api4kp._20200801.id.Term.newTerm;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import cpg.util.csv.ExcelToCSVExtractor;
import cpg.util.csv.ExcelToCSVExtractorConfig;
import edu.mayo.kmdp.terms.TermsHelper;
import edu.mayo.kmdp.util.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hl7.fhir.r4.model.Duration;
import org.hl7.fhir.r4.model.Enumerations.FHIRAllTypes;
import org.omg.spec.api4kp._20200801.id.Term;

public abstract class AbstractOntologyDrivenGenerator {

  public static final String C19ED_NS = "https://opencpg.org/ontology/covid19/ed/";
  public static final String C19ED_VERSION = "https://opencpg.org/ontology/covid19/20200111/ed/";
  public static final String C19ED_TAXONOMY_NS = "https://opencpg.org/taxonomy/covid19/ed/";
  public static final String C19ED_TAXONOMY_SCHEME = "https://opencpg.org/taxonomy/covid19/ed/288293fc-1079-46a1-b644-95ddf1264414";
  public static final String C19ED_TAXONOMY_TOP = "https://opencpg.org/taxonomy/covid19/ed/b2ce4e9d-2c3c-409c-9484-0bd6008cbd31";

  public static final String OLEX_CONCEPTOF = "http://www.w3.org/ns/lemon/ontolex#isConceptOf";
  public static final String OLEX_DENOTES = "http://www.w3.org/ns/lemon/ontolex#denotes";


  public enum ConceptKind {
    PROCEDURE, OBSERVABLE, PROBLEM, MEDICATION, DEMOGRAPHICS, LAB;
  }

  public enum Interrogatives {
    IS("Is", " - Present"),
    KIND_OF("KindOf", " - Kind Of"),
    VALUE_OF("ValueOf", " - Value Of");

    public final String code;
    public final String label;

    Interrogatives(String code, String label) {
      this.code = code;
      this.label = label;
    }
  }

  public abstract void run(Path srcPath, Path tgtPath);

  static Map<String,List<SemanticDataElementInfo>> cache = new HashMap<>();

  protected List<SemanticDataElementInfo> readDataElements(Path srcPath) {
    String key = srcPath.toAbsolutePath().toString();
    if (cache.containsKey(key)) {
      return cache.get(key);
    }

    Optional<byte[]> csvData = readAsCSV(srcPath);
    List<SemanticDataElementInfo> elements = csvData.map(csv -> new CsvToBeanBuilder<SemanticDataElementInfo>(
        new InputStreamReader(new ByteArrayInputStream(csv)))
        .withType(SemanticDataElementInfo.class)
        .build()
        .parse()
        .stream()
        .filter(de -> !Util.isEmpty(de.dataElement))
        .collect(Collectors.toList())
    ).orElseThrow();

    cache.put(key, elements);
    return elements;
  }

  private Optional<byte[]> readAsCSV(Path srcPath) {
    try {
      try (InputStream is = Files.newInputStream(srcPath.toAbsolutePath())) {
        ExcelToCSVExtractorConfig cfg = new ExcelToCSVExtractorConfig();
        return new ExcelToCSVExtractor()
            .convertExcelToCSV(is, cfg);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
    return Optional.empty();
  }


  public static URI getSituationUri(SemanticDataElementInfo termInfo) {
    return URI.create(C19ED_NS
        + UUID.nameUUIDFromBytes(termInfo.dataElement.getBytes()));
  }


  public static class SemanticDataElementInfo {

    @CsvBindByName(column = "Data Element")
    public String dataElement;

    // FIXME Upgrade to OpenCSV 5.x - enums are converted out of the box
    @CsvCustomBindByName(column = "Domain", converter = ConceptKindConverter.class )
    public ConceptKind kind;

    @CsvCustomBindByName(converter = SnomedTermConverter.class, column = "Focal Concept")
    public Term focalConcept;

    @CsvBindByName(column = "Situation")
    public String situation;

    @CsvCustomBindByName(column = "Temporal", converter = FHIRDurationConverter.class)
    public Duration temporal;

    @CsvCustomBindByName(converter = SnomedTermConverter.class, column = "Context")
    public Term context;

    @CsvBindAndSplitByName(column = "FHIR", splitOn = "\\|", elementType = String.class)
    public List<String> fhirResources;



    @CsvCustomBindByName(column = "ICD-10-CM", converter = ICD10CMTermConverter.class)
    public List<Term> icd10cm;

    @CsvCustomBindByName(column = "LOINC", converter = LOINCTermConverter.class)
    public List<Term> loinc;

    @CsvCustomBindByName(column = "ICD-10-PCS", converter = ICD10PCSTermConverter.class)
    public List<Term> icd10pcs;

    @CsvCustomBindByName(column = "CPT", converter = CPTTermConverter.class)
    public List<Term> cpt;

    @CsvCustomBindByName(column = "HCPCS", converter = HCPCSTermConverter.class)
    public List<Term> hcpcs;

    @CsvCustomBindByName(column = "RxNORM", converter = RxNORMTermConverter.class)
    public List<Term> rxNorm;

    public Stream<Term> allConcepts() {
      List<Term> allTerms = new ArrayList<>();
      allTerms.addAll(cpt);
      allTerms.addAll(hcpcs);
      allTerms.addAll(icd10cm);
      allTerms.addAll(loinc);
      allTerms.addAll(icd10pcs);
      allTerms.addAll(rxNorm);
      return allTerms.stream();
    }

    public String label() {
      switch (kind) {
        case PROCEDURE:
          return "Prior " + dataElement;
        case DEMOGRAPHICS:
          return dataElement;
        case OBSERVABLE:
          return "Current " + dataElement;
        case MEDICATION:
          return "On " + dataElement;
        case PROBLEM:
          return "Has " + dataElement;
        case LAB:
          return "Most Recent " + dataElement;
        default:
          throw new UnsupportedOperationException();
      }
    }
  }

  public static class SnomedTermConverter extends AbstractBeanField<Term> {
    @Override
    protected Term convert(String value)
        throws CsvDataTypeMismatchException, CsvConstraintViolationException {
      if (Util.isEmpty(value)) {
        return null;
      }
      if (! value.trim().startsWith("SCTID:")) {
        throw new CsvConstraintViolationException("Unable to process " + value + " as a SNOMED term");
      }
      int index = value.indexOf('|');
      int lastIndex = value.lastIndexOf('|');
      String code = value.substring("SCTID:".length(), index).trim();
      String label = value.substring(index + 1, lastIndex > index ? lastIndex : value.length()).trim();
      return TermsHelper.sct(label,code);
    }
  }

  public abstract static class SimpleTermConverter extends AbstractBeanField<List<Term>> {
    @Override
    protected List<Term> convert(String value)
        throws CsvDataTypeMismatchException, CsvConstraintViolationException {
      if (Util.isEmpty(value)) {
        return Collections.emptyList();
      }
      return Arrays.stream(value.trim().split("\\|"))
          .filter(s -> !Util.isEmpty(s))
          .map(this::toTerm)
          .collect(Collectors.toList());
    }

    protected abstract Term toTerm(String code);
  }

  public static class RxNORMTermConverter extends SimpleTermConverter {
    @Override
    protected Term toTerm(String code) {
      return TermsHelper.rxn("", code);
    }
  }

  public static class HCPCSTermConverter extends SimpleTermConverter {
    @Override
    protected Term toTerm(String code) {
      return TermsHelper.hcpcs("", code);
    }
  }

  public static class ICD10CMTermConverter extends SimpleTermConverter {
    @Override
    protected Term toTerm(String code) {
      return TermsHelper.icd10cm("", code);
    }
  }
  public static class LOINCTermConverter extends SimpleTermConverter {
    @Override
    protected Term toTerm(String code) {
      return TermsHelper.lnc("", code);
    }
  }
  public static class ICD10PCSTermConverter extends SimpleTermConverter {
    @Override
    protected Term toTerm(String code) {
      return TermsHelper.icd10pcs("", code);
    }
  }
  public static class CPTTermConverter extends SimpleTermConverter {
    @Override
    protected Term toTerm(String code) {
      return TermsHelper.cpt("", code);
    }
  }


  public static class ConceptKindConverter extends AbstractBeanField<ConceptKind> {
    @Override
    protected Object convert(String value)
        throws CsvDataTypeMismatchException, CsvConstraintViolationException {
      if (Util.isEmpty(value)) {
        return null;
      }
      return ConceptKind.valueOf(value.toUpperCase());
    }
  }

  public static class FHIRDurationConverter extends AbstractBeanField<Duration> {
    static Pattern durationPattern = Pattern.compile("^\\s*(\\d+)\\s*(\\w+)\\s*$");

    @Override
    protected Duration convert(String value)
        throws CsvDataTypeMismatchException, CsvConstraintViolationException {
      if (Util.isEmpty(value)) {
        return null;
      }
      Matcher m = durationPattern.matcher(value);
      if (!m.matches()) {
        throw new CsvDataTypeMismatchException("Expecting a duration, but found " + value);
      }
      int num = Integer.parseInt(m.group(1));
      String unit = m.group(2);
      return (Duration) new Duration()
          .setValue(num)
          .setUnit(toLabel(unit))
          .setCode(unit);
    }

    private String toLabel(String unit) {
      switch (unit) {
        case "ms":
          return "milliseconds";
        case "s":
          return "seconds";
        case "min":
          return "minutes";
        case "h":
          return "hours";
        case "d":
          return "days";
        case "wk":
          return "weeks";
        case "mo":
          return "months";
        case "a":
          return "years";
        default:
          throw new IllegalStateException();
      }
    }

  }

  protected String getDefaultTimeFilter(FHIRAllTypes resource) {
    switch (resource) {
      case MEDICATIONSTATEMENT:
      case DIAGNOSTICREPORT:
      case OBSERVATION:
        return "effective";
      case PROCEDURE:
        return "performed";
      case CONDITION:
        return "onset";
      case DEVICEREQUEST:
      case MEDICATIONREQUEST:
      case SERVICEREQUEST:
        return "occurrence";
      default:
        throw new UnsupportedOperationException("Cannot define a temporal filter on " + resource);
    }
  }
}
