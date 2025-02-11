package src.javaproject.interfaces;

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Interface for storing database related methods
 */
public interface TableCreator {

    /**
     * Creates table for storing user account data
     * @param logger logger of controller calling this method
     */
    static void accountData(Logger logger) {
        try {
            Connection conn = DatabaseUtilities.getConnection(logger);

            Statement stat = conn.createStatement();

            String creationString = "CREATE TABLE IF NOT EXISTS " + "accounts" + "("
                    +"userTag TEXT PRIMARY KEY NOT NULL, "
                    +"fName TEXT NOT NULL, "
                    +"lName TEXT NOT NULL, "
                    +"password TEXT NOT NULL, "
                    +"role TEXT NOT NULL"
                    +")";
            stat.executeUpdate(creationString);
        } catch (SQLException _) {
            logger.warn("Problem creating account table");
        }
    }
}
