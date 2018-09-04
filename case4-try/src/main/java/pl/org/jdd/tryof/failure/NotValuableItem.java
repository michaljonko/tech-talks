package pl.org.jdd.tryof.failure;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pl.org.jdd.legacy.stub.Souvenir;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public final class NotValuableItem extends SomethingWrong {

  private final Souvenir souvenir;

  public NotValuableItem(Souvenir souvenir) {
    super(souvenir.toString() + " has no value for Tola.");
    this.souvenir = souvenir;
  }
}
