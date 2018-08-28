package pl.org.jdd.legacy;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import pl.org.jdd.legacy.stub.Diamond;
import pl.org.jdd.legacy.stub.Souvenir;

public final class SouvenirDispatcher {

  private static final Map<Class<? extends Souvenir>, Handler> HANDLERS =
      ImmutableMap.of(Diamond.class, DiamondsHandler.create());
  private static final Handler UNKNOWN_HANDLER = new UnknownHandler();

  public void dispatch(Souvenir souvenir) {
    HANDLERS.getOrDefault(souvenir.getClass(), UNKNOWN_HANDLER)
        .handleSouvenir(souvenir);
  }
}
