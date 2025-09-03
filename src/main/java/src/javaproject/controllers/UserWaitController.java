package src.javaproject.controllers;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.javaproject.exceptions.UserNotExistException;
import src.javaproject.interfaces.ScreenUtilities;
import src.javaproject.interfaces.WorkerMethods;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class UserWaitController implements Initializable {
    @FXML
    private ImageView rotator;
    @FXML
    private Label storage;
    private ScheduledExecutorService scheduler;
    private final Logger logger = LoggerFactory.getLogger(UserWaitController.class);

    private void shutdownScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException _) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Path path = Path.of("G:\\learning_java\\JavaProject\\src\\main\\resources\\data\\tempCred.txt");
        try (Stream<String> linesStream = Files.lines(path)) {
            linesStream.findFirst().ifPresent(storage::setText);
            Files.delete(path);
        } catch (IOException _) {
            logger.error("Could not load user");
        }

        RotateTransition rotate = new RotateTransition();
        rotate.setNode(rotator);
        rotate.setDuration(Duration.millis(750));
        rotate.setCycleCount(Animation.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.play();

        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "accountChecker");
            t.setDaemon(true);

            return t;
        });

        Runnable databaseChecker = () -> {
            try {
                String status = WorkerMethods.checkStatus(logger, storage.getText());

                if (status != null) {
                    shutdownScheduler();

                    Platform.runLater(() ->
                        ScreenUtilities.switchScreen(logger, "/src/javaproject/Admin_Menu", storage)
                    );
                }
            } catch (UserNotExistException _) {
                logger.error("User does not exist");
            }
        };

        scheduler.scheduleAtFixedRate(databaseChecker, 0, 2, TimeUnit.SECONDS);
    }
}
