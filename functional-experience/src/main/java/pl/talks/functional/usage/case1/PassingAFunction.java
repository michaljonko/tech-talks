package pl.talks.functional.usage.case1;

import io.vavr.Function1;

import java.util.Optional;

public class PassingAFunction {

    static final Function1<UserActivity, Optional<AuditMessage>> COFFEE_ACTIVITY_MESSAGE_PRODUCER = (UserActivity userActivity) -> {
        // some checks
        return Optional.of(new AuditMessage() {
        });
    };

    static final Function1<UserActivity, Optional<AuditMessage>> WORK_TIME_MESSAGE_PRODUCER = (UserActivity userActivity) -> {
        // some checks
        return Optional.of(new AuditMessage() {
        });
    };

    public static void audit(UserActivity userActivity, Function1<UserActivity, Optional<AuditMessage>>... producers) {
        for (Function1<UserActivity, Optional<AuditMessage>> producer : producers) {
            producer.apply(userActivity)
                    .ifPresent(auditMessage -> MessageService.INSTANCE.sendMessage(auditMessage));
        }
    }

    public void audit() {
        UserActivity userActivity = new UserActivity();
        audit(userActivity, COFFEE_ACTIVITY_MESSAGE_PRODUCER, WORK_TIME_MESSAGE_PRODUCER);
    }

}
