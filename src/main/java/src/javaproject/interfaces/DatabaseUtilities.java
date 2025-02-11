package src.javaproject.interfaces;

import org.slf4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Interface containing utility methods for the database
 */
public interface DatabaseUtilities {

    /**
     * Returns Connection to database, creates it if one doesn't exist
     * @param logger logger of controller calling this method
     * @return Connection to database
     */
    static Connection getConnection(Logger logger) {
        Connection conn = null;
        try {
            final String db_file = "src/main/resources/data/database.properties";
            Properties prop = new Properties();
            prop.load(new FileReader(db_file));
            String url = prop.getProperty("databaseURL");
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.warn("Error creating table");
        } catch (IOException e) {
            logger.warn("Error opening or reading database.properties");
        }
        return conn;
    }

    /**
     * Checks whether user tag already exists
     * @param logger logger of controller calling this method
     * @param filter String provided as potential user tag
     * @return true if user tag exists, false if it does not exist
     */
    static boolean accountExists(Logger logger, String filter) {
        try {
            Connection conn = getConnection(logger);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(STR."SELECT * FROM accounts WHERE userTag = '%\{filter}%'");
            if (rs.next()) {
                return true;
            }
        } catch (SQLException _) {
            logger.warn("Error reading from table");
        }
        return false;
    }
}
