package pl.org.jdd.either;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.diamond.Diamond;
import pl.org.jdd.legacy.stub.diamond.DiamondPacker;
import pl.org.jdd.legacy.stub.diamond.DiamondValidator;

@Slf4j
public final class DiamondsHandler implements Handler<Diamond, Location> {

  private final DiamondPacker converter;
  private final Treasury treasury;
  private final DiamondValidator validator;
  private final MeterRegistry meterRegistry;

  public DiamondsHandler(
      Treasury treasury,
      DiamondPacker converter,
      DiamondValidator validator,
      MeterRegistry meterRegistry) {
    this.treasury = treasury;
    this.converter = converter;
    this.meterRegistry = meterRegistry;
    this.validator = validator;
  }

  @Override
  public Either<Throwable, Location> handleSouvenir(Diamond diamond) {
    return Either.left(new RuntimeException());
  }
}
