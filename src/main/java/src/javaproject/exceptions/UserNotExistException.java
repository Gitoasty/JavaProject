package src.javaproject.exceptions;

import java.sql.SQLException;

/**
 * Custom Exception to be used when accessing database which cannot be located
 */
public class UserNotExistException extends SQLException {

    /**
     * Constructs a new database exception with the specified message
     * @param message the message to be used for this exception, may be null
     */
    public UserNotExistException(String message) {
        super(message);
    }
}
