package pl.org.jdd.chain;

import io.vavr.Function1;
import io.vavr.control.Option;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.chain.tool.ChainInvoker;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.option.Handler;

@Slf4j
public final class JewelleryHandler implements Handler<Jewellery, Location> {

  private final Function1<Jewellery, Option<Jewellery>> validateFunction;
  private final Function1<Option<Jewellery>, Option<Jewellery>> reportFunction;
  private final Function1<Option<Jewellery>, Option<Location>> putToTreasuryFunction;

  public JewelleryHandler(
      @NonNull Function1<Jewellery, Option<Jewellery>> validateFunction,
      @NonNull Function1<Option<Jewellery>, Option<Jewellery>> reportFunction,
      @NonNull Function1<Option<Jewellery>, Option<Location>> putToTreasuryFunction) {
    this.validateFunction = validateFunction;
    this.reportFunction = reportFunction;
    this.putToTreasuryFunction = putToTreasuryFunction;
  }

  @Override
  public Option<Location> handleSouvenir(Jewellery jewellery) {
    return ChainInvoker.invoke(jewellery, validateFunction, reportFunction, putToTreasuryFunction);
  }
}
