package pl.org.jdd.chain.functions;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.diamond.Diamond;
import pl.org.jdd.legacy.stub.diamond.DiamondValidator;

@Slf4j
public class ValidateDiamondFunction implements Function1<Diamond, Option<Diamond>> {
    private final MeterRegistry meterRegistry;
    private final DiamondValidator diamondValidator;

    public ValidateDiamondFunction(MeterRegistry meterRegistry, DiamondValidator diamondValidator) {
        this.meterRegistry = meterRegistry;
        this.diamondValidator = diamondValidator;
    }


    @Override
    public Option<Diamond> apply(Diamond diamond) {
        if (diamondValidator.isValid(diamond)) {
            log.error("Invalid ResourceA Data");
            meterRegistry.counter("messages.invalid.data").increment();
            return Option.of(diamond);
        } else {
            return Option.none();
        }
    }
}