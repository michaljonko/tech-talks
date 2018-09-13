package pl.org.jdd.chain;

import io.micrometer.core.instrument.Metrics;
import io.vavr.control.Option;
import org.junit.Test;
import pl.org.jdd.chain.functions.PutJewelleryToTreasuryFunction;
import pl.org.jdd.chain.functions.ReportJewelleryFunction;
import pl.org.jdd.chain.functions.ValidateJewelleryFunction;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryPacker;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;

public class JewelleryHandlerTest {

  private final JewelleryHandler handler = new JewelleryHandler(
      new ValidateJewelleryFunction(new JewelleryValidator(), Metrics.globalRegistry),
      new ReportJewelleryFunction(Metrics.globalRegistry),
      new PutJewelleryToTreasuryFunction(new JewelleryPacker(), new Treasury())
  );

  @Test
  public void topazIsNotValidSouvenirForTola() throws Exception {
    Option<Location> location = handler.handleSouvenir(new Jewellery("topaz"));

    assert location.isEmpty();
  }

  @Test
  public void goldIsPackedInSafe() throws Exception {
    Location expectedLocation = new Location("safe behind the picture");

    Option<Location> location = handler.handleSouvenir(new Jewellery("gold"));

    assert location.get().equals(expectedLocation);
  }
}