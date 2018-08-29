package pl.org.jdd.either;

import io.vavr.control.Either;
import io.vavr.control.Option;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Souvenir;

public interface Handler<I extends Souvenir, V extends Location> {

  Either<Throwable, V> handleSouvenir(I souvenir);
}
