package src.javaproject.interfaces;

import org.slf4j.Logger;
import src.javaproject.classes.FreelanceWorker;
import src.javaproject.classes.Worker;
import src.javaproject.controllers.LoginController;
import src.javaproject.controllers.WorkerManageController;
import src.javaproject.controllers.WorkerSearchController;
import src.javaproject.enums.WorkerTypes;
import src.javaproject.exceptions.UserNotExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains method for checking employment status
 */
public sealed interface WorkerMethods permits LoginController, WorkerSearchController, WorkerManageController {

    /**
     * Checks the user's type of employment
     * @param logger logger of controller calling this method
     * @param user userTag to be checked
     * @return type of employment, null if empty
     * @throws UserNotExistException in case userTag doesn't exist in table
     */
    default String checkStatus(Logger logger, String user) throws UserNotExistException {
        String query = "SELECT type FROM workers WHERE id = ?";
        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getString("type");
        } catch (SQLException _) {
            logger.warn("Error reading from table");
        }
        throw new UserNotExistException("User not found in database");
    }

    default List<Worker> getWorkers(Logger logger, String worker) {
        List<Worker> outList = new ArrayList<>();
        String query;

        if (worker.isEmpty()) {
            query = "SELECT id, fName, lName, type, experience FROM workers";
        } else {
            query = STR."SELECT id, fName, lName, type, experience FROM workers WHERE id LIKE \"\{worker}%\"";
        }

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                FreelanceWorker temp = FreelanceWorker.builder()
                        .id(results.getString("id"))
                        .firstName(results.getString("fName"))
                        .lastName(results.getString("lName"))
                        .workExperience(results.getInt("experience"))
                        .build();

                String test = results.getString("type");
                if (test == null) {
                    temp.type = WorkerTypes.Waiting;
                } else if (Character.isDigit(test.toCharArray()[0])) {
                    temp.type = WorkerTypes.Freelance;
                } else {
                    temp.type = WorkerTypes.Staying;
                }

                outList.add(temp);
            }

            return outList;
        } catch (SQLException _) {
            logger.error("Problem with getting workers from table");
        }
        return new ArrayList<>();
    }
}
