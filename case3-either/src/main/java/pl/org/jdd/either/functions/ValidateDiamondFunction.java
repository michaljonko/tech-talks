package pl.org.jdd.either.functions;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.either.TolaSystemException;
import pl.org.jdd.legacy.stub.diamond.Diamond;
import pl.org.jdd.legacy.stub.diamond.DiamondValidator;

@Slf4j
public class ValidateDiamondFunction implements Function1<Diamond, Either<Throwable, Diamond>> {
    private final MeterRegistry meterRegistry;
    private final DiamondValidator diamondValidator;

    public ValidateDiamondFunction(MeterRegistry meterRegistry, DiamondValidator diamondValidator) {
        this.meterRegistry = meterRegistry;
        this.diamondValidator = diamondValidator;
    }


    @Override
    public Either<Throwable, Diamond> apply(Diamond diamond) {
        if (diamondValidator.isValid(diamond)) {
            log.error("Invalid ResourceA Data");
            meterRegistry.counter("messages.invalid.data").increment();
            return Either.right(diamond);
        } else {
            return Either.left(new TolaSystemException("Problem while validation"));
        }
    }
}