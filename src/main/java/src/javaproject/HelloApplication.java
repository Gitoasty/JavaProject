package src.javaproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login_Screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/temp_backgrounds/icon.png")));
        stage.getIcons().add(icon);
        stage.setMaximized(true);
        stage.setMinWidth(1280);
        stage.setMinHeight(720);
        stage.setTitle("Freelancer tracking");
        stage.setScene(scene);
        stage.setOnCloseRequest(event ->
            logger.info("App closed successfully")
        );
        stage.show();
        logger.info("App started successfully");
    }

    public static void main(String[] args) {
        DatabaseUtilities.getConnection(logger);
        TableCreator.accountData(logger);
        launch(args);
    }
}