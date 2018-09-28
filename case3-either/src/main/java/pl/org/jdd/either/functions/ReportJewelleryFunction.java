package pl.org.jdd.either.functions;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Either;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;

@Slf4j
public final class ReportJewelleryFunction implements Function1<Jewellery, Either<? extends Throwable, Jewellery>> {

  private final MeterRegistry meterRegistry;

  public ReportJewelleryFunction(@NonNull MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Override
  public Either<? extends Throwable, Jewellery> apply(Jewellery jewellery) {
    try {
      log.info("Report new jewellery: {}", jewellery);
      meterRegistry.counter("jewellery.counter").increment();
      return Either.right(jewellery);
    } catch (Throwable throwable) {
      log.error("Something went wrong during reporting.", throwable);
      return Either.left(throwable);
    }
  }
}