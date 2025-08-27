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
import src.javaproject.classes.Account;
import src.javaproject.interfaces.AccountMethods;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the AccountSearch Screen
 */
public class AccountSearchController implements Initializable {

    @FXML
    private BorderPane topPane;
    @FXML
    private TextField searchTarget;
    @FXML
    private TableView<Account<String>> table;
    @FXML
    private TableColumn<Account<String>, String> userTag;
    @FXML
    private TableColumn<Account<String>, String> password;
    @FXML
    private TableColumn<Account<String>, String> role;
    private static final Logger logger = LoggerFactory.getLogger(AccountSearchController.class);

    public void searchAccounts() {
        List<Account<String>> contracts = AccountMethods.getAccounts(logger, searchTarget.getText());

        table.getItems().clear();
        userTag.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().usernameGetter()));
        password.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().passwordGetter()));
        role.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().roleGetter()));

        ObservableList<Account<String>> observableContracts = FXCollections.observableArrayList(contracts);
        table.setItems(observableContracts);
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
                    searchAccounts();
                }
            });
        } catch (IOException | NullPointerException _) {
            logger.warn("Issue setting MenuBar to top of screen");
        }

        ObservableList<TableColumn<Account<String>, ?>> columns = table.getColumns();
        for (TableColumn<Account<String>, ?> column : columns) {
            column.prefWidthProperty().bind(table.widthProperty().multiply(0.33));
        }
    }
}
