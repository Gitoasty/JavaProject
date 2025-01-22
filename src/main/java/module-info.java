module src.javaproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens src.javaproject to javafx.fxml;
    exports src.javaproject;
}