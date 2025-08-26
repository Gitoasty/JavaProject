package src.javaproject.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.javaproject.exceptions.PasswordException;
import src.javaproject.exceptions.UserNotExistException;
import src.javaproject.interfaces.LoginManagement;
import src.javaproject.interfaces.ScreenUtilities;
import src.javaproject.interfaces.WorkerMethods;


/**
 * Controller for the login screen
 */
public final class LoginController implements WorkerMethods {

    @FXML
    private TextField userText;
    @FXML
    private PasswordField passText;
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * Handles switching to the PasswordField and calling the login() function when pressing enter
     * @param event key press while TextField or PasswordField is selected
     */
    public void typing(KeyEvent event) {
        if (event.getSource().equals(userText) && event.getCode() == KeyCode.ENTER) {
            passText.requestFocus();
        } else if (event.getSource().equals(passText) && event.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    /**
     * Calls the {@link LoginManagement#attemptLogin(Logger, String, String)} method from {@link LoginManagement} interface
     */
    public void login() {
        int result = 0;

        try {
            result = LoginManagement.attemptLogin(logger, userText.getText(), passText.getText());
        } catch (PasswordException _) {
            logger.info("Password was too long");
        }

        switch (result) {
            case 1:
                logger.info("Logged in as admin");
                ScreenUtilities.switchScreen(logger, "/src/javaproject/Admin_Menu", userText);
                break;
            case 2:
                logger.info("Logged in as user");
                String status = "";
                try {
                    status = checkStatus(logger, userText.getText());
                } catch (UserNotExistException _) {
                    logger.error("User not found in database");
                }
                if (status == null) {
                    //add something
                } else {
                    ScreenUtilities.switchScreen(logger, "/src/javaproject/UserMenu", userText);
                }
                break;
            default:
                logger.info("Login failed");
                break;
        }
    }

    /**
     * Calls the register {@link ScreenUtilities#switchScreen(Logger, String, Node)} method from {@link ScreenUtilities} interface
     * @param event Event from the Button click
     */
    public void register(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        ScreenUtilities.switchScreen(logger, "/src/javaproject/Register_Screen", clicked);
    }
}
