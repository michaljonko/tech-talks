package pl.org.jdd.either;

import io.vavr.control.Either;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Souvenir;

public interface Handler<I extends Souvenir, V extends Location> {

  Either<SomethingWrong, V> handleSouvenir(I souvenir);
}
