package src.javaproject.interfaces;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    /**
     * Switches application to the Screen specified by target String
     * @param stackPane Base pane of Screen calling the method
     * @param target String determining which Screen to switch to
     */
    public void switchScreen(StackPane stackPane, String target) {
        try {
            logger.trace("Error with switching screens");
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(STR."\{target}.fxml")));
            Stage stage = (Stage) stackPane.getScene().getWindow();
            Scene scene = new Scene(root);
            logger.trace("Error with switching screens");

            String css = String.valueOf(Objects.requireNonNull(getClass().getClassLoader().getResource("stylesheets.css")));
            logger.trace("Error with switching screens");
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("Error with switching screens");
        } catch (NullPointerException e) {
            logger.error(String.valueOf(e.getCause()));
        }
    }
}
