package pl.org.jdd.chain.functions;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Option;
import lombok.NonNull;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;

public final class ReportJewelleryFunction implements Function1<Option<Jewellery>, Option<Jewellery>> {

  private final MeterRegistry meterRegistry;

  public ReportJewelleryFunction(@NonNull MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Override
  public Option<Jewellery> apply(Option<Jewellery> jewellery) {
    return jewellery
        .peek(souvenir -> meterRegistry.counter("jewellery.counter").increment());
  }
}