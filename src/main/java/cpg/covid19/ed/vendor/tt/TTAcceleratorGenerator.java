/**
 * Copyright © 2018 Mayo Clinic (RSTKNOWLEDGEMGMT@mayo.edu)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package cpg.covid19.ed.vendor.tt;

import static org.omg.spec.api4kp._20200801.id.Term.newTerm;

import cpg.covid19.ed.AbstractOntologyDrivenGenerator;
import cpg.covid19.ed.vendor.tt.TTAccelerator.TTAcceleratorEntry;
import cpg.util.fhir.IOUtil;
import edu.mayo.kmdp.util.URIUtil;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.omg.spec.api4kp._20200801.id.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TTAcceleratorGenerator extends AbstractOntologyDrivenGenerator {

  private static final Logger logger = LoggerFactory.getLogger(TTAcceleratorGenerator.class);

  public void run(Path srcPath, Path tgtPath) {
    List<SemanticDataElementInfo> infos = readDataElements(srcPath);

    TTAccelerator accel = buildAccelerator(
        URI.create(C19ED_TAXONOMY_SCHEME),
        "Covid-19 ED CPG",
        infos.stream());

    IOUtil.saveToJSON(accel, tgtPath);
  }

  public TTAccelerator buildAccelerator(URI uri, String label,
      Stream<SemanticDataElementInfo> terms) {
    URI nuri = URIUtil.normalizeURI(uri);
    String suri = nuri.toString();
    if (suri.endsWith("/")) {
      suri = suri.substring(0, suri.length() - 1);
    }
    String ver = suri.substring(suri.lastIndexOf('/') + 1);
    TTAccelerator accel = new TTAccelerator(
        nuri,
        label + " - " + ver,
        "");
    terms.map(sdi -> {
      Term pc = newTerm(getSituationUri(sdi));
      return new TTAcceleratorEntry(
          pc.getResourceId(),
          sdi.label(),
          "");
    }).distinct()
        .forEach(accel::addChild);
    return accel;
  }

}
