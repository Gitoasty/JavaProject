package src.javaproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Main_Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/temp_backgrounds/icon.png")));
        stage.getIcons().add(icon);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}