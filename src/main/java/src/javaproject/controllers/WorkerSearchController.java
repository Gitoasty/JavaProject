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
import src.javaproject.classes.Worker;
import src.javaproject.enums.WorkerTypes;
import src.javaproject.interfaces.WorkerMethods;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * <h1>
 *     Controller class for monitoring workers as admin
 * </h1>
 */
public final class WorkerSearchController implements Initializable, WorkerMethods {

    @FXML
    private BorderPane topPane;
    @FXML
    private TextField searchTarget;
    @FXML
    private TableView<Worker> table;
    @FXML
    private TableColumn<Worker, String> workerTag;
    @FXML
    private TableColumn<Worker, String> fName;
    @FXML
    private TableColumn<Worker, String> lName;
    @FXML
    private TableColumn<Worker, String> type;
    @FXML
    private TableColumn<Worker, String> experience;
    private static final Logger logger = LoggerFactory.getLogger(WorkerSearchController.class);

    public void searchWorkers() {
        List<Worker> workers = WorkerMethods.getWorkers(logger, searchTarget.getText());

        table.getItems().clear();
        workerTag.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idGetter()));
        fName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().firstNameGetter()));
        lName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().lastNameGetter()));
        type.setCellValueFactory(cellData -> {
            if (cellData.getValue().getType().equals(WorkerTypes.FREELANCE)) {
                return new SimpleStringProperty("Freelance");
            } else if (cellData.getValue().getType().equals(WorkerTypes.STAYING)) {
                return new SimpleStringProperty("Contract");
            } else {
                return new SimpleStringProperty("Waiting");
            }
        });
        experience.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().experienceGetter().toString()));

        ObservableList<Worker> observableWorkers = FXCollections.observableArrayList(workers);
        table.setItems(observableWorkers);
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
                    searchWorkers();
                }
            });
        } catch (IOException | NullPointerException _) {
            logger.warn("Issue setting MenuBar to top of screen");
        }


        ObservableList<TableColumn<Worker, ?>> columns = table.getColumns();
        for (TableColumn<Worker, ?> column : columns) {
            column.prefWidthProperty().bind(table.widthProperty().multiply(0.225));
        }
        columns.getFirst().prefWidthProperty().bind(table.widthProperty().multiply(0.1));
    }
}
