package src.javaproject.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.javaproject.classes.Contract;
import src.javaproject.classes.Project;
import src.javaproject.classes.ProjectAssignment;
import src.javaproject.classes.SerialWriter;
import src.javaproject.interfaces.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectManageController implements Initializable {

    @FXML
    private BorderPane topPane;
    @FXML
    private ListView<String> theList;
    @FXML
    private TextField name;
    @FXML
    private TextField duration;
    @FXML
    private TextField description;
    @FXML
    private ListView<String> workerList;
    private static final Logger logger = LoggerFactory.getLogger(ProjectManageController.class);
    private final String tableName = "projects";

    public void updateList() {
        theList.getItems().clear();
        String column = "name";
        List<String> values = DatabaseUtilities.getColumnFromTable(logger, column, tableName);

        assert values != null;
        theList.getItems().addAll(values);
    }

    public void addProject()  {
        String tasks = description.getText().replace(", ", ",");

        String sql = STR."INSERT INTO \{tableName} (name, estimatedTime, tasks, workers) VALUES(?, ?, ?, ?)";
        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name.getText());
            pstmt.setInt(2, Integer.parseInt(duration.getText()));
            pstmt.setString(3, tasks);
            pstmt.setString(4, String.join(",", workerList.getSelectionModel().getSelectedItems()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Project data = ProjectMethods.getProjects(logger, "").getLast();

        updateList();
        SerializationUtilities.serialize(new SerialWriter<>("Admin", data));
    }

    public void editProject() {
        Project editingProject = ProjectMethods.getProject(logger, theList.getSelectionModel().getSelectedItem());

        if (editingProject == null) {
            return;
        }

        String sql = STR."UPDATE \{tableName} SET name = ?, estimatedTime = ?, tasks = ?, workers = ? WHERE id = ?";

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name.getText());
            pstmt.setInt(2, Integer.parseInt(duration.getText()));
            pstmt.setString(3, description.getText());
            pstmt.setString(4, String.join(",", workerList.getSelectionModel().getSelectedItems()));
            pstmt.setInt(5, editingProject.idGetter());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        updateList();
        SerializationUtilities.serialize(new SerialWriter<>("Admin", editingProject));
    }

    public void deleteProject()  {
        Project deletingProject = ProjectMethods.getProject(logger, theList.getSelectionModel().getSelectedItem());

        if (deletingProject == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/temp_backgrounds/deleteAlert.jpg")))));
        alert.setTitle("Deletion confirmation");
        alert.setHeaderText(STR."You're about to delete entry: \{deletingProject.nameGetter()}");
        alert.setContentText("Are you ok with this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()){
            if (result.get() == ButtonType.OK) {
                String sql = STR."DELETE FROM \{tableName} WHERE id = ?";
                System.out.println(STR."Deleting project: \{deletingProject.idGetter()} - \{deletingProject.nameGetter()}");
                try (Connection conn = DatabaseUtilities.getConnection(logger);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, deletingProject.idGetter());

                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                updateList();
                SerializationUtilities.serialize(new SerialWriter<>("Admin", deletingProject));
            }
        }
    }

    public void switchScreen(ActionEvent event) {
        String target = "/src/javaproject/Admin_Menu";

        ScreenUtilities.switchScreen(logger, target, (Node) event.getSource());
    }

    /**
     * Sets the MenuBar on top
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            MenuBar menuBar = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/src/javaproject/AdminMenuBar.fxml")));
            topPane.setTop(menuBar);
        } catch (IOException | NullPointerException _) {
            logger.warn("Issue setting MenuBar to top of screen");
        }

        workerList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        List<ProjectAssignment> projects = ProjectMethods.getProjects(logger, "");
        List<String> workers = DatabaseUtilities.getColumnFromTable(logger, "id", "workers");
        assert projects != null;
        assert workers != null;

        theList.getItems().addAll(projects.stream().map(ProjectAssignment::nameGetter).map(String::valueOf).toList());
        workerList.getItems().addAll(workers);
    }
}
