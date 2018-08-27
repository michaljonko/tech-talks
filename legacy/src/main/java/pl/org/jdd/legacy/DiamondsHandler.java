package pl.org.jdd.legacy;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.SouvenirRequestMessage;
import pl.org.jdd.legacy.stub.Diamond;
import pl.org.jdd.legacy.stub.DiamondMessageConverter;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.DiamondValidator;
import pl.org.jdd.legacy.stub.Handler;

@Slf4j
public class DiamondsHandler implements Handler {

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
    this.validator = validator;
    this.meterRegistry = meterRegistry;
  }

  @Override
  public void handleSouvenir(Diamond diamond) {
    if (validator.isValid(diamond)) {
      meterRegistry.counter("messages.diamonds").increment();
      SouvenirRequestMessage requestMessage = converter.convert(diamond);
      treasury.put(requestMessage);
    } else {
      log.info("Invalid Diamond");
      meterRegistry.counter("messages.invalid.data").increment();
    }
  }
}
