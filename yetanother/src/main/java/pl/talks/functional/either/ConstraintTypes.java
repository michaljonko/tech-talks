package pl.talks.functional.either;

/**
 * Here we are defining each error message in app.
 */
public enum ConstraintTypes {

    CANT_FIND_CONFIG_FILE(100, "Cannot find config file"),
    EXTERNAL_SYSTEM_X_CONNECTION_PROBLEM(101, "Connection problem with connection to 'System X'. Response code: {}, Response message: {}");

    private int errorCode;
    private String messageTemplate;

    ConstraintTypes(int errorCode, String messageTemplate) {
        this.errorCode = errorCode;
        this.messageTemplate = messageTemplate;
    }

    public String formatMessage(Object... parameters) {
        return "";
//    return MessageFormatter.arrayFormat(messageTemplate, parameters).getMessage();
    }

    public Constraint buildConstraint(Object... parameters) {
        return new Constraint(this, parameters);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
