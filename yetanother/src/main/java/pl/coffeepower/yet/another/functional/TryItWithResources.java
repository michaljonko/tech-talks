package pl.coffeepower.yet.another.functional;

import io.vavr.control.Try;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.function.Supplier;
import lombok.NonNull;
import lombok.extern.java.Log;

@Log
final class TryItWithResources {

  private static void logException(@NonNull Throwable throwable) {
    log.severe(throwable.getMessage());
  }

  private static void logInfo(@NonNull String message) {
    log.info(message);
  }


  static long counterOldWay(@NonNull Reader reader, @NonNull String word) {
    try (BufferedReader bufferedReader = new BufferedReader(reader)) {
      long counter = bufferedReader.lines()
          .limit(10)
          .map(line -> Arrays.stream(line.split("\\W+")).filter(s -> s.equals(word)).count())
          .reduce(Long::sum)
          .orElse(0L);
      if (counter > 10) {
        logInfo("More then 10 word occurrences");
      }
      return counter;
    } catch (IOException e) {
      logException(e);
    }
    return 0L;
  }

  static long counter(@NonNull Supplier<Reader> readerSupplier, @NonNull String word) {
    return Try.withResources(() -> new BufferedReader(readerSupplier.get()))
        .of(reader -> io.vavr.collection.Stream.ofAll(reader.lines())
            .take(10)
            .map(line -> io.vavr.collection.Stream.of(line.split("\\W+")).count(s -> s.equals(word)))
            .reduce(Integer::sum))
        .onFailure(TryItWithResources::logException)
        .onSuccess(counter -> {
          if (counter > 10) {
            logInfo("");
          }
        })
        .getOrElse(0);
  }
}
