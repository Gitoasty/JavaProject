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
import src.javaproject.classes.Contract;
import src.javaproject.interfaces.ContractMethods;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ContractSearchController implements Initializable {

    @FXML
    private BorderPane topPane;
    @FXML
    private TextField searchTarget;
    @FXML
    private TableView<Contract> table;
    @FXML
    private TableColumn<Contract, String> contractId;
    @FXML
    private TableColumn<Contract, String> startDate;
    @FXML
    private TableColumn<Contract, String> endDate;
    @FXML
    private TableColumn<Contract, String> salary;
    @FXML
    private TableColumn<Contract, String> companyId;
    private static final Logger logger = LoggerFactory.getLogger(ContractSearchController.class);

    public void searchContracts() {
        List<Contract> contracts = ContractMethods.getContracts(logger, searchTarget.getText());

        table.getItems().clear();
        contractId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id().toString()));
        startDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().startDate().toString()));
        endDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().endDate().toString()));
        salary.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().salary().toString()));
        companyId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().companyId().toString()));

        ObservableList<Contract> observableProjects = FXCollections.observableArrayList(contracts);
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
                    searchContracts();
                }
            });
        } catch (IOException | NullPointerException _) {
            logger.warn("Issue setting MenuBar to top of screen");
        }

        ObservableList<TableColumn<Contract, ?>> columns = table.getColumns();
        for (TableColumn<Contract, ?> column : columns) {
            column.prefWidthProperty().bind(table.widthProperty().multiply(0.225));
        }
        columns.getFirst().prefWidthProperty().bind(table.widthProperty().multiply(0.1));
    }
}
