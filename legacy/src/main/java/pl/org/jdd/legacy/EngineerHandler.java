package pl.org.jdd.legacy;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.CommandRequestMessage;
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
    this.validator = validator;
    this.meterRegistry = meterRegistry;
  }

  @Override
  public void handleMessage(Engineer engineer) {
    if (validator.isValid(engineer)) {
      meterRegistry.counter("messages.delivered").increment();
      CommandRequestMessage requestMessage = converter.convert(engineer);
      factoryClient.send(requestMessage);
    } else {
      log.info("Invalid Engineer Data");
      meterRegistry.counter("messages.invalid.data").increment();
    }
  }

  @Override
  public Class<Engineer> getHandlerClass() {
    return Engineer.class;
  }
}
