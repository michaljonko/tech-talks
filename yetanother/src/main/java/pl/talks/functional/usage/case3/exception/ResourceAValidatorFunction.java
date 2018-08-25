package pl.talks.functional.usage.case3.exception;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import pl.talks.functional.either.Constraint;
import pl.talks.functional.either.Failure;
import pl.talks.functional.usage.case3.stub.ResourceA;
import pl.talks.functional.usage.case3.stub.ResourceAValidator;

@Slf4j
public class ResourceAValidatorFunction implements Function1<ResourceA, ResourceA> {
    private final MeterRegistry meterRegistry;
    private final ResourceAValidator resourceAValidator;

    public ResourceAValidatorFunction(MeterRegistry meterRegistry, ResourceAValidator resourceAValidator) {
        this.meterRegistry = meterRegistry;
        this.resourceAValidator = resourceAValidator;
    }

    @Override
    public ResourceA apply(ResourceA resourceA) {
        if(resourceAValidator.isValid(resourceA)) {
//            log.error("Invalid ResourceA Data");
            meterRegistry.counter("messages.invalid.data").increment();
            return resourceA;
        } else {
            throw new RuntimeException("dupa");
        }
    }
}