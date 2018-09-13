package pl.org.jdd.legacy;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.org.jdd.legacy.stub.toy.Toy;
import pl.org.jdd.legacy.stub.toy.ToyCleaner;
import pl.org.jdd.legacy.stub.toy.ToyShelf;
import pl.org.jdd.legacy.stub.toy.ToyValidator;

@Slf4j
public final class ToysHandler implements Handler<Toy> {

  private final ToyCleaner cleaner;
  private final ToyShelf shelf;
  private final ToyValidator validator;
  private final MeterRegistry meterRegistry;

  ToysHandler(
      @NonNull ToyCleaner cleaner,
      @NonNull ToyShelf shelf,
      @NonNull ToyValidator validator,
      @NonNull MeterRegistry meterRegistry) {
    this.cleaner = cleaner;
    this.shelf = shelf;
    this.validator = validator;
    this.meterRegistry = meterRegistry;
  }

  public static ToysHandler create() {
    return new ToysHandler(new ToyCleaner(), new ToyShelf(), new ToyValidator(),
        Metrics.globalRegistry);
  }

  @Override
  public void handleSouvenir(Toy toy) {
    if (validator.isNotBroken(toy)) {
      meterRegistry.counter("toy.counter").increment();
      ToyShelf.Position positionOnShelf = getPositionOnShelf(toy);
      Toy cleanToy = cleaner.clean(toy);
      shelf.put(positionOnShelf, cleanToy);
    }
  }

  private ToyShelf.Position getPositionOnShelf(Toy toy) {
    if (validator.isAppropriateForBaby(toy)) {
      return ToyShelf.Position.DOWN;
    }
    return ToyShelf.Position.UP;
  }
}
