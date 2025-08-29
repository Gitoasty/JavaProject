package src.javaproject.interfaces;

import org.slf4j.Logger;
import src.javaproject.classes.Account;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
        final String db_file = "src/main/resources/data/database.properties";
        try (FileReader fr = new FileReader(db_file)) {
            Properties prop = new Properties();
            prop.load(fr);
            String url = prop.getProperty("databaseURL");
            conn = DriverManager.getConnection(url);
        } catch (SQLException _) {
            logger.warn("Error creating table");
        } catch (IOException _) {
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
        try (Connection conn = getConnection(logger)) {
            assert conn != null;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, filter);
                ResultSet rs = stmt.executeQuery();
                return rs.next();
            }
        } catch (SQLException | NullPointerException _) {
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
        try (Connection conn = getConnection(logger)) {
            assert conn != null;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, userTag);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Account<>(rs.getString("userTag"), rs.getString("password"), rs.getString("role"));
                }
            }
        } catch (SQLException _) {
            logger.warn("Error reading from table");
        }
        return new Account<>("","","");
    }

    /**
     * for getting rows to display
     */
    static List<String> getColumnFromTable(Logger logger, String column, String table) {
        String sql = STR."SELECT * FROM \{table}";
        List<String> outData = new ArrayList<>();

        try {
            Connection conn = DatabaseUtilities.getConnection(logger);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String value = rs.getString(column);
                outData.add(value);
            }

            return outData;
        } catch (SQLException e) {
            logger.error("Problem with getting column");
        }

        return null;
    }
}
