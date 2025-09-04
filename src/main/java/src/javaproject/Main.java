package src.javaproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import src.javaproject.interfaces.DatabaseUtilities;
import src.javaproject.interfaces.TableCreator;

/**
 * <h1>
 *     Main class, starting point for the application
 * </h1>
 */
public class Main extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * <h3>
     *     Starting method of JavaFX
     * </h3>
     * <p>
     *     Sets window settings and initial scene settings
     * </p>
     * @param stage The window to be displayed on
     * @throws IOException in case of a problem with accessing files
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("Login_Screen.fxml")));
        Scene scene = new Scene(root);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.R) {
                try {
                    stage.setMaximized(false);
                    Parent root1 = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("Register_Screen.fxml")));
                    Scene scene1 = new Scene(root1);
                    String css = Objects.requireNonNull(Main.class.getResource("/stylesheets.css")).toExternalForm();
                    scene1.getStylesheets().add(css);
                    stage.setScene(scene1);
                    stage.setMaximized(true);
                    stage.show();
                    logger.info("Switched to register screen with keyboard shortcut");
                } catch (IOException _) {
                    logger.warn("Problem switching scene with keyboard shortcut");
                }
            }
        });
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/temp_backgrounds/icon.png")));
        stage.getIcons().add(icon);
        stage.setMaximized(true);
        stage.setMinWidth(1280);
        stage.setMinHeight(735);
        stage.setTitle("Freelancer tracking");
        stage.setScene(scene);
        stage.setOnCloseRequest(_ ->
            logger.info("App closed successfully")
        );
        stage.show();
        logger.info("App started successfully");
    }

    /**
     * <h3>
     *     Main method for the application
     * </h3>
     * <p>
     *     Ensures the existence of a database and required tables
     * </p>
     * @param args received command line arguments
     */
    public static void main(String[] args) {
        DatabaseUtilities.getConnection(logger);
        TableCreator.accountData(logger);
        TableCreator.workerData(logger);
        TableCreator.projectData(logger);
        TableCreator.companyData(logger);
        TableCreator.contractData(logger);
        TableCreator.projectsForWorker(logger);
        launch(args);
    }
}