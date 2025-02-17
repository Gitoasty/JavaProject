package src.javaproject.exceptions;

/**
 * Custom value exception to be used when non-positive value is passed for salary of similar positive-only parameter
 */
public class PasswordException extends IllegalArgumentException {

    /**
     * Constructs a new value exception with the specified message
     *
     * @param message the message to use for this exception, may be null
     */
    public PasswordException(String message) {
        super(message);
    }
}
