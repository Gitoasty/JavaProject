package src.javaproject.interfaces;

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public interface WorkerProjectMethods {


    static Map<String, String> getWorkerProjects(Logger logger, String worker) {
        Map<String, String> outMap = new HashMap<>();
        String sql = "SELECT projects FROM workerProjects WHERE id = ?";

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, worker);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String projects = rs.getString("projects");

                outMap.put(worker, projects);
                return outMap;
            } else {
                sql = "INSERT INTO workerProjects (id, projects) VALUES(?, ?)";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setString(1, worker);
                    stmt.setString(2, "");

                    stmt.executeUpdate();
                }
            }
        } catch (SQLException _) {
            logger.error("Could not load projects for worker");
        }

        return outMap;
    }
}
