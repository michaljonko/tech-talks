package pl.org.jdd.tryof.failure;

import lombok.Getter;

@Getter
public enum ConstraintTypes {

  NOT_VALUABLE_SOUVENIR(1, "That souvenir sucks!"),
  SOMETHING_IS_NOT_DEFINED(2, "Something is not defined and it is needed by the process."),
  SOMETHING_WENT_WRONG(3, "Something went wrong and broke process."),
  JDD_FAILURE(4, "jDD");

  private int errorCode;
  private String message;

  ConstraintTypes(int errorCode, String message) {
    this.errorCode = errorCode;
    this.message = message;
  }
}
