package pl.coffeepower.yet.another.functional;

import io.vavr.CheckedFunction1;
import io.vavr.control.Try;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.java.Log;

@Log
public final class TryItOf {

  public static Set<Path> getHiddenPaths(Path rootPathToCheck) {
    try {
      try (Stream<Path> walk = Files.walk(rootPathToCheck)) {
        return walk
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
    } catch (IOException e) {
      logException(e);
    }
    return Collections.emptySet();
  }

  public static Set<Path> getHiddenPathsFunctionalWay(Path rootPathToCheck) {
    return Try
        .withResources(() -> Files.walk(rootPathToCheck))
        .of(CheckedFunction1.identity())
        .onFailure(TryItOf::logException)
        .getOrElse(Stream.empty())
        .filter(path ->
            Try
                .of(() -> Files.isHidden(path))
                .onFailure(TryItOf::logException)
                .getOrElse(false))
        .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
  }

  private static void logException(Throwable throwable) {
    log.severe(throwable.getMessage());
  }
}
