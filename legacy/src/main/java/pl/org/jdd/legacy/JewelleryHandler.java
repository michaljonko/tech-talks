package pl.org.jdd.legacy;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.SouvenirPackage;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryPacker;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;

@Slf4j
public final class JewelleryHandler implements Handler<Jewellery> {

  private final JewelleryPacker packer;
  private final Treasury treasury;
  private final JewelleryValidator validator;
  private final MeterRegistry meterRegistry;

  JewelleryHandler(
      @NonNull JewelleryValidator validator,
      @NonNull JewelleryPacker packer,
      @NonNull Treasury treasury,
      @NonNull MeterRegistry meterRegistry) {
    this.treasury = treasury;
    this.packer = packer;
    this.validator = validator;
    this.meterRegistry = meterRegistry;
  }

  public static JewelleryHandler create() {
    return new JewelleryHandler(new JewelleryValidator(), new JewelleryPacker(), new Treasury(),
        Metrics.globalRegistry);
  }

  @Override
  public void handleSouvenir(Jewellery jewellery) {
    if (validator.isValid(jewellery)) {
      meterRegistry.counter("jewellery.counter").increment();
      SouvenirPackage requestMessage = packer.pack(jewellery);
      treasury.put(requestMessage);
    } else {
      log.info("Invalid Jewellery");
      meterRegistry.counter("jewellery.invalid.counter").increment();
    }
  }
}
