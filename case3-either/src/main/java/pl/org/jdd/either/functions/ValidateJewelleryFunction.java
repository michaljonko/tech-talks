package pl.org.jdd.either.functions;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Either;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.either.exception.NotValuableSouvenirException;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;

@Slf4j
public final class ValidateJewelleryFunction implements Function1<Jewellery, Either<? extends Throwable, Jewellery>> {

  private final MeterRegistry meterRegistry;
  private final JewelleryValidator validator;

  public ValidateJewelleryFunction(
      @NonNull JewelleryValidator validator,
      @NonNull MeterRegistry meterRegistry) {
    this.validator = validator;
    this.meterRegistry = meterRegistry;
  }

  @Override
  public Either<? extends Throwable, Jewellery> apply(Jewellery jewellery) {
    try {
      if (validator.isValid(jewellery)) {
        log.info("Valid Jewellery: {}", jewellery);
        return Either.right(jewellery);
      } else {
        log.info("Invalid Jewellery: {}", jewellery);
        meterRegistry.counter("jewellery.invalid.counter").increment();
        return Either.left(new NotValuableSouvenirException("Not valid jewellery."));
      }
    } catch (Throwable throwable) {
      log.error("Something went wrong during validation.", throwable);
      return Either.left(throwable);
    }
  }
}