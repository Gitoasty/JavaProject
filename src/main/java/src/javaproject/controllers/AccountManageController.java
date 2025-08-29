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
import src.javaproject.classes.Account;
import src.javaproject.interfaces.AccountMethods;
import src.javaproject.interfaces.DatabaseUtilities;
import src.javaproject.interfaces.ScreenUtilities;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountManageController implements Initializable {

    @FXML
    private BorderPane topPane;
    @FXML
    private ListView<String> theList;
    @FXML
    private ChoiceBox<String> roles;
    private static final Logger logger = LoggerFactory.getLogger(AccountManageController.class);
    private final String tableName = "accounts";
    private final String column = "userTag";

    public void updateList() {
        theList.getItems().clear();
        List<String> values = DatabaseUtilities.getColumnFromTable(logger, column, tableName);

        assert values != null;
        theList.getItems().addAll(values);
    }

    public void editAccount() {

        String sql = STR."UPDATE \{tableName} SET role = ? WHERE userTag = ?";

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, roles.getValue());
            pstmt.setString(2, theList.getSelectionModel().getSelectedItem());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        updateList();
    }

    public void deleteAccount() {
        String sql = STR."DELETE FROM \{tableName} WHERE \{column} = ?";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/temp_backgrounds/deleteAlert.jpg")))));
        alert.setTitle("Deletion confirmation");
        alert.setHeaderText(STR."You're about to delete entry: \{theList.getSelectionModel().getSelectedItem()}");
        alert.setContentText("Are you ok with this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                try (Connection conn = DatabaseUtilities.getConnection(logger);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, theList.getSelectionModel().getSelectedItem());

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

        List<Account<String>> accounts = AccountMethods.getAccounts(logger, "");
        theList.getItems().addAll(accounts.stream().map(Account<String>::usernameGetter).toList());
        roles.getItems().addAll("admin", "worker", "user");
    }
}
