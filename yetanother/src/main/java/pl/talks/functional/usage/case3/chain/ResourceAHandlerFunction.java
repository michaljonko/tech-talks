package pl.talks.functional.usage.case3.chain;

import io.vavr.Function1;
import io.vavr.control.Either;
import lombok.extern.java.Log;
import pl.talks.functional.either.Failure;
import pl.talks.functional.usage.case3.stub.*;

@Log
public class ResourceAHandlerFunction implements Function1<ResourceA, Either<Failure, ResourceA>> {

  private final ResourceAConverter converter;
  private final ResourceFactoryClient resourceFactoryClient;

  public ResourceAHandlerFunction(ResourceAConverter converter, ResourceFactoryClient resourceFactoryClient) {
    this.converter = converter;
    this.resourceFactoryClient = resourceFactoryClient;
  }

  @Override
  public Either<Failure, ResourceA> apply(ResourceA resourceA) {
    CommandRequestMessage message = converter.convert(resourceA);
    resourceFactoryClient.send(message);
    return Either.right(resourceA);
  }
}
