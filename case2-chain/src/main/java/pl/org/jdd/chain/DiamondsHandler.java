package pl.org.jdd.chain;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.collection.List;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.Handler;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.diamond.Diamond;
import pl.org.jdd.legacy.stub.diamond.DiamondMessageConverter;
import pl.org.jdd.legacy.stub.diamond.DiamondValidator;

@Slf4j
public final class DiamondsHandler implements Handler<Diamond> {

  private final DiamondMessageConverter converter;
  private final Treasury treasury;
  private final DiamondValidator validator;
  private final MeterRegistry meterRegistry;

  public DiamondsHandler(
      Treasury treasury,
      DiamondMessageConverter converter,
      DiamondValidator validator,
      MeterRegistry meterRegistry) {
    this.treasury = treasury;
    this.converter = converter;
    this.meterRegistry = meterRegistry;
    this.validator = validator;
  }

  public static void main(String[] args) {
    new DiamondsHandler(new Treasury(), new DiamondMessageConverter(), new DiamondValidator(), null)
        .handleSouvenir(null);
  }

  @Override
  public void handleSouvenir(Diamond diamond) {
    List<Function1<Diamond, Diamond>> functions = List.of(
        d -> {
          System.out.println("1nd function");
          return d;
        },
        d -> {
          System.out.println("2nd function");
          return d;
        },
        d -> {
          System.out.println("function to make a treasure from diamond");
          return d;
        });
    chain(new Diamond(), functions);
  }

  private Diamond chain(Diamond diamond, List<Function1<Diamond, Diamond>> functions) {
    return functions.reduceLeft(Function1::andThen).apply(diamond);
  }
}
