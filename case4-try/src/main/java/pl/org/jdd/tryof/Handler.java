package pl.org.jdd.tryof;

import io.vavr.control.Either;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Souvenir;
import pl.org.jdd.tryof.failure.Failure;

public interface Handler<I extends Souvenir, O extends Location> {

  Either<Failure, O> handleSouvenir(I souvenir);
}
