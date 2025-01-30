package src.javaproject.interfaces;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.javaproject.controllers.LoginController;

import java.io.IOException;
import java.util.Objects;

/**
 * Helper Interface for storing Screen related methods
 */
public class ScreenUtilities {
    private static Logger logger = LoggerFactory.getLogger(ScreenUtilities.class);

    public void switchScreen(String target, Button clicked) {
        try {
            Stage stage = (Stage) clicked.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(STR."\{target}.fxml"));
            Scene scene = new Scene(root);

            String css = getClass().getResource("/stylesheets.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("Error with switching screens");
        } catch (NullPointerException e) {
            logger.error("Problem with loading");
        }
    }
}
