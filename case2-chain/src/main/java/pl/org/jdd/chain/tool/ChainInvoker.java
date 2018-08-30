package pl.org.jdd.chain.tool;

import io.vavr.Function1;
import io.vavr.collection.List;
import pl.org.jdd.legacy.stub.Souvenir;

public class ChainInvoker {

    public static <R> R invokeChain(Souvenir souvenir, Function1... functions) {
        return (R) List.of(functions).reduceLeft(Function1::andThen).apply(souvenir);
    }
}
