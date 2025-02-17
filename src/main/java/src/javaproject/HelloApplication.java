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

public class HelloApplication extends Application {

    private static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("Login_Screen.fxml")));
        Scene scene = new Scene(root);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.R) {
                try {
                    stage.setMaximized(false);
                    Parent root1 = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("Register_Screen.fxml")));
                    Scene scene1 = new Scene(root1);
                    String css = Objects.requireNonNull(HelloApplication.class.getResource("/stylesheets.css")).toExternalForm();
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
        stage.setMinHeight(720);
        stage.setTitle("Freelancer tracking");
        stage.setScene(scene);
        stage.setOnCloseRequest(_ ->
            logger.info("App closed successfully")
        );
        stage.show();
        logger.info("App started successfully");
    }

    public static void main(String[] args) {
        DatabaseUtilities.getConnection(logger);
        TableCreator.accountData(logger);
        TableCreator.workerData(logger);
        TableCreator.projectData(logger);
        TableCreator.companyData(logger);
        TableCreator.contractData(logger);
        launch(args);
    }
}