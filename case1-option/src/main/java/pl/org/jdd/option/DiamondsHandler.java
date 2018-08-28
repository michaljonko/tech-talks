package pl.org.jdd.option;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.Diamond;
import pl.org.jdd.legacy.stub.DiamondMessageConverter;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.DiamondValidator;
import pl.org.jdd.legacy.Handler;

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
  public void handleSouvenir(Diamond diamond) {
    Option.of(diamond)
        .filter(validator::isValid)
        .onEmpty(() -> {
          log.info("Invalid Diamond.");
          meterRegistry.counter("messages.invalid.data").increment();
        })
        .peek(eng -> meterRegistry.counter("messages.diamonds").increment())
        .map(converter::convert)
        .forEach(treasury::put);
  }

}
