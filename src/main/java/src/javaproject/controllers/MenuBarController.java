package src.javaproject.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.javaproject.interfaces.ScreenUtilities;

/**
 * Controller class for both the admin and user MenuBars
 */
public class MenuBarController {

    @FXML
    private MenuBar adminBar;
    private static final Logger logger = LoggerFactory.getLogger(MenuBarController.class);

    /**
     * Switches scene corresponding to the Menu item chosen
     * @param event Event which triggered the method call
     */
    public void switchScreen(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        String target = "";

        if (((MenuItem) event.getSource()).getText().equals("Search")) {
            target = STR."/src/javaproject/\{item.getParentMenu().getText()}Search";
        } else if (((MenuItem) event.getSource()).getText().equals("Manage")) {
            target = STR."/src/javaproject/\{item.getParentMenu().getText()}Manage";
        }

        ScreenUtilities.switchScreen(logger, target, adminBar);
    }
}
