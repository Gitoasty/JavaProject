package src.javaproject.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import src.javaproject.interfaces.LoginManagement;

/**
 * Controller for the login screen
 */
public class LoginController {

    @FXML
    private TextField userText;
    @FXML
    private PasswordField passText;

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
        LoginManagement.attemptLogin(userText.getText(), passText.getText());
    }
}
