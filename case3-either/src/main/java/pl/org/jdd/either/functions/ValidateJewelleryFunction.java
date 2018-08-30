package pl.org.jdd.either.functions;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Either;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.either.exception.TolaSystemException;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;

@Slf4j
public class ValidateJewelleryFunction implements Function1<Jewellery, Either<Throwable, Jewellery>> {

  private final MeterRegistry meterRegistry;
  private final JewelleryValidator validator;

  public ValidateJewelleryFunction(
      @NonNull JewelleryValidator validator,
      @NonNull MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
    this.validator = validator;
  }

  @Override
  public Either<Throwable, Jewellery> apply(Jewellery jewellery) {
    if (validator.isValid(jewellery)) {
      log.error("Invalid Jewellery.");
      meterRegistry.counter("jewellery.invalid.counter").increment();
      return Either.right(jewellery);
    } else {
      return Either.left(new TolaSystemException("Problem with validation"));
    }
  }
}