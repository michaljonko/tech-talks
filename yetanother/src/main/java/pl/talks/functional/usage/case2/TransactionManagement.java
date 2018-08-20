package pl.talks.functional.usage.case2;

import org.springframework.transaction.support.TransactionTemplate;

public class TransactionManagement {

  TransactionTemplate transactionTemplate;

  public void howYouCanManageTransactionViaFunctionalWay() {
    transactionTemplate.execute(transactionStatus -> {
      // some logic here
      thisLogicInvokeInNewTransaction();
      // more logic
      return new SomeResult();
    });
  }

  private void thisLogicInvokeInNewTransaction() {
    transactionTemplate.execute(transactionStatus -> 42);
  }

  ;

  private class SomeResult {

  }
}
