package cpg.covid19.ed.cql;

import edu.mayo.kmdp.util.Util;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface CQLGenerator<T> {

  default void run(Path srcPath, Path tgtPath) {
    T sources = readSources(srcPath);

    String cql = toCQL(sources);

    if (Util.isNotEmpty(cql)) {
      save(sources, cql, tgtPath);
    }

  }

  String toCQL(T sources);

  T readSources(Path srcPath);

  String getFileName(T source);

  String getLibraryName(T source);

  String getVersion(T source);

  default void save(T source, String cql, Path tgtPath) {
    Path file = tgtPath.resolve(getFileName(source));
    try {
      if (! Files.exists(file.getParent())) {
        Files.createDirectories(file.getParent());
      }
      Files.write(file,cql.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
