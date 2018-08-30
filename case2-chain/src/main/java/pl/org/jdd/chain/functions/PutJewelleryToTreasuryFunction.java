package pl.org.jdd.chain.functions;

import io.vavr.Function1;
import io.vavr.control.Option;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryPacker;

@Slf4j
public class PutJewelleryToTreasuryFunction implements Function1<Option<Jewellery>, Option<Location>> {

  private final JewelleryPacker packer;
  private final Treasury treasury;

  public PutJewelleryToTreasuryFunction(
      @NonNull JewelleryPacker packer,
      @NonNull Treasury treasury) {
    this.packer = packer;
    this.treasury = treasury;
  }

  @Override
  public Option<Location> apply(Option<Jewellery> jewellery) {
    return jewellery
        .map(packer::convert)
        .map(treasury::put);
  }
}
