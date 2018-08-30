package pl.org.jdd.tryof;

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

  private final JewelleryPacker converter;
  private final Treasury treasury;
  private final JewelleryValidator validator;
  private final MeterRegistry meterRegistry;

  public JewelleryHandler(
      @NonNull JewelleryValidator validator,
      @NonNull JewelleryPacker converter,
      @NonNull Treasury treasury,
      @NonNull MeterRegistry meterRegistry) {
    this.treasury = treasury;
    this.converter = converter;
    this.meterRegistry = meterRegistry;
    this.validator = validator;
  }

  @Override
  public Either<SomethingWrong, Location> handleSouvenir(Jewellery jewellery) {
    return Either.left(new SomethingWrong("jDD sucks!"));
  }
}
