package src.javaproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CompanyManageController implements Initializable {

    @FXML
    private BorderPane topPane;
    @FXML
    private ListView<String> theList;
    @FXML
    private ListView<String> workerList;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button backButton;
    private static final Logger logger = LoggerFactory.getLogger(CompanyManageController.class);

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

        theList.getItems().addAll("auvgieuba", "kavbuia", "oaubviub", "oavuabivuab");
        workerList.getItems().addAll("auvgieuba", "kavbuia", "oaubviub", "oavuabivuab");
    }
}
