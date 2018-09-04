package pl.org.jdd.chain.functions;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Option;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;

@Slf4j
public final class ValidateJewelleryFunction implements Function1<Jewellery, Option<Jewellery>> {

  private final MeterRegistry meterRegistry;
  private final JewelleryValidator diamondValidator;

  public ValidateJewelleryFunction(
      @NonNull JewelleryValidator diamondValidator,
      @NonNull MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
    this.diamondValidator = diamondValidator;
  }

  @Override
  public Option<Jewellery> apply(Jewellery jewellery) {
    if (diamondValidator.isValid(jewellery)) {
      return Option.of(jewellery);
    } else {
      log.info("Invalid Jewellery: {}", jewellery);
      meterRegistry.counter("jewellery.invalid.counter").increment();
      return Option.none();
    }
  }
}