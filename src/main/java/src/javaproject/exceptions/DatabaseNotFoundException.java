package src.javaproject.exceptions;

import java.io.FileNotFoundException;

/**
 * Custom Exception to be used when accessing database which cannot be located
 */
public class DatabaseNotFoundException extends FileNotFoundException {

    /**
     * Constructs a new database exception with the specified message
     * @param message the message to be used for this exception, may be null
     */
    public DatabaseNotFoundException(String message) {
        super(message);
    }
}
