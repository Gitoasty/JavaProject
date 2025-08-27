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
import src.javaproject.classes.Company;
import src.javaproject.interfaces.CompanyMethods;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CompanySearchController implements Initializable {

    @FXML
    private BorderPane topPane;
    @FXML
    private TextField searchTarget;
    @FXML
    private TableView<Company> table;
    @FXML
    private TableColumn<Company, String> companyId;
    @FXML
    private TableColumn<Company, String> companyName;
    @FXML
    private TableColumn<Company, String> workers;
    private static final Logger logger = LoggerFactory.getLogger(CompanySearchController.class);

    public void searchCompanies() {
        List<Company> companies = CompanyMethods.getCompanies(logger, searchTarget.getText());

        table.getItems().clear();
        companyId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        companyName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        workers.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWorkers().toString()));

        ObservableList<Company> observableCompanies = FXCollections.observableArrayList(companies);
        table.setItems(observableCompanies);
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
                    searchCompanies();
                }
            });
        } catch (IOException | NullPointerException _) {
            logger.warn("Issue setting MenuBar to top of screen");
        }

        ObservableList<TableColumn<Company, ?>> columns = table.getColumns();
        for (TableColumn<Company, ?> column : columns) {
            column.prefWidthProperty().bind(table.widthProperty().multiply(0.45));
        }
        columns.getFirst().prefWidthProperty().bind(table.widthProperty().multiply(0.1));
    }
}
