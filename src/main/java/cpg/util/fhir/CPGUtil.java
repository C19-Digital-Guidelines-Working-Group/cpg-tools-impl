package cpg.util.fhir;

import edu.mayo.kmdp.util.NameUtils;
import edu.mayo.kmdp.util.NameUtils.IdentifierType;
import edu.mayo.kmdp.util.Util;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.omg.spec.api4kp._20200801.id.Term;

public class CPGUtil {

  static Map<String,String> codeSystemRegistry = new HashMap<>();

  public enum KnownCodeSystems {
    SNOMED("SNOMED"),
    ICD10CM("ICD-10-CM"),
    ICD10PCS("ICD-10-PCS"),
    LOINC("LOINC"),
    CPT("CPT"),
    HCPCS("HCPCS"),
    RxNORM("RxNorm");

    KnownCodeSystems(String csCD) {
      this.code = csCD;
    }
    public final String code;
  }
  
  static {
    codeSystemRegistry.put("SNOMED","http://snomed.info/sct");
    codeSystemRegistry.put("ICD-10-CM","http://hl7.org/fhir/sid/icd-10-cm");
    codeSystemRegistry.put("ICD-10-PCS","http://hl7.org/fhir/sid/icd-10-pcs");
    codeSystemRegistry.put("LOINC","http://loinc.org");
    codeSystemRegistry.put("CPT","http://www.ama-assn.org/go/cpt");
    codeSystemRegistry.put("HCPCS","urn:oid:2.16.840.1.113883.6.14");
    codeSystemRegistry.put("RxNorm","http://www.nlm.nih.gov/research/umls/rxnorm");
    codeSystemRegistry.put("C19-ED-CPG","https://opencpg.org/ontology/covid19/ed/");
  }

  public static Collection<String> getCodeSystems() {
    return codeSystemRegistry.keySet();
  }

  public static String getCodeSystem(String code) {
    return codeSystemRegistry.get(code);
  }

  public static String lookupCodeSystem(URI codeSystemUri) {
    return lookupCodeSystem(codeSystemUri.toString());
  }

  public static String lookupCodeSystem(String codeSystemUri) {
    Optional<String> code = codeSystemRegistry.entrySet().stream()
        .filter(entry -> codeSystemUri.equals(entry.getValue()))
        .map(Entry::getKey)
        .findFirst();
    if (code.isEmpty()) {
      System.err.println("Unable to lookup " + codeSystemUri);
    }
    return code.orElseThrow();
  }

  public static String makeTechnicalLabel(Term trm) {
    if (Util.isNotEmpty(trm.getLabel())) {
      return trm.getLabel();
    }
    if (Util.isUUID(trm.getTag())) {
      return sanitizeCQLIdentifier(lookupCodeSystem(trm.getNamespaceUri()))
          + "_" + trm.getTag().replaceAll("-","_");
    } else {
      return sanitizeCQLIdentifier(lookupCodeSystem(trm.getNamespaceUri()))
          + "_"
          + sanitizeCQLIdentifier(trm.getTag());
    }
  }

  public static String sanitizeCQLIdentifier(String codeSystem) {
    return NameUtils.nameToIdentifier(codeSystem, IdentifierType.CONSTANT);
  }

}
