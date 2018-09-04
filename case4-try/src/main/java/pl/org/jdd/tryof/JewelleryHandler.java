package pl.org.jdd.tryof;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.allOf;
import static io.vavr.Predicates.instanceOf;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.function.Consumer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.either.exception.NotValuableSouvenirException;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryPacker;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;
import pl.org.jdd.tryof.failure.NotValuableItem;
import pl.org.jdd.tryof.failure.SomethingWrong;
import pl.org.jdd.tryof.functions.PutJewelleryToTreasuryFunction;
import pl.org.jdd.tryof.functions.ReportJewelleryConsumer;
import pl.org.jdd.tryof.functions.ValidateJewelleryFunction;

@Slf4j
public final class JewelleryHandler implements Handler<Jewellery, Location> {

  private final Function1<Jewellery, Jewellery> validateFunction;
  private final Consumer<Jewellery> reportFunction;
  private final Function1<Jewellery, Location> putToTreasuryFunction;

  public JewelleryHandler(
      @NonNull JewelleryValidator validator,
      @NonNull JewelleryPacker packer,
      @NonNull Treasury treasury,
      @NonNull MeterRegistry meterRegistry) {
    this.validateFunction = new ValidateJewelleryFunction(validator, meterRegistry);
    this.reportFunction = new ReportJewelleryConsumer(meterRegistry);
    this.putToTreasuryFunction = new PutJewelleryToTreasuryFunction(packer, treasury);
  }

  @Override
  public Either<? extends SomethingWrong, Location> handleSouvenir(Jewellery jewellery) {
    return Try.of(() -> validateFunction.apply(jewellery))
        .andThen(reportFunction)
        .map(putToTreasuryFunction)
        .onFailure(throwable -> log.debug("Failure!", throwable))
        .toEither()
        .mapLeft(throwable -> Match(throwable).of(
            Case($(instanceOf(NotValuableSouvenirException.class)),
                () -> new NotValuableItem(jewellery)),
            Case($(allOf(instanceOf(NullPointerException.class), e -> e.getMessage().contains("jDD"))),
                () -> SomethingWrong.create("Some input value is null!")),
            Case($(),
                () -> SomethingWrong.create("jDD sucks!"))
        ));
  }
}
