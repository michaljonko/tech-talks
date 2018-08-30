package pl.org.jdd.chain;

import io.vavr.Function1;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.chain.functions.PutSouvenirToTreasuryFunction;
import pl.org.jdd.chain.functions.ReportSouvenirFunction;
import pl.org.jdd.chain.functions.ValidateDiamondFunction;
import pl.org.jdd.chain.tool.ChainInvoker;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.diamond.Diamond;
import pl.org.jdd.option.Handler;

@Slf4j
public final class DiamondsHandler implements Handler<Diamond, Location> {

  private final PutSouvenirToTreasuryFunction putSouvenirToTreasuryFunction;
  private final ReportSouvenirFunction reportSouvenirFunction;
  private final ValidateDiamondFunction validateDiamondFunction;

  public DiamondsHandler(
          PutSouvenirToTreasuryFunction putSouvenirToTreasuryFunction,
          ReportSouvenirFunction reportSouvenirFunction,
          ValidateDiamondFunction validateDiamondFunction) {
    this.putSouvenirToTreasuryFunction = putSouvenirToTreasuryFunction;
    this.reportSouvenirFunction = reportSouvenirFunction;
    this.validateDiamondFunction = validateDiamondFunction;
  }

  @Override
  public Option<Location> handleSouvenir(Diamond diamond) {
    return ChainInvoker
            .invokeChain(diamond,
                         Function1.of(validateDiamondFunction),
                         Function1.of(reportSouvenirFunction),
                         Function1.of(putSouvenirToTreasuryFunction));
  }
}
