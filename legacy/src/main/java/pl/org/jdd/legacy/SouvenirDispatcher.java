package pl.org.jdd.legacy;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import pl.org.jdd.legacy.stub.Souvenir;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;

public final class SouvenirDispatcher {

  private static final Map<Class<? extends Souvenir>, Handler<? extends Souvenir>> HANDLERS =
      ImmutableMap.of(Jewellery.class, JewelleryHandler.create());
  private static final Handler UNKNOWN_HANDLER = new UnknownHandler();

  public void dispatch(Souvenir souvenir) {
    HANDLERS.getOrDefault(souvenir.getClass(), UNKNOWN_HANDLER)
        .handleSouvenir(souvenir);
  }
}
