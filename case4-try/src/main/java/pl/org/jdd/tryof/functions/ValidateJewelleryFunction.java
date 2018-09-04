package pl.org.jdd.tryof.functions;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.either.exception.NotValuableSouvenirException;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;

@Slf4j
public class ValidateJewelleryFunction implements Function1<Jewellery, Jewellery> {

  private final MeterRegistry meterRegistry;
  private final JewelleryValidator validator;

  public ValidateJewelleryFunction(
      @NonNull JewelleryValidator validator,
      @NonNull MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
    this.validator = validator;
  }

  @Override
  public Jewellery apply(Jewellery jewellery) {
    if (validator.isValid(jewellery)) {
      log.info("Valid Jewellery.");
      return jewellery;
    } else {
      log.info("Invalid Jewellery.");
      meterRegistry.counter("jewellery.invalid.counter").increment();
      throw new NotValuableSouvenirException("Not valid jewellery.");
    }
  }
}