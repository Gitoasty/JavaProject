package src.javaproject.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.javaproject.exceptions.PasswordException;
import src.javaproject.interfaces.LoginManagement;
import src.javaproject.interfaces.ScreenUtilities;

import java.util.ArrayList;

public class RegisterController {

    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private TextField userTag;
    @FXML
    private PasswordField passText;
    @FXML
    private PasswordField passConfirmText;
    @FXML
    private Label indicatorLabel;
    @FXML
    private Button registerButton;
    private final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    /**
     * Handles switching to the PasswordField and calling the login() function when pressing enter
     * @param event key press while TextField or PasswordField is selected
     */
    public void typing(KeyEvent event) {
        if (event.getSource().equals(fName) && event.getCode() == KeyCode.ENTER) {
            lName.requestFocus();
        } else if (event.getSource().equals(lName) && event.getCode() == KeyCode.ENTER) {
            userTag.requestFocus();
        } else if (event.getSource().equals(userTag) && event.getCode() == KeyCode.ENTER) {
            passText.requestFocus();
        } else if (event.getSource().equals(passText) && event.getCode() == KeyCode.ENTER) {
            passConfirmText.requestFocus();
        } else if (event.getSource().equals(passConfirmText) && event.getCode() == KeyCode.ENTER) {
            register();
        }
    }

    public void register() {
        ArrayList<String> credentials = new ArrayList<>();
        credentials.add(userTag.getText());
        credentials.add(fName.getText());
        credentials.add(lName.getText());
        credentials.add(passText.getText());
        credentials.add(passConfirmText.getText());

        try {
            if (LoginManagement.attemptRegister(logger, credentials, indicatorLabel)) {
                ScreenUtilities.good(registerButton);
                ScreenUtilities.switchScreen(logger, "/src/javaproject/Login_Screen", registerButton);
            } else {
                ScreenUtilities.bad(registerButton);
            }
        } catch (PasswordException _) {
            logger.info("Password was too long");
        }
    }

    public void goBack(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        ScreenUtilities.switchScreen(logger, "/src/javaproject/Login_Screen", clicked);
    }
}
