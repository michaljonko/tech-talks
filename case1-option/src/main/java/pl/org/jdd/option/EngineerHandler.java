package pl.org.jdd.option;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.Engineer;
import pl.org.jdd.legacy.stub.EngineerConverter;
import pl.org.jdd.legacy.stub.EngineerFactoryClient;
import pl.org.jdd.legacy.stub.EngineerValidator;
import pl.org.jdd.legacy.stub.Handler;

@Slf4j
public class EngineerHandler implements Handler {

  private final EngineerConverter converter;
  private final EngineerFactoryClient factoryClient;
  private final EngineerValidator validator;
  private final MeterRegistry meterRegistry;

  public EngineerHandler(
      EngineerFactoryClient factoryClient,
      EngineerConverter converter,
      EngineerValidator validator,
      MeterRegistry meterRegistry) {
    this.factoryClient = factoryClient;
    this.converter = converter;
    this.meterRegistry = meterRegistry;
    this.validator = validator;
  }

  @Override
  public void handleMessage(Engineer engineer) {
    Option.of(engineer)
        .filter(validator::isValid)
        .onEmpty(() -> {
          log.info("Invalid ResourceA Data");
          meterRegistry.counter("messages.invalid.data").increment();
        })
        .peek(eng -> meterRegistry.counter("messages.delivered").increment())
        .map(converter::convert)
        .forEach(factoryClient::send);
  }

  @Override
  public Class<Engineer> getHandlerClass() {
    return Engineer.class;
  }
}
