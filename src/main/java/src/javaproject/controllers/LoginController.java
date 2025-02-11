package src.javaproject.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.javaproject.interfaces.LoginManagement;
import src.javaproject.interfaces.ScreenUtilities;


/**
 * Controller for the login screen
 */
public class LoginController {

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
    public void login(){
        int result = LoginManagement.attemptLogin(logger, userText.getText(), passText.getText());

        switch (result) {
            case 1:
                logger.info("Logged in as admin");
                //switch to admin panel
                break;
            case 2:
                logger.info("Logged in as user");
                //switch to user panel
                break;
            default:
                logger.info("Login failed");
        }
    }

    /**
     * Calls the register {@link ScreenUtilities#switchScreen(Logger, String, Button)} method from {@link ScreenUtilities} interface
     * @param event Event from the Button click
     */
    public void register(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        ScreenUtilities.switchScreen(logger, "/src/javaproject/Register_Screen", clicked);
    }
}
