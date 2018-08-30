package pl.org.jdd.tryof;

import io.vavr.control.Either;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Souvenir;

public interface Handler<I extends Souvenir, O extends Location> {

  Either<SomethingWrong, O> handleSouvenir(I souvenir);
}
