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
package cpg.api4kp.common.cql;

import cpg.covid19.ed.cql.CQLConceptsGenerator;
import cpg.covid19.ed.cql.CQLPreMappedConceptsGenerator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import javax.xml.bind.JAXB;
import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.LibrarySourceProvider;
import org.cqframework.cql.cql2elm.ModelInfoLoader;
import org.cqframework.cql.cql2elm.ModelManager;
import org.fhir.ucum.UcumEssenceService;
import org.fhir.ucum.UcumService;
import org.hl7.elm.r1.VersionedIdentifier;
import org.hl7.elm_modelinfo.r1.ModelInfo;
import org.omg.spec.api4kp._20200801.services.KnowledgeCarrier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CQL2ELMHelper {

  private static final Logger logger = LoggerFactory.getLogger(CQL2ELMHelper.class);

  private LibraryManager libraryManager;
  private ModelManager modelManager;
  private UcumService ucumService;

  public static final String BASE_SYSTEM_PATH = "/org/opencds/cqf/tooling/modelinfo/";

  public CQL2ELMHelper() {
    try {
      ModelInfoLoader.registerModelInfoProvider(
          new VersionedIdentifier().withId("FHIR").withVersion("4.0.1"),
          () -> JAXB.unmarshal(
              CQL2ELMHelper.class.getResourceAsStream(BASE_SYSTEM_PATH + "FHIR-modelinfo-4.0.1.xml"),
              ModelInfo.class)
      );
      modelManager = new ModelManager();
      libraryManager = new LibraryManager(modelManager);
      libraryManager.getLibrarySourceLoader()
          .registerProvider(new CQLLibraryLoader());
      ucumService = new UcumEssenceService(
          UcumEssenceService.class.getResourceAsStream("/ucum-essence.xml"));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  public CqlTranslator tryTranslate(KnowledgeCarrier input) throws IOException {
    return CqlTranslator
        .fromStream(new ByteArrayInputStream(input.asBinary()
                .orElseThrow(IllegalStateException::new)),
            modelManager,
            libraryManager,
            ucumService);
  }

  public static class CQLLibraryLoader implements LibrarySourceProvider {

    public static final String BASE_MODEL_PATH = "/opencpg/covid19/ed/cql/";

    @Override
    public InputStream getLibrarySource(VersionedIdentifier vid) {
      return resolveAsSytem(vid)
          .or(() -> resolveAsModel(vid))
          .orElseThrow(() -> err(vid));
    }

    private IllegalStateException err(VersionedIdentifier vid) {
      return new IllegalStateException(
          "Unable to resolve CQL library " + vid.getId() + " " + vid.getVersion());
    }

    private Optional<InputStream> resolveAsSytem(VersionedIdentifier vid) {
      String systemPath = String.format( BASE_SYSTEM_PATH + "%s-%s.cql", alias(vid.getId()), vid.getVersion());
      return Optional
          .ofNullable(CQLLibraryLoader.class.getResourceAsStream(systemPath));
    }

    private Optional<InputStream> resolveAsModel(VersionedIdentifier vid) {
      String modelPath = String.format( BASE_MODEL_PATH + "%s-%s.cql", alias(vid.getId()), vid.getVersion());
      return Optional
          .ofNullable(CQLLibraryLoader.class.getResourceAsStream(modelPath));
    }

    private String alias(String vid) {
      if (CQLConceptsGenerator.LIBRARY_NAME.equals(vid)) {
        return CQLConceptsGenerator.NAME;
      }
      if (CQLPreMappedConceptsGenerator.LIBRARY_NAME.equals(vid)) {
        return CQLPreMappedConceptsGenerator.NAME;
      }
      return vid;
    }
  }

}


