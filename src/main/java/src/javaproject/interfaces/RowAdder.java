package src.javaproject.interfaces;


import at.favre.lib.crypto.bcrypt.BCrypt;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for adding rows into database tables
 */
public interface RowAdder {

    /**
     * Adds account data into the table
     * @param logger logger of controller calling this method
     * @param creds ArrayList of Account data
     */
    static boolean addAccount(Logger logger, ArrayList<String> creds) {
        try {
            Connection conn = DatabaseUtilities.getConnection(logger);
            String sql = "INSERT INTO " + "accounts" + " (userTag, fName, lName, password, role) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, creds.getFirst());
            pstmt.setString(2, creds.get(1));
            pstmt.setString(3, creds.get(2));
            String password = BCrypt.withDefaults().hashToString(12, creds.get(3).toCharArray());
            pstmt.setString(4, password);
            pstmt.setString(5, "user");
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException _) {
            logger.warn("Problem inserting account into table");
        }

        return false;
    }
}
