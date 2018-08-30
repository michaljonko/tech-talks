package pl.org.jdd.either.functions;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Option;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.diamond.Diamond;

public class ReportSouvenirFunction implements Function1<Diamond, Either<Throwable, Diamond>> {

    private final MeterRegistry meterRegistry;

    public ReportSouvenirFunction(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public Either<Throwable, Diamond> apply(Diamond diamond) {
        meterRegistry.counter("messages.delivered").increment();
        return Either.right(diamond);
    }
}