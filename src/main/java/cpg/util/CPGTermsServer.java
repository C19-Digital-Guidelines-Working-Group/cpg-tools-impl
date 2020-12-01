/**
 * Copyright © 2018 Mayo Clinic (RSTKNOWLEDGEMGMT@mayo.edu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cpg.util;

import static org.omg.spec.api4kp._20200801.AbstractCarrier.codedRep;
import static org.omg.spec.api4kp._20200801.AbstractCarrier.rep;
import static org.omg.spec.api4kp._20200801.id.Term.newTerm;
import static org.omg.spec.api4kp._20200801.taxonomy.krformat.SerializationFormatSeries.XML_1_1;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.OWL_2;
import static org.omg.spec.api4kp._20200801.taxonomy.parsinglevel.ParsingLevelSeries.Abstract_Knowledge_Expression;
import static org.omg.spec.api4kp._20200801.terms.model.ConceptDescriptor.toConceptDescriptor;

import cpg.covid19.ed.Main;
import edu.mayo.kmdp.language.parsers.owl2.JenaOwlParser;
import edu.mayo.ontology.taxonomies.clinicalinterrogatives._20201101.ClinicalInterrogative;
import edu.mayo.ontology.taxonomies.kao.decisiontype.DecisionType;
import edu.mayo.ontology.taxonomies.kao.decisiontype.DecisionTypeSeries;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.vocabulary.RDFS;
import org.omg.spec.api4kp._20200801.AbstractCarrier;
import org.omg.spec.api4kp._20200801.AbstractCarrier.Encodings;
import org.omg.spec.api4kp._20200801.Answer;
import org.omg.spec.api4kp._20200801.api.terminology.v4.server.TermsApiInternal;
import org.omg.spec.api4kp._20200801.id.Term;
import org.omg.spec.api4kp._20200801.terms.model.ConceptDescriptor;
import org.springframework.context.annotation.Primary;

@Primary
public class CPGTermsServer implements TermsApiInternal {

  Map<String,ConceptDescriptor> index = new HashMap<>();

  @PostConstruct
  void init() {
    try {
      byte[] bytes = Files.readAllBytes(Main.covidOWLPath);
      OntModel om = new JenaOwlParser()
          .applyLift(AbstractCarrier.of(bytes)
              .withRepresentation(rep(OWL_2,XML_1_1, Charset.defaultCharset(), Encodings.DEFAULT)),
              Abstract_Knowledge_Expression,
              codedRep(OWL_2),
              null)
          .flatOpt(kc -> kc.as(OntModel.class))
          .orElseThrow(IllegalStateException::new);

      om.listIndividuals()
          .forEachRemaining(ind -> {
            String label = om.getProperty(ind,RDFS.label).getObject().toString();
            Term trm = newTerm(URI.create(ind.getURI()));
            ConceptDescriptor cd = toConceptDescriptor(trm);
            cd.withName(label);
            index.put(cd.getTag(),cd);
          });

      Arrays.stream(ClinicalInterrogative.values())
          .forEach(ci -> index.put(ci.getUuid().toString(), toConceptDescriptor(ci)));
      Arrays.stream(DecisionTypeSeries.values())
          .forEach(dt -> index.put(dt.getUuid().toString(), toConceptDescriptor(dt)));

    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }


  @Override
  public Answer<ConceptDescriptor> lookupTerm(String conceptId) {
    ConceptDescriptor cd = index.get(conceptId);
    if (cd == null) {
      return Answer.notFound();
    }
    return Answer.of(cd);
  }
}
