package pl.org.jdd.tryof.functions;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.function.Consumer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;

@Slf4j
public final class ReportJewelleryConsumer implements Consumer<Jewellery> {

  private final MeterRegistry meterRegistry;

  public ReportJewelleryConsumer(@NonNull MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Override
  public void accept(Jewellery jewellery) {
    meterRegistry.counter("jewellery.counter").increment();
  }
}