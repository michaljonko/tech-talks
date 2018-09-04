package pl.org.jdd.tryof.failure;

import lombok.NonNull;

public abstract class SomethingWrong {

  private final String message;

  SomethingWrong(String message) {
    this.message = message;
  }

  public static SomethingWrong create(@NonNull String message) {
    return new SomethingWrong(message) {
    };
  }

  public String getMessage() {
    return message;
  }
}
