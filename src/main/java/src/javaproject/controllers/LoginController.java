package src.javaproject.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import src.javaproject.interfaces.LoginManagement;

public class LoginController {

    @FXML
    private TextField userText;
    @FXML
    private PasswordField passText;

    public void typing(KeyEvent event) {
        if (event.getSource().equals(userText) && event.getCode() == KeyCode.ENTER) {
            passText.requestFocus();
        } else if (event.getSource().equals(passText) && event.getCode() == KeyCode.ENTER) {
            login();
        }
    }
    public void login(){
        LoginManagement.attemptLogin(userText.getText(), passText.getText());
    }
}
