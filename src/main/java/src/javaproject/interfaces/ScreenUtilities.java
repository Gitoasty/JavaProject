package src.javaproject.interfaces;

import javafx.animation.RotateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Objects;

/**
 * Helper Interface for storing Screen related methods
 */
public interface ScreenUtilities {

    /**
     * Switches displayed screen to the target Screen
     * @param logger logger of controller calling this method
     * @param target name of target screen
     * @param clicked Button which triggered the method call
     */
    static void switchScreen(Logger logger, String target, Button clicked) {
        try {
            Stage stage = (Stage) clicked.getScene().getWindow();
            stage.setMaximized(false);
            Parent root = FXMLLoader.load(Objects.requireNonNull(ScreenUtilities.class.getResource(STR."\{target}.fxml")));
            Scene scene = new Scene(root);

            String css = Objects.requireNonNull(ScreenUtilities.class.getResource("/stylesheets.css")).toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
            logger.info(STR."Switched screen to \{target}");
        } catch (IOException e) {
            logger.error("Error with switching screens");
        } catch (NullPointerException e) {
            logger.error("Problem with loading");
        }
    }

    /**
     * Indicates action was successful
     * @param b Button object to animate
     */
     static void good(Button b) {
        RotateTransition r = new RotateTransition();
        r.setNode(b);
        r.setDuration(Duration.millis(500));
        r.setByAngle(360);
        r.setAxis(Rotate.X_AXIS);
        r.setAutoReverse(true);
        r.play();
    }

    /**
     * Indicates action was not successful
     * @param b Button object to animate
     */
    static void bad(Button b) {
        RotateTransition r = new RotateTransition();
        r.setNode(b);
        r.setDuration(Duration.millis(500));
        r.setByAngle(360);
        r.setAxis(Rotate.Y_AXIS);
        r.setAutoReverse(true);
        r.play();
    }

    /**
     * Updates passed Label with the passed String
     * @param label Label to be updated
     * @param text String to insert into the Label
     */
    static void labelUpdater(Label label, String text) {
        label.setText(text);
    }
}
