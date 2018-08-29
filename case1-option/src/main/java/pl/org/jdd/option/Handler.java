package pl.org.jdd.option;

import io.vavr.control.Option;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Souvenir;

public interface Handler<T extends Souvenir> {

  Option<Location> handleSouvenir(T souvenir);
}
