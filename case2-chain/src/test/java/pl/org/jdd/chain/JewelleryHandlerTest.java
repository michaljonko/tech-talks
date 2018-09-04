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
      new PutJewelleryToTreasuryFunction(new JewelleryPacker(), new Treasury()),
      new ReportJewelleryFunction(Metrics.globalRegistry));

  @Test
  public void topazIsNotValidSouvenirForTola() throws Exception {
    Option<Location> souvenir = handler.handleSouvenir(new Jewellery("topaz"));

    assert souvenir.isEmpty();
  }

  @Test
  public void diamondsAreValidSouvenirForTola() throws Exception {
    Option<Location> souvenir = handler.handleSouvenir(new Jewellery("diamonds"));

    assert !souvenir.isEmpty();
  }
}