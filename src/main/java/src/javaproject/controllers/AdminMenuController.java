package src.javaproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for the main admin menu
 */
public class AdminMenuController implements Initializable {

    @FXML
    private BorderPane topPane;
    private final Logger logger = LoggerFactory.getLogger(AdminMenuController.class);

    /**
     *Adds the MenuBar to the screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            MenuBar menuBar = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/src/javaproject/AdminMenuBar.fxml")));
            topPane.setTop(menuBar);
        } catch (IOException | NullPointerException _) {
            logger.warn("Issue setting MenuBar to top of screen");
        }
    }
}
