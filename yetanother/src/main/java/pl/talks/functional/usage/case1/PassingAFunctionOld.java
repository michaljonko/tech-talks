package pl.talks.functional.usage.case1;

public class PassingAFunctionOld {

  private UserActivity userActivity;
  private MessageService messageService;

  public void audit() {
    auditCoffee();
    auditWorkTime();
    // ...
  }

  void auditCoffee() {
    // Some logic to check whether activity regarding coffee had a place
    // if yes then we are sending message
    AuditMessage coffeeActivity = new AuditMessage() {
    };
    MessageService.INSTANCE.sendMessage(coffeeActivity);
  }

  void auditWorkTime() {
    // Some logic to check whether activity regarding work time
    // if yes then we are sending message
    AuditMessage workTimeActivity = new AuditMessage() {
    };
    MessageService.INSTANCE.sendMessage(workTimeActivity);
  }
}
