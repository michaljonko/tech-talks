package pl.org.jdd.either;

import io.micrometer.core.instrument.MeterRegistry;
import io.vavr.Function1;
import io.vavr.control.Either;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.either.functions.PutJewelleryToTreasuryFunction;
import pl.org.jdd.either.functions.ReportJewelleryFunction;
import pl.org.jdd.either.functions.ValidateJewelleryFunction;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryPacker;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;

@Slf4j
public final class JewelleryHandler implements Handler<Jewellery, Location> {

  private final Function1<Jewellery, Either<Throwable, Jewellery>> validateFunction;
  private final Function1<Jewellery, Either<Throwable, Jewellery>> reportFunction;
  private final Function1<Jewellery, Either<Throwable, Location>> putToTreasuryFunction;

  public JewelleryHandler(
      @NonNull JewelleryValidator validator,
      @NonNull JewelleryPacker packer,
      @NonNull Treasury treasury,
      @NonNull MeterRegistry meterRegistry) {
    this.validateFunction = new ValidateJewelleryFunction(validator, meterRegistry);
    this.reportFunction = new ReportJewelleryFunction(meterRegistry);
    this.putToTreasuryFunction = new PutJewelleryToTreasuryFunction(packer, treasury);
  }

  @Override
  public Either<Throwable, Location> handleSouvenir(Jewellery jewellery) {
    // TODO: 30.08.2018 taki szybki pomysl na either'a bez chintoola. jak nic nie znajdziemy to zeby bylo ladnie i czytelnie to chyba to zostawimy
    return validateFunction.apply(jewellery)
        .flatMap(reportFunction)
        .flatMap(putToTreasuryFunction);
  }
}
