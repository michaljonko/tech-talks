package pl.coffeepower.yet.another.functional;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.allOf;
import static io.vavr.Predicates.instanceOf;
import static java.util.function.Predicate.isEqual;

import io.vavr.collection.Stream;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;
import lombok.NonNull;
import lombok.extern.java.Log;

@Log
final class TryItWithResources {

  private static void logException(@NonNull Throwable throwable) {
    log.severe(throwable.getMessage());
  }

  private static void logInfo(@NonNull String word) {
    log.warning("Word '" + word + "' occurred more then once.");
  }

  private static String[] splitText(String line) {
    return line.split("\\W+");
  }

  static int wordFrequencyInFirstTenLinesOfReader(@NonNull Reader reader, @NonNull String word) {
    try (BufferedReader bufferedReader = new BufferedReader(reader)) {
      int counter = bufferedReader.lines()
          .limit(10L)
          .map(line -> Arrays.stream(splitText(line)).filter(isEqual(word)).count())
          .reduce(0L, Long::sum)
          .intValue();
      if (counter > 1) {
        logInfo(word);
      }
      return counter;
    } catch (IOException e) {
      logException(e);
    }
    return 0;
  }

  static int wordFrequencyInFirstTenLinesOfReaderWithTry(@NonNull Reader reader, @NonNull String word) {
    return Try
        .withResources(() -> new BufferedReader(reader))
        .of(bufferedReader -> Stream.ofAll(bufferedReader.lines())
            .take(10)
            .map(line -> Stream.of(splitText(line)).count(isEqual(word)))
            .reduce(Integer::sum))
        .onFailure(TryItWithResources::logException)
        .onSuccess(counter -> Option.when(counter > 1, true).forEach(o -> logInfo(word)))
        .getOrElse(0);
  }

  @SuppressWarnings("unchecked")
  static Either<? extends Throwable, Integer> wordFrequencyInFirstTenLinesOfReaderWithTryAndEither(@NonNull Reader reader,
      @NonNull String word) {
    return Try
        .withResources(() -> new BufferedReader(reader))
        .of(bufferedReader -> Stream.ofAll(bufferedReader.lines())
            .take(10)
            .map(line -> Stream.of(splitText(line)).count(isEqual(word)))
            .reduce(Integer::sum))
        .mapFailure(
            Case(
                $(allOf(
                    instanceOf(NoSuchElementException.class),
                    (Predicate<Throwable>) throwable -> Objects.equals("reduceLeft on Nil", throwable.getLocalizedMessage()))),
                new NoSuchWordInText()))
        .onSuccess(counter -> Option.when(counter > 1, true).forEach(o -> logInfo(word)))
        .toEither();
  }

  public static final class NoSuchWordInText extends Throwable {

    @Override
    public boolean equals(Object obj) {
      return obj instanceof NoSuchWordInText;
    }
  }
}
