package cpg.covid19.ed.owl;

import static cpg.covid19.ed.AbstractOntologyDrivenGenerator.C19ED_TAXONOMY_NS;
import static cpg.covid19.ed.AbstractOntologyDrivenGenerator.C19ED_TAXONOMY_SCHEME;
import static cpg.covid19.ed.AbstractOntologyDrivenGenerator.C19ED_TAXONOMY_TOP;
import static cpg.util.fhir.IOUtil.save;
import static org.omg.spec.api4kp._20200801.AbstractCarrier.codedRep;
import static org.omg.spec.api4kp._20200801.AbstractCarrier.rep;
import static org.omg.spec.api4kp._20200801.taxonomy.krformat.SerializationFormatSeries.XML_1_1;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.OWL_2;
import static org.omg.spec.api4kp._20200801.taxonomy.krserialization.KnowledgeRepresentationLanguageSerializationSeries.RDF_XML_Syntax;
import static org.omg.spec.api4kp._20200801.taxonomy.parsinglevel.ParsingLevelSeries.Abstract_Knowledge_Expression;

import edu.mayo.kmdp.language.parsers.owl2.JenaOwlParser;
import edu.mayo.kmdp.util.NameUtils;
import edu.mayo.kmdp.util.NameUtils.IdentifierType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.SKOS;
import org.hl7.fhir.r4.model.CodeSystem;
import org.hl7.fhir.r4.model.CodeSystem.ConceptDefinitionComponent;
import org.hl7.fhir.r4.model.ValueSet;
import org.omg.spec.api4kp._20200801.AbstractCarrier;
import org.omg.spec.api4kp._20200801.services.KnowledgeCarrier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConceptSchemeGenerator  {

  Logger logger = LoggerFactory.getLogger(ConceptSchemeGenerator.class);

  JenaOwlParser parser = new JenaOwlParser();

  public void run(Path srcPath, Path tgtPath) {
    logger.info("Generate FHIR ConceptScheme from OWL Ontology...");
    read(srcPath)
    .flatMap(this::toCodeSystem)
    .ifPresent(cs -> {
      save(cs.getContained().get(0), tgtPath.resolve("conceptScheme.valueset.r4.json"));
      save(cs, tgtPath.resolve("conceptScheme.r4.json"));
    });
  }

  private Optional<CodeSystem> toCodeSystem(Model model) {
    ResIterator schemes = model.listResourcesWithProperty(RDF.type, SKOS.ConceptScheme);
    if (! schemes.hasNext()) {
      logger.error("NO CONCEPT SCHEME FOUND");
      return Optional.empty();
    }
    Resource conceptScheme = schemes.nextResource();
    String conceptSchemeLabel = model.getProperty(conceptScheme, RDFS.label)
        .getObject().asLiteral().getString();

    CodeSystem cs = new CodeSystem();
    cs.setName(NameUtils.nameToIdentifier(conceptSchemeLabel, IdentifierType.VARIABLE));
    cs.setTitle(conceptSchemeLabel);
    cs.setUrl(conceptScheme.getURI());
    cs.setCompositional(true);
    cs.addIdentifier()
        .setValue(conceptScheme.getURI());

    ValueSet vs = new ValueSet();
    vs.setId(UUID.randomUUID().toString());
    vs.setName(conceptSchemeLabel + " (Entire ValueSet)");

    cs.setValueSet("#" + vs.getId());
    cs.addContained(vs);

    // initially, index all Concepts
    Map<Resource, CodeSystem.ConceptDefinitionComponent> allConcepts = new HashMap<>();
    model.listResourcesWithProperty(SKOS.inScheme, conceptScheme)
        .filterDrop(r -> r.getURI().equals(C19ED_TAXONOMY_SCHEME) || r.getURI().equals(C19ED_TAXONOMY_TOP))
        .forEachRemaining(c -> allConcepts.put(c, new ConceptDefinitionComponent()));

    //
    allConcepts.keySet().forEach(c -> populateConcept(c, cs, vs, model, allConcepts));

    return Optional.of(cs);
  }

  private void populateConcept(
      Resource concept,
      CodeSystem cs,
      ValueSet vs,
      Model model,
      Map<Resource, ConceptDefinitionComponent> index) {
    String notation = model.getProperty(concept, SKOS.notation).getObject().asLiteral().toString();
    String label = model.getProperty(concept, RDFS.label).getObject().asLiteral().toString();

    ConceptDefinitionComponent cd = index.get(concept);
    cd.setCode(notation);
    cd.setDisplay(label);
    cd.addDesignation()
        .setLanguage("us-en")
        .setValue(label);

    vs.getExpansion()
        .addContains()
        .setCode(notation)
        .setDisplay(label)
        .setSystem(cs.getUrl());

    List<Resource> parents = model.listObjectsOfProperty(concept, SKOS.broader)
        .filterKeep(n -> n instanceof Resource)
        .mapWith(Resource.class::cast)
        .filterDrop(
            r -> r.getURI().equals(C19ED_TAXONOMY_SCHEME) || r.getURI().equals(C19ED_TAXONOMY_TOP))
        .filterKeep(r -> r.getURI().startsWith(C19ED_TAXONOMY_NS))
        .toList();
    if (parents.size() > 1) {
      throw new IllegalStateException();
    } else if (parents.isEmpty()) {
      cs.addConcept(cd);
    } else {
      parents.forEach(parent ->
          index.get(parent).addConcept(cd));
    }
  }

  private Optional<Model> read(Path srcPath) {
    try {
      try (InputStream is = Files.newInputStream(srcPath)) {
        KnowledgeCarrier kc = AbstractCarrier.of(is)
            .withRepresentation(rep(OWL_2, RDF_XML_Syntax, XML_1_1, Charset.defaultCharset()));
        return parser.applyLift(kc, Abstract_Knowledge_Expression, codedRep(OWL_2), null)
            .flatOpt(x -> x.as(Model.class))
            .getOptionalValue();
      }
    } catch (IOException ioe) {
      logger.error(ioe.getMessage(), ioe);
      return Optional.empty();
    }
  }


}
