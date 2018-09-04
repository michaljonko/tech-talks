package pl.org.jdd.tryof;

import io.vavr.control.Either;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Souvenir;
import pl.org.jdd.tryof.failure.SomethingWrong;

public interface Handler<I extends Souvenir, O extends Location> {

  Either<? extends SomethingWrong, O> handleSouvenir(I souvenir);
}
