package src.javaproject.exceptions;

/**
 * Custom SQL exception to be used if there is a null value when handling objects
 */
public class NullObjectException extends Exception {

    /**
     * Constructs a new Exception with the message
     * @param message the message to be used for this exception, may be null
     */
    public NullObjectException(String message) {
        super(message);
    }
}
