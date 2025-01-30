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
 * Helper Class for storing Screen related methods
 */
public interface ScreenUtilities {
    Logger logger = LoggerFactory.getLogger(ScreenUtilities.class);

    public static void switchScreen(String target, Button clicked) {
        try {
            Stage stage = (Stage) clicked.getScene().getWindow();
            stage.setMaximized(false);
            Parent root = FXMLLoader.load(ScreenUtilities.class.getResource(STR."\{target}.fxml"));
            Scene scene = new Scene(root);

            String css = ScreenUtilities.class.getResource("/stylesheets.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            logger.error("Error with switching screens");
        } catch (NullPointerException e) {
            logger.error("Problem with loading");
        }
    }
}
