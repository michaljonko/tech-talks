package pl.talks.functional.usage.case3;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Log
public class ResourceAHandlerOld implements Handler {
    private final ResourceAConverter converter;
    private final ResourceFactoryClient resourceFactoryClient;
    private final MeterRegistry meterRegistry;
    private final ResourceAValidator resourceAValidator;

    public ResourceAHandlerOld(ResourceFactoryClient resourceFactoryClient, ResourceAConverter converter,
                               MeterRegistry meterRegistry, ResourceAValidator resourceAValidator) {
        this.resourceFactoryClient = resourceFactoryClient;
        this.converter = converter;
        this.meterRegistry = meterRegistry;
        this.resourceAValidator = resourceAValidator;
    }


    @Override
    public void handleMessage(ResourceA resourceAMessage) {
        if (resourceAValidator.isValid(resourceAMessage)) {
            meterRegistry.counter("messages.delivered").increment();
            CommandRequestMessage requestMessage = converter.convert(resourceAMessage);
            resourceFactoryClient.send(requestMessage);
        } else {
            log.info("Invalid ResourceA Data");
            meterRegistry.counter("messages.invalid.data").increment();
        }
    }


    @Override
    public Class<ResourceA> getHandlerClass() {
        return ResourceA.class;
    }
}
