package src.javaproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.javaproject.classes.SerialWriter;
import src.javaproject.interfaces.SerializationUtilities;
import src.javaproject.interfaces.SerializeMarker;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LogsController implements Initializable {
    @FXML
    private BorderPane topPane;
    @FXML
    private ListView<String> logList;
    private static final Logger logger = LoggerFactory.getLogger(LogsController.class);

    /**
     * Adds MenuBar
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            MenuBar menuBar = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/src/javaproject/AdminMenuBar.fxml")));
            topPane.setTop(menuBar);
        } catch (IOException | NullPointerException _) {
            logger.warn("Issue setting MenuBar to top of screen");
        }
        List<SerialWriter<String, SerializeMarker>> lines;
        lines = SerializationUtilities.deserialize();

        try {
            logList.getItems().clear();
            logList.getItems().addAll(lines.stream().map(SerialWriter<String, SerializeMarker>::toString).toList());
        } catch (Exception _) {
            logger.error("Problem setting deserialized data");
        }
    }
}
