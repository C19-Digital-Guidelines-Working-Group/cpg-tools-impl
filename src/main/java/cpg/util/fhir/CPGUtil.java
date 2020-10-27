package cpg.util.fhir;

import edu.mayo.kmdp.util.NameUtils;
import edu.mayo.kmdp.util.NameUtils.IdentifierType;
import edu.mayo.kmdp.util.Util;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.omg.spec.api4kp._20200801.id.Term;

public class CPGUtil {

  static Map<String,String> codeSystemRegistry = new HashMap<>();
  
  static {
    codeSystemRegistry.put("SNOMED","http://snomed.info/sct");
    codeSystemRegistry.put("ICD-10-CM","http://hl7.org/fhir/sid/icd-10-cm");
    codeSystemRegistry.put("ICD-10-PCS","http://hl7.org/fhir/sid/icd-10-pcs");
    codeSystemRegistry.put("LOINC","http://loinc.org");
    codeSystemRegistry.put("CPT","http://www.ama-assn.org/go/cpt");
    codeSystemRegistry.put("HCPCS","urn:oid:2.16.840.1.113883.6.14");
    codeSystemRegistry.put("RxNorm","http://www.nlm.nih.gov/research/umls/rxnorm");
  }

  public static Collection<String> getCodeSystems() {
    return codeSystemRegistry.keySet();
  }

  public static String getCodeSystem(String code) {
    return codeSystemRegistry.get(code);
  }

  public static String lookupCodeSystem(String codeSystemUri) {
    Optional<String> code = codeSystemRegistry.entrySet().stream()
        .filter(entry -> codeSystemUri.equals(entry.getValue()))
        .map(Entry::getKey)
        .findFirst();
    if (!code.isPresent()) {
      System.err.println("Unable to lookup " + codeSystemUri);
    }
    return code.orElseThrow();
  }

  public static String lookupTermLabel(Term trm) {
    if (Util.isNotEmpty(trm.getLabel())) {
      return trm.getLabel();
    }
    return lookupCodeSystem(trm.getNamespaceUri().toString())
        + "_"
        + NameUtils.nameToIdentifier(trm.getTag(), IdentifierType.CONSTANT);
  }
}
