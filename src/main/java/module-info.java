module src.javaproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires bcrypt;


    opens src.javaproject to javafx.fxml;
    exports src.javaproject;
    exports src.javaproject.controllers;
    opens src.javaproject.controllers to javafx.fxml;
}