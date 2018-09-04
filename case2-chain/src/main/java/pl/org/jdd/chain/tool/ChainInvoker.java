package pl.org.jdd.chain.tool;

import io.vavr.Function1;
import io.vavr.collection.List;

public final class ChainInvoker {

  private ChainInvoker() {
    throw new UnsupportedOperationException();
  }

  @SuppressWarnings("unchecked")
  public static <I, O> O invoke(I input, Function1... functions) {
    Function1<I, O> composedFunction = List.of(functions).reduceLeft(Function1::andThen);
    return composedFunction.apply(input);
  }
}
