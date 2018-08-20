package pl.talks.functional.either;

import static java.util.Objects.requireNonNull;

import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.Objects;

/**
 * Error container which associate error message and error code.
 */
public final class Constraint implements Serializable {

    private static final long serialVersionUID = 1;
    private final String message;
    private final int errorCode;

    public Constraint(String message, int errorCode) {
        this.message = requireNonNull(message, "'message' cannot be null");
        this.errorCode = errorCode;
    }

    public Constraint(ConstraintTypes constraint, Object... params) {
        this(constraint.formatMessage(params), constraint.getErrorCode());
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessageWithErrorCode() {
        return "Error " + errorCode + ": " + message;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("message", message)
                .add("errorCode", errorCode)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Constraint that = (Constraint) o;
        return errorCode == that.errorCode &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, errorCode);
    }
}
