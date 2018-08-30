package pl.org.jdd.legacy;

import org.junit.Test;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;

public class SouvenirDispatcherTest {

  private final SouvenirDispatcher dispatcher = new SouvenirDispatcher();

  @Test
  public void passTopazToJewelleryHandler() throws Exception {
    dispatcher.dispatch(new Jewellery("topaz"));
  }

  @Test
  public void passDiamondsToJewelleryHandler() throws Exception {
    dispatcher.dispatch(new Jewellery("diamonds"));
  }
}