package pl.org.jdd.chain;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.diamond.Diamond;
import pl.org.jdd.legacy.stub.diamond.DiamondPacker;
import pl.org.jdd.legacy.stub.diamond.DiamondValidator;
import pl.org.jdd.option.Handler;

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
  public Option<Location> handleSouvenir(Diamond diamond) {
    return Option.none();
  }
}
