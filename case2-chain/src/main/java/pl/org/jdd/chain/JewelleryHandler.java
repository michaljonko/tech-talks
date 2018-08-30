package pl.org.jdd.chain;

import io.vavr.control.Option;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.chain.functions.PutJewelleryToTreasuryFunction;
import pl.org.jdd.chain.functions.ReportJewelleryFunction;
import pl.org.jdd.chain.functions.ValidateJewelleryFunction;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.option.Handler;

@Slf4j
public final class JewelleryHandler implements Handler<Jewellery, Location> {

  private final PutJewelleryToTreasuryFunction putToTreasuryFunction;
  private final ReportJewelleryFunction reportFunction;
  private final ValidateJewelleryFunction validateFunction;

  public JewelleryHandler(
      @NonNull ValidateJewelleryFunction validateFunction,
      @NonNull PutJewelleryToTreasuryFunction putToTreasuryFunction,
      @NonNull ReportJewelleryFunction reportFunction) {
    this.putToTreasuryFunction = putToTreasuryFunction;
    this.reportFunction = reportFunction;
    this.validateFunction = validateFunction;
  }

  @Override
  public Option<Location> handleSouvenir(Jewellery jewellery) {
    // TODO: 30.08.2018 we have to think what we should show - andThen or Chain
    return validateFunction
        .andThen(reportFunction)
        .andThen(putToTreasuryFunction)
        .apply(jewellery);
//    return ChainInvoker
//        .invokeChain(jewellery,
//            validateFunction, reportFunction, putToTreasuryFunction);
  }
}
