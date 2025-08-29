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
import src.javaproject.interfaces.CompanyMethods;
import src.javaproject.interfaces.DatabaseUtilities;
import src.javaproject.interfaces.ScreenUtilities;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CompanyManageController implements Initializable {

    @FXML
    private BorderPane topPane;
    @FXML
    private ListView<String> theList;
    @FXML
    private TextField company;
    @FXML
    private ListView<String> workerList;
    private static final Logger logger = LoggerFactory.getLogger(CompanyManageController.class);
    private final String tableName = "companies";
    private final String column = "name";

    public void updateList() {
        theList.getItems().clear();
        List<String> values = DatabaseUtilities.getColumnFromTable(logger, column, tableName);

        assert values != null;
        theList.getItems().addAll(values);
    }

    public void addCompany()  {
        String sql = STR."INSERT INTO \{tableName} (name, workers) VALUES(?, ?)";
        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, company.getText());
            pstmt.setString(2, String.join(",", workerList.getSelectionModel().getSelectedItems()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        updateList();
    }

    public void editCompany() {
        Company editingCompany = CompanyMethods.getCompany(logger, theList.getSelectionModel().getSelectedItem());

        if (editingCompany == null) {
            return;
        }

        String sql = STR."UPDATE \{tableName} SET name = ?, workers = ? WHERE id = ?";

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, company.getText());
            pstmt.setString(2, String.join(",", workerList.getSelectionModel().getSelectedItems()));
            pstmt.setInt(3, editingCompany.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        updateList();
    }

    public void deleteCompany()  {
        Company deletingCompany = CompanyMethods.getCompany(logger, theList.getSelectionModel().getSelectedItem());

        if (deletingCompany == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/temp_backgrounds/deleteAlert.jpg")))));
        alert.setTitle("Deletion confirmation");
        alert.setHeaderText(STR."You're about to delete entry: \{deletingCompany.getName()}");
        alert.setContentText("Are you ok with this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()){
            if (result.get() == ButtonType.OK) {
                String sql = STR."DELETE FROM \{tableName} WHERE id = ?";
                System.out.println(STR."Deleting company: \{deletingCompany.getId()} - \{deletingCompany.getName()}");
                try (Connection conn = DatabaseUtilities.getConnection(logger);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, deletingCompany.getId());

                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                updateList();
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

        List<String> companies = DatabaseUtilities.getColumnFromTable(logger, column, tableName);
        List<String> workers = DatabaseUtilities.getColumnFromTable(logger, "id", "workers");
        assert companies != null;
        assert workers != null;

        theList.getItems().addAll(companies);
        workerList.getItems().addAll(workers);
    }
}
