package pl.talks.functional.usage.case2;

import org.springframework.transaction.support.TransactionTemplate;

public class TransactionManagement {

    TransactionTemplate transactionTemplate;

    public void howYouCanManageTransactionViaFunctionalWay() {
        transactionTemplate.execute(transactionStatus -> {
            // some logic here
            thisLogicIsInvokeInNewTransaction();
            // more logic
            return new SomeResult();
        });
    }

    private void thisLogicIsInvokeInNewTransaction() {
        transactionTemplate.execute(transactionStatus -> 42);
    }

    private class SomeResult {
    }
}
