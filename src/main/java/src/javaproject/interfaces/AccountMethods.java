package src.javaproject.interfaces;

import org.slf4j.Logger;
import src.javaproject.classes.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface AccountMethods {

    static List<Account<String>> getAccounts(Logger logger, String user) {
        List<Account<String>> outList = new ArrayList<>();
        String query;

        if (user.isEmpty()) {
            query = "SELECT userTag, password, role FROM accounts";
        } else {
            query = STR."SELECT userTag, password, role FROM accounts WHERE userTag LIKE \"\{user}%\"";
        }

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                Account<String> tempAccount = new Account<>(
                        results.getString("userTag"),
                        results.getString("password"),
                        results.getString("role")
                );
                outList.add(tempAccount);
            }

            return outList;
        } catch (SQLException | NullPointerException _) {
            logger.error("Problem with getting projects");
        }
        return new ArrayList<>();
    }
}
