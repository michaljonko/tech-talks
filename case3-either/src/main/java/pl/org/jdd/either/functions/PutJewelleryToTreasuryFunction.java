package pl.org.jdd.either.functions;

import io.vavr.Function1;
import io.vavr.control.Either;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.SouvenirPackage;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryPacker;

@Slf4j
public final class PutJewelleryToTreasuryFunction implements
    Function1<Jewellery, Either<? extends Throwable, Location>> {

  private final JewelleryPacker packer;
  private final Treasury treasury;

  public PutJewelleryToTreasuryFunction(
      @NonNull JewelleryPacker packer,
      @NonNull Treasury treasury) {
    this.packer = packer;
    this.treasury = treasury;
  }

  @Override
  public Either<? extends Throwable, Location> apply(Jewellery jewellery) {
    log.info("Pack & put to treasury.");
    SouvenirPackage jewelleryPackage = packer.pack(jewellery);
    return Either.right(treasury.put(jewelleryPackage));
  }
}
