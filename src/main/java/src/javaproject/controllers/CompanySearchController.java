package src.javaproject.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CompanySearchController implements Initializable {

    @FXML
    private BorderPane topPane;
    @FXML
    private TextField target;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<String> table;
    private static final Logger logger = LoggerFactory.getLogger(CompanySearchController.class);

    /**
     * Adds MenuBar and sets table column widths on load
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            MenuBar menuBar = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/src/javaproject/AdminMenuBar.fxml")));
            topPane.setTop(menuBar);
        } catch (IOException | NullPointerException _) {
            logger.warn("Issue setting MenuBar to top of screen");
        }

        ObservableList<TableColumn<String, ?>> columns = table.getColumns();
        for (TableColumn<String, ?> column : columns) {
            column.prefWidthProperty().bind(table.widthProperty().multiply(0.45));
        }
        columns.getFirst().prefWidthProperty().bind(table.widthProperty().multiply(0.1));
    }
}
