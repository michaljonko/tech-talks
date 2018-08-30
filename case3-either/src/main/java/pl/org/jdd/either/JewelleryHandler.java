package pl.org.jdd.either;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.control.Either;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryPacker;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;

@Slf4j
public final class JewelleryHandler implements Handler<Jewellery, Location> {

  private final JewelleryPacker packer;
  private final JewelleryValidator validator;
  private final Treasury treasury;
  private final MeterRegistry meterRegistry;

  public JewelleryHandler(
      @NonNull JewelleryValidator validator,
      @NonNull JewelleryPacker packer,
      @NonNull Treasury treasury,
      @NonNull MeterRegistry meterRegistry) {
    this.treasury = treasury;
    this.packer = packer;
    this.meterRegistry = meterRegistry;
    this.validator = validator;
  }

  @Override
  public Either<Throwable, Location> handleSouvenir(Jewellery jewellery) {
    return Either.left(new RuntimeException());
  }
}
