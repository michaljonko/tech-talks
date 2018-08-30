package pl.org.jdd.option;

import io.vavr.control.Option;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Souvenir;

public interface Handler<I extends Souvenir, O extends Location> {

  Option<O> handleSouvenir(I souvenir);
}
