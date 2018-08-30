package pl.org.jdd.legacy.stub.jewellery;

import static java.util.Objects.nonNull;

import java.util.Objects;

public final class JewelleryValidator {

  public boolean isValid(Jewellery jewellery) {
    return nonNull(jewellery) && Objects.equals("diamonds", jewellery.getType());
  }
}
