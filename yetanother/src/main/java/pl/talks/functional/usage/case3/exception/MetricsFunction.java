package pl.talks.functional.usage.case3.exception;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import pl.talks.functional.usage.case3.stub.ResourceA;

public class MetricsFunction implements Function1<ResourceA, ResourceA> {

  private final MeterRegistry meterRegistry;

  public MetricsFunction(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Override
  public ResourceA apply(ResourceA resourceA) {
    meterRegistry.counter("messages.delivered").increment();
    return resourceA;
  }
}