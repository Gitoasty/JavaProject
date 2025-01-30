package src.javaproject.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Interface for storing database related methods
 */
public interface TableCreator {

    Logger logger = LoggerFactory.getLogger(TableCreator.class);

    /**
     * Creates an SQL database if one does not already exist
     */
    public static void createDatabase() {
        try {
            final String db_file = "src/main/resources/data/database.properties";
            Properties prop = new Properties();
            prop.load(new FileReader(db_file));
            String url = prop.getProperty("databaseURL");
            Connection conn = DriverManager.getConnection(url);
            conn.close();
        } catch (SQLException e) {
            logger.warn("Error creating table");
        } catch (IOException e) {
            logger.warn("Error opening or reading database.properties");
        }
    }

    public static void accountData() {

    }
}
