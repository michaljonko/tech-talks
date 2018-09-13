package pl.org.jdd.tryof;

import io.micrometer.core.instrument.Metrics;
import io.vavr.control.Either;
import org.junit.Test;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryPacker;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;
import pl.org.jdd.tryof.failure.ConstraintTypes;
import pl.org.jdd.tryof.failure.Failure;

public class JewelleryHandlerTest {

  private final JewelleryHandler handler =
      new JewelleryHandler(new JewelleryValidator(), new JewelleryPacker(), new Treasury(), Metrics.globalRegistry);

  @Test
  public void topazIsNotValuableSouvenir() {
    Either<Failure, Object> expectedEither = Either.left(Failure.create(ConstraintTypes.NOT_VALUABLE_SOUVENIR));

    Either<Failure, Location> either = handler.handleSouvenir(new Jewellery("topaz"));

    assert either.equals(expectedEither);
  }

  @Test
  public void platinumIsPackedInSafe() {
    Either<Object, Location> expectedEither = Either.right(new Location("safe behind the picture"));

    Either<Failure, Location> either = handler.handleSouvenir(new Jewellery("platinum"));

    assert either.equals(expectedEither);
  }
}