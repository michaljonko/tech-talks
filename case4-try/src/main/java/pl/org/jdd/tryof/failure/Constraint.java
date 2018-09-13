package pl.org.jdd.tryof.failure;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public final class Constraint {

  private String message;
  private int errorCode;

  public Constraint(ConstraintTypes constraint) {
    this(constraint.getMessage(), constraint.getErrorCode());
  }

  public String getMessageWithErrorCode() {
    return "Error " + errorCode + ": " + message;
  }
}
