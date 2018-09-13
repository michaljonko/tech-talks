package pl.org.jdd.either;

import io.micrometer.core.instrument.Metrics;
import io.vavr.control.Either;
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
    Either<? extends Throwable, Object> expectedEither = Either
        .left(new NotValuableSouvenirException("Not valid jewellery."));

    Either<? extends Throwable, Location> either = handler.handleSouvenir(new Jewellery("topaz"));

    assert either.equals(expectedEither);
  }

  @Test
  public void silverIsPackedInSafe() throws Exception {
    Either<Object, Location> expectedEither = Either.right(new Location("safe behind the picture"));

    Either<? extends Throwable, Location> either = handler.handleSouvenir(new Jewellery("silver"));

    assert either.equals(expectedEither);
  }
}