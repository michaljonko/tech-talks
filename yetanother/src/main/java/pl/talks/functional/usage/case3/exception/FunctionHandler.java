package pl.talks.functional.usage.case3.exception;

import io.vavr.Function1;
import io.vavr.collection.List;
import pl.talks.functional.usage.case3.stub.Handler;
import pl.talks.functional.usage.case3.stub.ResourceA;

public class FunctionHandler implements Handler {

  private final ResourceAValidatorFunction resourceAValidatorFunction;
  private final MetricsFunction metricsFunction;
  private final ResourceAHandlerFunction resourceAHandlerFunction;

  public FunctionHandler(ResourceAValidatorFunction resourceAValidatorFunction,
      MetricsFunction metricsFunction, ResourceAHandlerFunction resourceAHandlerFunction) {
    this.resourceAValidatorFunction = resourceAValidatorFunction;
    this.metricsFunction = metricsFunction;
    this.resourceAHandlerFunction = resourceAHandlerFunction;
  }

  @Override
  public void handleMessage(ResourceA ticketDeltaMessage) {
    List.of(resourceAValidatorFunction, metricsFunction, resourceAHandlerFunction)
        .reduce(Function1::andThen)
        .apply(ticketDeltaMessage);
  }

  @Override
  public Class<ResourceA> getHandlerClass() {
    return ResourceA.class;
  }
}
