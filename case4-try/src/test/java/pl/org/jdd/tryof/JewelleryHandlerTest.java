package pl.org.jdd.tryof;

import io.micrometer.core.instrument.Metrics;
import io.vavr.control.Either;
import java.util.Objects;
import org.junit.Test;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryPacker;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;
import pl.org.jdd.tryof.failure.NotValuableItem;
import pl.org.jdd.tryof.failure.SomethingWrong;

public class JewelleryHandlerTest {

  private final JewelleryHandler handler =
      new JewelleryHandler(new JewelleryValidator(), new JewelleryPacker(), new Treasury(), Metrics.globalRegistry);

  @Test
  public void topazIsNotValuableSouvenir() throws Exception {
    Jewellery topaz = new Jewellery("topaz");
    Either<? extends SomethingWrong, Location> either = handler.handleSouvenir(topaz);

    SomethingWrong somethingWrong = either.getLeft();
    assert somethingWrong instanceof NotValuableItem;
    assert ((NotValuableItem) somethingWrong).getSouvenir() == topaz;
    assert Objects.equals(somethingWrong.getMessage(), topaz.toString() + " has no value for Tola.");
  }

  @Test
  public void diamondsArePackedInBlackHole() throws Exception {
    Either<? extends SomethingWrong, Location> either = handler.handleSouvenir(new Jewellery("diamonds"));

    assert Objects.equals("Black hole", either.get().getDirection());
  }


}