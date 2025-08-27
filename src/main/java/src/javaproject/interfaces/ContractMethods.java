package src.javaproject.interfaces;

import org.slf4j.Logger;
import src.javaproject.classes.Contract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ContractMethods {

    static List<Contract> getContracts(Logger logger, String contract) {
        List<Contract> outList = new ArrayList<>();
        String query;

        if (contract.isEmpty()) {
            query = "SELECT id, start, end, salary, companyId FROM contracts";
        } else {
            query = STR."SELECT id, start, end, salary, companyId FROM contracts WHERE id LIKE \"\{contract}%\"";
        }

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                Contract tempContract = new Contract(
                        results.getInt("id"),
                        results.getDate("start").toLocalDate(),
                        results.getDate("end").toLocalDate(),
                        results.getInt("salary"),
                        results.getInt("companyId")
                );
                outList.add(tempContract);
            }

            return outList;
        } catch (SQLException _) {
            logger.error("Problem with getting projects");
        }
        return new ArrayList<>();
    }
}
