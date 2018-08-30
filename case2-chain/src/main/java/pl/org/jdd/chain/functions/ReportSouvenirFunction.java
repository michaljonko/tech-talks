package pl.org.jdd.chain.functions;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Option;
import pl.org.jdd.legacy.stub.diamond.Diamond;

public class ReportSouvenirFunction implements Function1<Option<Diamond>, Option<Diamond>> {

    private final MeterRegistry meterRegistry;

    public ReportSouvenirFunction(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public Option<Diamond> apply(Option<Diamond> diamond) {
        return diamond
                .peek(diamond1 -> {
                    meterRegistry.counter("messages.delivered").increment();
                });
    }
}