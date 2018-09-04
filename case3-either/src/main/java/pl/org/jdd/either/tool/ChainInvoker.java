package pl.org.jdd.either.tool;

import io.vavr.Function1;
import io.vavr.collection.LinearSeq;
import io.vavr.collection.List;
import io.vavr.control.Either;

public final class ChainInvoker {

  private ChainInvoker() {
    throw new UnsupportedOperationException();
  }

  @SafeVarargs
  public static <I, O> Either<? extends Throwable, O> chain(I input, Function1<I, ?>... functions) {
    return chain(Either.right(input), List.of(functions));
  }

  @SuppressWarnings("unchecked")
  private static <I, O> Either<? extends Throwable, O> chain(
      Either<? extends Throwable, I> either,
      LinearSeq<Function1> functions) {
    if (functions.nonEmpty()) {
      return chain(either.flatMap(functions.head()), functions.tail());
    }
    return (Either<? extends Throwable, O>) either;
  }
}
