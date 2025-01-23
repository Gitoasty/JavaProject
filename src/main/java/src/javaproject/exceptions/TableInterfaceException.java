package src.javaproject.exceptions;


import java.sql.SQLException;

/**
 * Custom SQL exception to be used if there is an error when interfacing with a SQL table
 */
public class TableInterfaceException extends SQLException {

    /**
     * Constructs a new SQL table exception with the specified message
     * @param message the message to be used for this exception, may be null
     */
    public TableInterfaceException(String message) {
        super(message);
    }
}
