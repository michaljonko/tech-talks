package pl.org.jdd.legacy;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.diamond.Diamond;
import pl.org.jdd.legacy.stub.diamond.DiamondPacker;
import pl.org.jdd.legacy.stub.diamond.DiamondValidator;
import pl.org.jdd.legacy.stub.PackageSouvenir;
import pl.org.jdd.legacy.stub.Treasury;

@Slf4j
public final class DiamondsHandler implements Handler<Diamond> {

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
    this.validator = validator;
    this.meterRegistry = meterRegistry;
  }

  public static DiamondsHandler create() {
    return new DiamondsHandler(new Treasury(), new DiamondPacker(), new DiamondValidator(), Metrics.globalRegistry);
  }

  @Override
  public void handleSouvenir(Diamond diamond) {
    if (validator.isValid(diamond)) {
      meterRegistry.counter("messages.diamonds").increment();
      PackageSouvenir requestMessage = converter.convert(diamond);
      treasury.put(requestMessage);
    } else {
      log.info("Invalid Diamond");
      meterRegistry.counter("messages.invalid.data").increment();
    }
  }
}
