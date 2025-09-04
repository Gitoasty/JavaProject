package src.javaproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.javaproject.classes.ProjectAssignment;
import src.javaproject.classes.Worker;
import src.javaproject.interfaces.DatabaseUtilities;
import src.javaproject.interfaces.ProjectMethods;
import src.javaproject.interfaces.WorkerMethods;
import src.javaproject.interfaces.WorkerProjectMethods;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

public class UserMenuController implements Initializable {
    @FXML
    private Label welcomeLabel;
    @FXML
    private ListView<String> projectList;
    @FXML
    private Label experienceLabel;
    private Map<String, List<ProjectAssignment>> projects;
    private final String tableName = "workerProjects";

    public void updateList() {
        String user = welcomeLabel.getText().replace("Welcome, ", "").replace("!", "");

        projectList.getItems().clear();
        projects = ProjectMethods.getProjectsForWorker(logger, user);

        if (projects != null && !projects.isEmpty()) {
            projectList.getItems().addAll(projects.get(user)
                    .stream()
                    .map(p -> STR."\{p.nameGetter()} - \{p.taskGetter()}")
                    .toList());
        }
    }
    private final Logger logger = LoggerFactory.getLogger(UserMenuController.class);

    public void addProject() {
        String user = welcomeLabel.getText().replace("Welcome, ", "").replace("!", "");
        Map<String, String> entry = WorkerProjectMethods.getWorkerProjects(logger, user);

        if (!entry.containsKey(user) || projectList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        List<String> projects = new ArrayList<>(Arrays.asList(entry.get(user).split(" \" ")));
        projects.add(projectList.getSelectionModel().getSelectedItem().split(" - ")[0]);

        String sql = STR."UPDATE \{tableName} SET projects = ? WHERE id = ?";

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, String.join(" \" ", projects));
            pstmt.setString(2, user);

            pstmt.executeUpdate();

            Worker worker = WorkerMethods.getWorker(logger, user);
            if (worker != null)  {
                sql = "UPDATE workers SET experience = ? WHERE id = ?";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, worker.experienceGetter() + 1);
                    stmt.setString(2, user);

                    stmt.executeUpdate();
                }
                experienceLabel.setText(STR."Experience: \{worker.experienceGetter() + 1}");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        updateList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Path path = Path.of("G:\\learning_java\\JavaProject\\src\\main\\resources\\data\\tempCred.txt");
        List<String> temp;
        String user = null;
        try (Stream<String> linesStream = Files.lines(path)) {
            temp = linesStream.toList();
            user = temp.getFirst();
            welcomeLabel.setText(STR."Welcome, \{user}!");
            projects = ProjectMethods.getProjectsForWorker(logger, user);

            Map<String, String> entry = WorkerProjectMethods.getWorkerProjects(logger, user);

            if (!entry.containsKey(user)) {
                String sql = STR."UPDATE \{tableName} SET projects = ? WHERE id = ?";
                try (Connection conn = DatabaseUtilities.getConnection(logger);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, "");
                    pstmt.setString(2, user);

                    pstmt.executeUpdate();
                }
            }


            Files.delete(path);

            Worker worker = WorkerMethods.getWorker(logger, user);
            if (worker != null)  {
                experienceLabel.setText(STR."Experience: \{worker.experienceGetter()}");
            }
        } catch (IOException e) {
            logger.error("Could not load user");
        } catch (SQLException e) {
            logger.error("Could not create row for projects");
        }

        if (!projects.isEmpty()) {
            projectList.getItems().addAll(projects.get(user)
                    .stream()
                    .map(p -> STR."\{p.nameGetter()} - \{p.taskGetter()}")
                    .toList());
        }

        projectList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}
