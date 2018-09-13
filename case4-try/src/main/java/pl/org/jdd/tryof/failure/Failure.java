package pl.org.jdd.tryof.failure;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public final class Failure {

  private Set<Constraint> constraints;

  public Failure(Constraint constraint) {
    this(HashSet.of(constraint));
  }

  public static Failure create(ConstraintTypes... constraintTypes) {
    return new Failure(HashSet.of(constraintTypes).map(Constraint::new));
  }

  public static Failure create(Constraint... constraints) {
    return new Failure(HashSet.of(constraints));
  }

  public String getMessages() {
    return constraints.map(Constraint::getMessageWithErrorCode).reduceLeft(String::concat);
  }
}
