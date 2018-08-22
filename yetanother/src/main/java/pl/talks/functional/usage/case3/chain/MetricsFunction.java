package pl.talks.functional.usage.case3.chain;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Either;
import pl.talks.functional.either.Failure;
import pl.talks.functional.usage.case3.stub.ResourceA;

public class MetricsFunction implements Function1<ResourceA, Either<Failure, ResourceA>> {

    private final MeterRegistry meterRegistry;

    public MetricsFunction(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public Either<Failure, ResourceA> apply(ResourceA resourceA) {
        meterRegistry.counter("messages.delivered").increment();
        return Either.right(resourceA);
    }
}