package pl.org.jdd.option;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.vavr.control.Option;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryPacker;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;

@Slf4j
public final class JewelleryHandler implements Handler<Jewellery, Location> {

  private final JewelleryValidator validator;
  private final JewelleryPacker packer;
  private final Treasury treasury;
  private final MeterRegistry meterRegistry;

  JewelleryHandler(
      @NonNull JewelleryValidator validator,
      @NonNull JewelleryPacker packer,
      @NonNull Treasury treasury,
      @NonNull MeterRegistry meterRegistry) {
    this.treasury = treasury;
    this.packer = packer;
    this.meterRegistry = meterRegistry;
    this.validator = validator;
  }

  public static JewelleryHandler create() {
    return new JewelleryHandler(new JewelleryValidator(), new JewelleryPacker(), new Treasury(), Metrics.globalRegistry);
  }

  @Override
  public Option<Location> handleSouvenir(Jewellery jewellery) {
    return Option.of(jewellery)
        .peek(j -> log.info("Handling {} with Option solution", j))
        .filter(validator::isValid)
        .onEmpty(() -> {
          log.info("Invalid Jewellery.");
          meterRegistry.counter("jewellery.invalid.counter").increment();
        })
        .peek(j -> meterRegistry.counter("jewellery.counter").increment())
        .map(packer::convert)
        .map(treasury::put)
        .toOption();
  }
}
