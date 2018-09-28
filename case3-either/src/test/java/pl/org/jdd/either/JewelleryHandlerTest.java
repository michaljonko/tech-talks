package pl.org.jdd.either;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import io.micrometer.core.instrument.MeterRegistry;
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

  @Test
  public void topazIsNotValuableSouvenir() throws Exception {
    Either<? extends Throwable, Object> expectedEither =
        Either.left(new NotValuableSouvenirException("Not valid jewellery."));
    JewelleryHandler handler =
        new JewelleryHandler(new JewelleryValidator(), new JewelleryPacker(), new Treasury(), Metrics.globalRegistry);

    Either<? extends Throwable, Location> either = handler.handleSouvenir(new Jewellery("topaz"));

    assert either.equals(expectedEither);
  }

  @Test
  public void silverIsPackedInSafe() throws Exception {
    Either<Object, Location> expectedEither =
        Either.right(new Location("safe behind the picture"));
    JewelleryHandler handler =
        new JewelleryHandler(new JewelleryValidator(), new JewelleryPacker(), new Treasury(), Metrics.globalRegistry);

    Either<? extends Throwable, Location> either = handler.handleSouvenir(new Jewellery("silver"));

    assert either.equals(expectedEither);
  }

  @Test
  public void reportFunctionThrowsRuntimeException() throws Exception {
    Throwable throwable = new RuntimeException("Expected exception");
    Either<? extends Throwable, Object> expectedEither = Either.left(throwable);
    MeterRegistry meterRegistry = mock(MeterRegistry.class);
    given(meterRegistry.counter(anyString())).willThrow(throwable);
    JewelleryHandler handler =
        new JewelleryHandler(new JewelleryValidator(), new JewelleryPacker(), new Treasury(), meterRegistry);

    Either<? extends Throwable, Location> either = handler.handleSouvenir(new Jewellery("silver"));

    assert either.equals(expectedEither);
  }
}