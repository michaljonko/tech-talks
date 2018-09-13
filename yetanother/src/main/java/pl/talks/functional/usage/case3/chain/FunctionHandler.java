package pl.talks.functional.usage.case3.chain;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.control.Either;
import pl.talks.functional.either.Failure;
import pl.talks.functional.usage.case3.stub.Handler;
import pl.talks.functional.usage.case3.stub.ResourceA;
import pl.talks.functional.usage.case3.stub.ResourceAConverter;
import pl.talks.functional.usage.case3.stub.ResourceAValidator;
import pl.talks.functional.usage.case3.stub.ResourceFactoryClient;

public class FunctionHandler implements Handler {

  private final ResourceAConverter converter;
  private final ResourceFactoryClient resourceFactoryClient;
  private final MeterRegistry meterRegistry;
  private final ResourceAValidator resourceAValidator;

  public FunctionHandler(ResourceAConverter converter, ResourceFactoryClient resourceFactoryClient,
      MeterRegistry meterRegistry,
      ResourceAValidator resourceAValidator) {
    this.converter = converter;
    this.resourceFactoryClient = resourceFactoryClient;
    this.meterRegistry = meterRegistry;
    this.resourceAValidator = resourceAValidator;
  }

  @Override
  public void handleMessage(ResourceA ticketDeltaMessage) {
    List<Function1<ResourceA, Either<Failure, ResourceA>>> chain = List.of(
        new ResourceAValidatorFunction(meterRegistry, resourceAValidator),
        new MetricsFunction(meterRegistry),
        new ResourceAHandlerFunction(converter, resourceFactoryClient));

    for (Function1<ResourceA, Either<Failure, ResourceA>> function : chain) {
      ResourceA ticketDeltaMessage1 = ticketDeltaMessage;
      Either<Failure, ResourceA> apply = function.apply(ticketDeltaMessage1);
//            apply.map(resourceA -> )
    }
  }

  @Override
  public Class<ResourceA> getHandlerClass() {
    return ResourceA.class;
  }
}
