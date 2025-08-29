package src.javaproject.interfaces;

import org.slf4j.Logger;
import src.javaproject.classes.Contract;

import java.sql.*;
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
                Date start = results.getDate("start");
                Date end = results.getDate("end");
                assert start != null;
                assert end != null;
                Contract tempContract = new Contract(
                        results.getInt("id"),
                        start.toLocalDate(),
                        end.toLocalDate(),
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

    static Contract getContract(Logger logger, String contract) {
        String query = "SELECT * FROM contracts WHERE id = ?";

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, contract);
            ResultSet results = stmt.executeQuery();

            if (results.next()) {
                Integer id = results.getInt("id");
                Date start = results.getDate("start");
                Date end = results.getDate("end");
                Integer salary = results.getInt("salary");
                Integer companyId = results.getInt("companyId");

                return new Contract(id, start.toLocalDate(), end.toLocalDate(), salary, companyId);
            }

        } catch (SQLException _) {
            logger.error("Problem with getting projects");
        }
        return null;
    }
}
