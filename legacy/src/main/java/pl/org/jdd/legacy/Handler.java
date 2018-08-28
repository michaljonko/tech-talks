package pl.org.jdd.legacy;

import pl.org.jdd.legacy.stub.Souvenir;

public interface Handler<T extends Souvenir> {

  void handleSouvenir(T souvenir);
}
