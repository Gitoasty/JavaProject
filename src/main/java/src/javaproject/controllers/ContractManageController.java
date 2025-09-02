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
import src.javaproject.classes.Company;
import src.javaproject.classes.Contract;
import src.javaproject.classes.ContractSerializer;
import src.javaproject.classes.SerialWriter;
import src.javaproject.exceptions.NullObjectException;
import src.javaproject.interfaces.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class ContractManageController implements Initializable {

    @FXML
    private BorderPane topPane;
    @FXML
    private ListView<String> theList;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField salaryField;
    @FXML
    private ListView<String> companyList;
    private static final Logger logger = LoggerFactory.getLogger(CompanyManageController.class);
    private final String tableName = "contracts";
    private List<Contract> contracts;

    public void updateList() {
        theList.getItems().clear();
        contracts = ContractMethods.getContracts(logger, "");
        List<String> values = new ArrayList<>();

        if (!contracts.isEmpty()) {
            for (Contract c : contracts) {
                values.add(STR."\{c.id()} -> \{c.startDate()} <-> \{c.endDate()} - \{c.salary()} - \{c.companyId()}");
            }
        }
        theList.getItems().addAll(values);
    }

    public void addContract() throws NullObjectException {
        String sql = STR."INSERT INTO \{tableName} (start, end, salary, companyId) VALUES(?, ?, ?, ?)";
        Company temp = CompanyMethods.getCompany(logger, companyList.getSelectionModel().getSelectedItem());

        assert temp != null;
        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(startDate.getValue()));
            pstmt.setDate(2, Date.valueOf(endDate.getValue()));
            pstmt.setInt(3, Integer.parseInt(salaryField.getText()));
            pstmt.setInt(4, temp.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Contract data = ContractMethods.getContracts(logger, "").getLast();
        System.out.println(data);

        if (data == null) throw new NullObjectException("Contract is null");
        updateList();
        SerializationUtilities.serialize(new SerialWriter<>("Admin", new ContractSerializer(data)));
    }

    public void editContract() {
        String contractId = theList.getSelectionModel().getSelectedItem().split(" ->")[0];
        Contract data = ContractMethods.getContract(logger, contractId);
        Company company = CompanyMethods.getCompany(logger, companyList.getSelectionModel().getSelectedItem());
        assert company != null;

        String sql = STR."UPDATE \{tableName} SET start = ?, end = ?, salary = ?, companyId = ? WHERE id = ?";

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(startDate.getValue()));
            pstmt.setDate(2, Date.valueOf(endDate.getValue()));
            pstmt.setInt(3, Integer.parseInt(salaryField.getText()));
            pstmt.setInt(4, company.getId());
            pstmt.setInt(5, Integer.parseInt(contractId));

            System.out.println("got here");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        updateList();
        assert data != null;
        SerializationUtilities.serialize(new SerialWriter<>("Admin", new ContractSerializer(data)));
    }

    public void deleteContract()  {
        String contractId = theList.getSelectionModel().getSelectedItem().split(" ->")[0];
        Contract data = ContractMethods.getContract(logger, contractId);
        Contract deletingContract = null;
        
        for (Contract c : contracts) {
            if (c.id() == Integer.parseInt(contractId)) {
                deletingContract = c;
                break;
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/temp_backgrounds/deleteAlert.jpg")))));
        alert.setTitle("Deletion confirmation");
        assert deletingContract != null;
        alert.setHeaderText(STR."You're about to delete entry: \{deletingContract.id()}");
        alert.setContentText("Are you ok with this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()){
            if (result.get() == ButtonType.OK) {
                String sql = STR."DELETE FROM \{tableName} WHERE id = ?";
                try (Connection conn = DatabaseUtilities.getConnection(logger);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, deletingContract.id());

                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                updateList();
                assert data != null;
                SerializationUtilities.serialize(new SerialWriter<>("Admin", new ContractSerializer(data)));
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

        companyList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        List<String> companies = DatabaseUtilities.getColumnFromTable(logger, "name", "companies");
        assert companies != null;

        updateList();
        companyList.getItems().addAll(companies);

    }
}
