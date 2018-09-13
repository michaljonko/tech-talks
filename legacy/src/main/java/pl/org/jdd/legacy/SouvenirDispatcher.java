package pl.org.jdd.legacy;

import static pl.org.jdd.legacy.stub.SouvenireType.JEWELLERY;
import static pl.org.jdd.legacy.stub.SouvenireType.TOY;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import pl.org.jdd.legacy.stub.Souvenir;
import pl.org.jdd.legacy.stub.SouvenireType;

public final class SouvenirDispatcher {

  private static final Map<SouvenireType, Handler<? extends Souvenir>> HANDLERS =
      ImmutableMap.<SouvenireType, Handler<? extends Souvenir>>builder()
          .put(JEWELLERY, JewelleryHandler.create())
          .put(TOY, ToysHandler.create())
          .build();
  private static final Handler UNKNOWN_HANDLER = new UnknownHandler();

  public void dispatch(Souvenir souvenir) {
    SouvenireType souvenirType = recognizeType(souvenir);
    HANDLERS.getOrDefault(souvenirType, UNKNOWN_HANDLER)
        .handleSouvenir(souvenir);
  }

  private SouvenireType recognizeType(Souvenir souvenir) {
    return SouvenireType.JEWELLERY;
  }
}
