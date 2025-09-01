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
import src.javaproject.classes.Worker;
import src.javaproject.enums.WorkerTypes;
import src.javaproject.interfaces.DatabaseUtilities;
import src.javaproject.interfaces.ScreenUtilities;
import src.javaproject.interfaces.WorkerMethods;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public final class WorkerManageController implements Initializable, WorkerMethods {

    @FXML
    private BorderPane topPane;
    @FXML
    private ListView<String> theList;
    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private ListView<String> typeList;
    @FXML
    private  TextField experience;
    private static final Logger logger = LoggerFactory.getLogger(WorkerManageController.class);
    private final String tableName = "workers";

    public void updateList() {
        theList.getItems().clear();
        String column = "id";
        List<String> values = DatabaseUtilities.getColumnFromTable(logger, column, tableName);

        assert values != null;
        theList.getItems().addAll(values);
    }
    public void editWorker() {
        Worker editingWorker = WorkerMethods.getWorker(logger, theList.getSelectionModel().getSelectedItem());

        if (editingWorker == null) {
            return;
        }

        String sql = STR."UPDATE \{tableName} SET fname = ?, lName = ?, type = ?, experience = ? WHERE id = ?";

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fName.getText());
            pstmt.setString(2, lName.getText());
            pstmt.setString(3, typeList.getSelectionModel().getSelectedItem());
            pstmt.setInt(4, Integer.parseInt(experience.getText()));
            pstmt.setString(5, theList.getSelectionModel().getSelectedItem());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        updateList();
    }

    public void deleteWorker()  {
        Worker deletingWorker = WorkerMethods.getWorker(logger, theList.getSelectionModel().getSelectedItem());

        if (deletingWorker == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/temp_backgrounds/deleteAlert.jpg")))));
        alert.setTitle("Deletion confirmation");
        alert.setHeaderText(STR."You're about to delete entry: \{deletingWorker.getFirstName()} \{deletingWorker.getLastName()}");
        alert.setContentText("Are you ok with this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()){
            if (result.get() == ButtonType.OK) {
                String sql = STR."DELETE FROM \{tableName} WHERE id = ?";
                System.out.println(STR."Deleting company: \{deletingWorker.getId()} - \{deletingWorker.getFirstName()} \{deletingWorker.getLastName()}");
                try (Connection conn = DatabaseUtilities.getConnection(logger);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, deletingWorker.getId());

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

        typeList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        typeList.getItems().addAll(Arrays.stream(WorkerTypes.values()).map(WorkerTypes::name).toList());

        List<String> workers = DatabaseUtilities.getColumnFromTable(logger, "id", "workers");
        assert workers != null;

        theList.getItems().addAll(workers);
    }
}
