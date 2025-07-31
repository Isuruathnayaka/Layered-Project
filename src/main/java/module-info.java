module com.example.fxproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;


    opens com.example.fxproject to javafx.fxml;
    exports com.example.fxproject;
}