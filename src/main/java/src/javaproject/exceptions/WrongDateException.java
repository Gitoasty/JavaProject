package src.javaproject.exceptions;

import java.time.DateTimeException;

/**
 * Custom Date exception to be used when inputting dates for contracts
 */
public class WrongDateException extends DateTimeException {

    /**
     * Constructs a new date-time exception with the specified message
     *
     * @param message the message to use for this exception, may be null
     */
    public WrongDateException(String message) {
        super(message);
    }
}
