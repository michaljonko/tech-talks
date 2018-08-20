package pl.talks.functional.usage.case2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

public class TransactionManagementOld {

    class Service1 {
        private Service2 service2;
        private Service3 service3;
        private Service4 service4;

        @Transactional
        public void startTransaction() {
            // some logic
            service2.continueTransactionAndDoSomeLogic();
            service3.openNewTransaction();
            service4.continueTransactionAndDoSomeBigLogic();
        }
    }

    class Service2 {
        public void continueTransactionAndDoSomeLogic() {
        }
    }

    class Service3 {
        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public void openNewTransaction() {
        }
    }

    class Service4 {
        private Service4 selfReference;

        @Transactional
        public void continueTransactionAndDoSomeBigLogic() {
            selfReference.doThisLogicInNewTransaction();
        }

        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public void doThisLogicInNewTransaction() {
            //do in new transaction
        }
    }
}
