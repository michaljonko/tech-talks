package pl.coffeepower.yet.another.functional;

import io.vavr.control.Try;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.extern.java.Log;

@Log
public final class TryItOf {

  private static void logException(Throwable throwable) {
    log.severe(throwable.getMessage());
  }

  public static Set<Path> getHiddenPaths(@NonNull Collection<Path> paths) {
    return paths.stream()
        .filter(path -> {
          try {
            return Files.isHidden(path);
          } catch (IOException e) {
            logException(e);
          }
          return false;
        })
        .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
  }

  public static io.vavr.collection.Set<Path> getHiddenPaths(@NonNull io.vavr.collection.Seq<Path> paths) {
    return paths
        .filter(path ->
            Try.of(() -> Files.isHidden(path))
                .onFailure(TryItOf::logException)
                .getOrElse(false))
        .toSet();
  }
}
