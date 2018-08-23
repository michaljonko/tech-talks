package pl.talks.functional.usage.case3.exception;

import io.vavr.Function1;
import io.vavr.control.Either;
import lombok.extern.java.Log;
import pl.talks.functional.either.Failure;
import pl.talks.functional.usage.case3.stub.CommandRequestMessage;
import pl.talks.functional.usage.case3.stub.ResourceA;
import pl.talks.functional.usage.case3.stub.ResourceAConverter;
import pl.talks.functional.usage.case3.stub.ResourceFactoryClient;

@Log
public class ResourceAHandlerFunction implements Function1<ResourceA, ResourceA> {
    private final ResourceAConverter converter;
    private final ResourceFactoryClient resourceFactoryClient;

    public ResourceAHandlerFunction(ResourceAConverter converter, ResourceFactoryClient resourceFactoryClient) {
        this.converter = converter;
        this.resourceFactoryClient = resourceFactoryClient;
    }

    @Override
    public ResourceA apply(ResourceA resourceA) {
        CommandRequestMessage message = converter.convert(resourceA);
        resourceFactoryClient.send(message);
        return resourceA;
    }
}
