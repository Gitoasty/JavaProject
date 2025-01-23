module src.javaproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens src.javaproject to javafx.fxml;
    exports src.javaproject;
}