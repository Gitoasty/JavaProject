package src.javaproject.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.javaproject.HelloApplication;
import src.javaproject.classes.Account;
import src.javaproject.interfaces.LoginManagement;
import src.javaproject.interfaces.ScreenUtilities;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller for the login screen
 */
public class LoginController {

    @FXML
    private StackPane stackPane;
    @FXML
    private TextField userText;
    @FXML
    private PasswordField passText;
    @FXML
    private Button registerButton;
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

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
     * Calls the {@link LoginManagement#attemptLogin(String, String)} method from {@link LoginManagement} interface
     */
    public void login(){
        int result = LoginManagement.attemptLogin(userText.getText(), passText.getText());

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

    public void register(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        ScreenUtilities.switchScreen("/src/javaproject/Register_Screen", clicked);
    }
}
