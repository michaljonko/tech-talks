package pl.org.jdd.either.functions;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Either;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;

@Slf4j
public class ReportJewelleryFunction implements Function1<Jewellery, Either<Throwable, Jewellery>> {

  private final MeterRegistry meterRegistry;

  public ReportJewelleryFunction(@NonNull MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Override
  public Either<Throwable, Jewellery> apply(Jewellery jewellery) {
    log.info("Report new jewellery.");
    meterRegistry.counter("jewellery.counter").increment();
    return Either.right(jewellery);
  }
}