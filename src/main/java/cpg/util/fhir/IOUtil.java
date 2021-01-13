package cpg.util.fhir;

import static org.omg.spec.api4kp._20200801.AbstractCarrier.codedRep;
import static org.omg.spec.api4kp._20200801.AbstractCarrier.rep;
import static org.omg.spec.api4kp._20200801.taxonomy.krformat.SerializationFormatSeries.XML_1_1;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.CMMN_1_1;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.DMN_1_2;
import static org.omg.spec.api4kp._20200801.taxonomy.parsinglevel.ParsingLevelSeries.Abstract_Knowledge_Expression;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import edu.mayo.kmdp.language.parsers.cmmn.v1_1.CMMN11Parser;
import edu.mayo.kmdp.language.parsers.dmn.v1_2.DMN12Parser;
import edu.mayo.kmdp.util.JSonUtil;
import edu.mayo.kmdp.util.StreamUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hl7.fhir.r4.model.Resource;
import org.omg.spec.api4kp._20200801.AbstractCarrier;
import org.omg.spec.api4kp._20200801.AbstractCarrier.Encodings;
import org.omg.spec.api4kp._20200801.Answer;
import org.omg.spec.api4kp._20200801.services.KnowledgeCarrier;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class IOUtil {

  static IParser jsonParser = FhirContext.forR4()
      .newJsonParser()
      .setPrettyPrint(true);

  public static void save(Resource resource, Path file) {
    try {
      if (!Files.exists(file.getParent())) {
        Files.createDirectories(file.getParent());
      }
      String json = jsonParser.encodeResourceToString(resource);
      Files.write(file, json.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void saveOntology(OWLOntology onto, Path tgtPath)
      throws IOException, OWLOntologyStorageException {
    if (!Files.exists(tgtPath.getParent())) {
      Files.createDirectories(tgtPath.getParent());
    }
    try (OutputStream fos = Files.newOutputStream(tgtPath)) {
      onto.saveOntology(new RDFXMLDocumentFormat(), fos);
    }
  }

  public static void saveToJSON(Object object, Path tgtPath) {
    JSonUtil.writeJson(object)
        .ifPresent(baos -> {
          try {
            Files.write(tgtPath, baos.toByteArray());
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  public static void saveText(String txt, Path tgtPath) {
    try {
      Files.write(tgtPath, txt.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static List<Path> listModels(Path modelFolder) {
    try {
      try (Stream<Path> pathStream = Files.walk(modelFolder)) {
        return pathStream
            .filter(f -> isDMN(f) || isCMMN(f))
            .collect(Collectors.toList());
      }
    } catch (IOException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }


  public static boolean isDMN(Path f) {
    return f.getFileName().toString().endsWith("dmn.xml");
  }
  public static boolean isCMMN(Path f) {
    return f.getFileName().toString().endsWith("cmmn.xml");
  }

  public static List<KnowledgeCarrier> readDMNModels(Path dmnFolder) {
    return listModels(dmnFolder).stream()
        .filter(IOUtil::isDMN)
        .map(IOUtil::readDMNModel)
        .flatMap(StreamUtil::trimStream)
        .collect(Collectors.toList());
  }

  public static Optional<KnowledgeCarrier> readDMNModel(Path path) {
    DMN12Parser parser = new DMN12Parser();
    return loadXMLFile(path)
        .map(is -> AbstractCarrier.of(is)
            .withRepresentation(rep(DMN_1_2,
                XML_1_1, Charset.defaultCharset(), Encodings.DEFAULT)))
        .map(kc -> parser.applyLift(kc, Abstract_Knowledge_Expression, codedRep(DMN_1_2), null))
        .map(Answer::get);
  }


  public static List<KnowledgeCarrier> readCMMNModels(Path cmmnFolder) {
    return listModels(cmmnFolder).stream()
        .filter(IOUtil::isCMMN)
        .map(IOUtil::readCMMNModel)
        .flatMap(StreamUtil::trimStream)
        .collect(Collectors.toList());
  }

  private static Optional<KnowledgeCarrier> readCMMNModel(Path path) {
    CMMN11Parser parser = new CMMN11Parser();
    return loadXMLFile(path)
        .map(is -> AbstractCarrier.of(is)
            .withRepresentation(rep(CMMN_1_1,
                XML_1_1, Charset.defaultCharset(), Encodings.DEFAULT)))
        .map(kc -> parser.applyLift(kc, Abstract_Knowledge_Expression, codedRep(CMMN_1_1), null))
        .map(Answer::get);
  }


  private static Optional<InputStream> loadXMLFile(Path file) {
    try {
      return Optional.of(Files.newInputStream(file));
    } catch (IOException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

}
