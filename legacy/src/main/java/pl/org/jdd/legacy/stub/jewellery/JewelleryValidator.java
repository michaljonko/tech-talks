package pl.org.jdd.legacy.stub.jewellery;

import static java.util.Objects.nonNull;

import com.google.common.collect.ImmutableList;
import java.util.Collection;

public final class JewelleryValidator {

  private final Collection<String> jewelleryTypes = ImmutableList.of("diamonds", "gold", "silver", "platinum");

  public boolean isValid(Jewellery jewellery) {

    return nonNull(jewellery) && jewelleryTypes.stream().anyMatch(type -> type.equals(jewellery.getType()));
  }
}
