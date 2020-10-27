package cpg.covid19.ed.owl;

import static java.util.stream.Collectors.toList;
import static org.omg.spec.api4kp._20200801.AbstractCarrier.ofHeterogeneousComposite;
import static org.omg.spec.api4kp._20200801.AbstractCarrier.rep;
import static org.omg.spec.api4kp._20200801.taxonomy.krformat.snapshot.SerializationFormat.XML_1_1;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.snapshot.KnowledgeRepresentationLanguage.OWL_2;
import static org.omg.spec.api4kp._20200801.taxonomy.krserialization.snapshot.KnowledgeRepresentationLanguageSerialization.RDF_XML_Syntax;

import edu.mayo.kmdp.ops.tranx.owl2.ComplexOwl2SKOSTransrepresentator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.jena.rdf.model.Model;
import org.omg.spec.api4kp._20200801.AbstractCarrier;
import org.omg.spec.api4kp._20200801.services.KnowledgeCarrier;

public class OntologyCompiler {

  private ComplexOwl2SKOSTransrepresentator tx = new ComplexOwl2SKOSTransrepresentator();

  public static void main(String... args) {
    Path srcPath = Path.of("src","main", "resources", "covid19", "ed", "owl");
    Path tgtPath = Path.of("target", "generated-resources", "resources", "covid19", "ed", "skos");

    new OntologyCompiler().run(srcPath, tgtPath);
  }

  private void run(Path srcPath, Path tgtPath) {
    tx.applyTransrepresent(loadModels(srcPath), null, null)
        .map(ckc -> ckc.components().collect(toList()))
        .forEach(KnowledgeCarrier.class, x -> writeFile(x, tgtPath));
  }

  private KnowledgeCarrier loadModels(Path basePath) {
    return ofHeterogeneousComposite(loadModelsBinary(basePath)
        .map(bytes -> AbstractCarrier.of(bytes, rep(OWL_2, RDF_XML_Syntax, XML_1_1)))
        .collect(Collectors.toUnmodifiableList()));
  }


  Stream<byte[]> loadModelsBinary(Path basePath) {
    try {
      return Files.walk(basePath)
          .filter(f -> f.getFileName().toString().endsWith(".owl"))
          .map(this::read)
          .filter(b -> b.length > 0);
    } catch (IOException e) {
      e.printStackTrace();
      return Stream.empty();
    }
  }

  private byte[] read(Path ontoFile) {
    try {
      return Files.readAllBytes(ontoFile);
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  private void writeFile(KnowledgeCarrier kc, Path tgtPath) {
    if (! Files.exists(tgtPath)) {
      try {
        Files.createDirectories(tgtPath);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    try (FileOutputStream fos = new FileOutputStream(
        tgtPath + File.separator + kc.getLabel() + ".skos.owl")) {
      kc.as(Model.class)
          .ifPresent(m -> m.write(fos));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
