package src.javaproject.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.javaproject.classes.ProjectAssignment;
import src.javaproject.interfaces.ProjectMethods;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProjectSearchController implements Initializable {

    @FXML
    private BorderPane topPane;
    @FXML
    private TextField searchTarget;
    @FXML
    private TableView<ProjectAssignment> table;
    @FXML
    private TableColumn<ProjectAssignment, String> projectId;
    @FXML
    private TableColumn<ProjectAssignment, String> projectName;
    @FXML
    private TableColumn<ProjectAssignment, String> estimatedTime;
    @FXML
    private TableColumn<ProjectAssignment, String> tasks;
    @FXML
    private TableColumn<ProjectAssignment, String> workers;
    private static final Logger logger = LoggerFactory.getLogger(ProjectSearchController.class);

    public void searchProjects() {
        List<ProjectAssignment> projects = ProjectMethods.getProjects(logger, searchTarget.getText());

        table.getItems().clear();
        projectId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idGetter().toString()));
        projectName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nameGetter()));
        estimatedTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().timeGetter().toString()));
        tasks.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().taskGetter().toString()));
        workers.setCellValueFactory(cellData -> {
            String temp;
            if (!cellData.getValue().getWorkers().isEmpty()) {
                temp = cellData.getValue().getWorkers().toString();
            } else {
                temp = "";
            }
            return new SimpleStringProperty(temp);
        });

        ObservableList<ProjectAssignment> observableProjects = FXCollections.observableArrayList(projects);
        table.setItems(observableProjects);
    }

    /**
     * Adds MenuBar and sets table column widths on load
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            MenuBar menuBar = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/src/javaproject/AdminMenuBar.fxml")));
            topPane.setTop(menuBar);

            searchTarget.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    searchProjects();
                }
            });
        } catch (IOException | NullPointerException _) {
            logger.warn("Issue setting MenuBar to top of screen");
        }

        ObservableList<TableColumn<ProjectAssignment, ?>> columns = table.getColumns();
        for (TableColumn<ProjectAssignment, ?> column : columns) {
            column.prefWidthProperty().bind(table.widthProperty().multiply(0.225));
        }
        columns.getFirst().prefWidthProperty().bind(table.widthProperty().multiply(0.1));
    }
}
