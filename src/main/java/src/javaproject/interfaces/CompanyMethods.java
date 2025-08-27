package src.javaproject.interfaces;

import org.slf4j.Logger;
import src.javaproject.classes.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public interface CompanyMethods {

    static List<Company> getCompanies(Logger logger, String company) {
        List<Company> outList = new ArrayList<>();
        String query;

        if (company.isEmpty()) {
            query = "SELECT id, name, workers FROM companies";
        } else {
            query = STR."SELECT id, name, workers FROM companies WHERE id LIKE \"\{company}%\"";
        }

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                TreeSet<String> workers = new TreeSet<>(List.of(results.getString("workers").split(",")));
                Company tempCompany = Company.builder()
                        .id(results.getInt("id"))
                        .name(results.getString("name"))
                        .workers(workers)
                        .build();
                outList.add(tempCompany);
            }

            return outList;
        } catch (SQLException _) {
            logger.error("Problem with getting projects");
        }
        return new ArrayList<>();
    }
}
