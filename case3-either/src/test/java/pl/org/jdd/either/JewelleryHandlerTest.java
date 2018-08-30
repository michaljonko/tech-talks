package pl.org.jdd.either;

import io.micrometer.core.instrument.Metrics;
import io.vavr.control.Either;
import java.util.Objects;
import org.junit.Test;
import pl.org.jdd.either.exception.NotValuableSouvenirException;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryPacker;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;

public class JewelleryHandlerTest {

  private final JewelleryHandler handler =
      new JewelleryHandler(new JewelleryValidator(), new JewelleryPacker(), new Treasury(), Metrics.globalRegistry);

  @Test
  public void topazIsNotValuableSouvenir() throws Exception {
    Either<Throwable, Location> either = handler.handleSouvenir(new Jewellery("topaz"));

    assert either.getLeft() instanceof NotValuableSouvenirException;
  }

  @Test
  public void diamondsArePackedInBlackHole() throws Exception {
    Either<Throwable, Location> either = handler.handleSouvenir(new Jewellery("diamonds"));

    assert Objects.equals("Black hole", either.get().getDirection());
  }
}