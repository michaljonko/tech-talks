package pl.org.jdd.option;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.diamond.Diamond;
import pl.org.jdd.legacy.stub.diamond.DiamondMessageConverter;
import pl.org.jdd.legacy.stub.diamond.DiamondValidator;

@Slf4j
public final class DiamondsHandler implements Handler<Diamond> {

  private final DiamondMessageConverter converter;
  private final Treasury treasury;
  private final DiamondValidator validator;
  private final MeterRegistry meterRegistry;

  public DiamondsHandler(
      Treasury treasury,
      DiamondMessageConverter converter,
      DiamondValidator validator,
      MeterRegistry meterRegistry) {
    this.treasury = treasury;
    this.converter = converter;
    this.meterRegistry = meterRegistry;
    this.validator = validator;
  }

  @Override
  public Option<Location> handleSouvenir(Diamond diamond) {
    return Option.of(diamond)
        .filter(validator::isValid)
        .onEmpty(() -> {
          log.info("Invalid Diamond.");
          meterRegistry.counter("messages.invalid.data").increment();
        })
        .peek(eng -> meterRegistry.counter("messages.diamonds").increment())
        .map(converter::convert)
        .map(treasury::put)
        .toOption();
  }
}
