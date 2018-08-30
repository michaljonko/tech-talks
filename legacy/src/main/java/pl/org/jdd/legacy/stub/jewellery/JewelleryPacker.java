package pl.org.jdd.legacy.stub.jewellery;

import static java.util.Objects.nonNull;

import pl.org.jdd.legacy.stub.SouvenirPackage;

public final class JewelleryPacker {

  public SouvenirPackage pack(Jewellery jewellery) {
    return nonNull(jewellery) ? new SouvenirPackage() : null;
  }
}
