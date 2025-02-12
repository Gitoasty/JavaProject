package src.javaproject.interfaces;

import org.slf4j.Logger;
import src.javaproject.classes.Account;

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
        String query = "SELECT userTag FROM accounts WHERE userTag = ?";
        try (Connection conn = getConnection(logger);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, filter);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException _) {
            logger.warn("Error reading from table");
        }
        return false;
    }

    /**
     * Fetches given Account from database
     * @param logger logger of controller calling this method
     * @param userTag tag of the user we want
     * @return Account object with corresponding tag if it exists, otherwise null
     */
    static Account<String> fetchAccount(Logger logger, String userTag) {
        String query = "SELECT userTag, password, role FROM accounts WHERE userTag = ?";
        try (Connection conn = getConnection(logger);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userTag);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Account<>(rs.getString("userTag"), rs.getString("password"), rs.getString("role"));
            }
        } catch (SQLException _) {
            logger.warn("Error reading from table");
        }
        return new Account<>("","","");
    }
}
