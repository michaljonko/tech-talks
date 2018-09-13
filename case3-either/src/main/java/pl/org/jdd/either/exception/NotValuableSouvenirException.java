package pl.org.jdd.either.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false, of = "detailMessage")
public final class NotValuableSouvenirException extends RuntimeException {

  public NotValuableSouvenirException(String message) {
    super(message);
  }
}
