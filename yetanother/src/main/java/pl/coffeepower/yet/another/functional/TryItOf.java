package pl.coffeepower.yet.another.functional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

import io.vavr.control.Try;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import lombok.NonNull;
import lombok.extern.java.Log;

@Log
final class TryItOf {

  private static void logException(Throwable throwable) {
    log.severe(throwable.getMessage());
  }

  static Set<File> getHiddenFiles(@NonNull Collection<File> files) {
    return files.stream()
        .filter(File::isHidden)
        .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
  }

  static Set<Path> getHiddenPaths(@NonNull Collection<Path> paths) {
    return paths.stream()
        .filter(path -> {
          try {
            return Files.isHidden(path);
          } catch (IOException e) {
            logException(e);
          }
          return false;
        })
        .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
  }

  static io.vavr.collection.Set<Path> getHiddenPaths(@NonNull io.vavr.collection.Seq<Path> paths) {
    return paths
        .filter(path ->
            Try.of(() -> Files.isHidden(path))
                .onFailure(TryItOf::logException)
                .getOrElse(false))
        .toSet();
  }
}
