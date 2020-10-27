package cpg.covid19.ed;

import cpg.covid19.ed.owl.CPGDataRequirementGenerator;
import cpg.covid19.ed.owl.CQLRetrievesGenerator;
import cpg.covid19.ed.owl.OntologyGenerator;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  static final Logger logger = LoggerFactory.getLogger(Main.class);

  static Path sourcePath =
      Path.of("src","main", "resources");
  static Path tgtPath =
      Path.of("target", "generated-resources");
  static Path distPath =
      Path.of("dist");

  static Path scope =
      Path.of("covid19", "ed");

  static Path dataElementSheet =
      sourcePath.resolve(scope.resolve(Path.of("owl", "Data Elements.xlsx")));

  static Path covidOWLPath =
      tgtPath.resolve(scope.resolve(Path.of("owl", "covid19.owl")));
  static Path fhirPath =
      tgtPath.resolve(scope.resolve(Path.of("fhir")));
  static Path cqlPath =
      tgtPath.resolve(scope.resolve(Path.of("cql")));


  public static void main(String... args) throws IOException {
    clearAll(tgtPath);
    clearAll(distPath);

    new CQLRetrievesGenerator().run(dataElementSheet, cqlPath);

    new CPGDataRequirementGenerator().run(dataElementSheet, fhirPath);

    new OntologyGenerator().run(dataElementSheet, covidOWLPath);

    publish();
  }

  private static void clearAll(Path path) {
    try {
      Files.walkFileTree(
          path,
          new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                throws IOException {
              if (Files.isRegularFile(file)) {
                Files.delete(file);
              }
              return FileVisitResult.CONTINUE;
            }
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void publish() throws IOException {
    Files.walkFileTree(
        sourcePath, new PublicationFileVisitor(sourcePath)
    );
    Files.walkFileTree(
        tgtPath, new PublicationFileVisitor(tgtPath)
    );
  }


  public static class PublicationFileVisitor extends SimpleFileVisitor<Path> {

    private Path root;

    public PublicationFileVisitor(Path tgtPath) {
      this.root = tgtPath;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes)
              throws IOException {
      Path out = distPath.resolve(root.relativize(path));

      if (!Files.exists(out.getParent())) {
        Files.createDirectories(out.getParent());
      }

      if (! isIgnored(path)) {
        logger.trace("Copying {} into {}", path.toAbsolutePath(), out.toAbsolutePath());
        Files.copy(path,out, StandardCopyOption.REPLACE_EXISTING);
      } else {
        logger.trace("Skipping {}", path);
      }

      return FileVisitResult.CONTINUE;
    }

    private boolean isIgnored(Path path) throws IOException {
      return
          Files.isHidden(path)
          || "catalog-v001.xml".equals(path.getFileName().toString());
    }
  }

}
