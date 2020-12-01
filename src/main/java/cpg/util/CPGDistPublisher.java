package cpg.util;

import edu.mayo.kmdp.util.DateTimeUtil;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CPGDistPublisher {

  static final Logger logger = LoggerFactory.getLogger(CPGDistPublisher.class);

  public static void clearAll(Path path) {
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

  public static void publish(Path sourcePath, Path tgtPath, Path distPath) throws IOException {
    String dateStamp = DateTimeUtil.serializeDate(new Date(), DateTimeUtil.DEFAULT_DATE_PATTERN);
    Path outputPath = distPath.resolve(dateStamp);

    Files.walkFileTree(
        sourcePath, new PublicationFileVisitor(sourcePath, outputPath)
    );
    Files.walkFileTree(
        tgtPath, new PublicationFileVisitor(tgtPath, outputPath)
    );
  }


  public static class PublicationFileVisitor extends SimpleFileVisitor<Path> {

    private Path root;
    private Path distPath;

    public PublicationFileVisitor(Path tgtPath, Path distPath) {
      this.root = tgtPath;
      this.distPath = distPath;
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
