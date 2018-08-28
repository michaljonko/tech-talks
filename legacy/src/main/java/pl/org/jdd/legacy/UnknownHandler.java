package pl.org.jdd.legacy;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import pl.org.jdd.legacy.stub.Souvenir;

@Slf4j
public final class UnknownHandler implements Handler<Souvenir> {

  @Override
  public void handleSouvenir(Souvenir souvenir) {
    throw new IllegalStateException(MessageFormatter.format("Cannot handle souvenir: {}", souvenir).getMessage());
  }
}
