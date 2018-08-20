package pl.talks.functional.legacy.exceptions;

import io.vavr.Function1;

public class LegacyCodeErrorHandlingOptional {

  private final ExternalCoffeService userService;

  public LegacyCodeErrorHandlingOptional(ExternalCoffeService userService) {
    this.userService = userService;
  }

  public void chooseFavouriteCoffee() {
    String userLogin = "krzysztof";
    String favouriteCoffee = Function1.lift(userService::getUserFavouriteCoffee)
        .apply(userLogin)
        .getOrElse(chooseTodaysSpecial());
  }

  private String chooseTodaysSpecial() {
    return "JAVACoffee";
  }

  interface ExternalCoffeService {

    String getUserFavouriteCoffee(String login) throws NoResultFoundRuntimeException;
  }

  private static class NoResultFoundRuntimeException extends RuntimeException {

  }
}
