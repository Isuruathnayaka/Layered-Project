//module com.example.fxproject {
//    requires javafx.controls;
//    requires javafx.fxml;
//    requires static lombok;
//    requires java.sql;
//    requires java.desktop;
//
//    opens com.example.fxproject.controller to javafx.fxml; // ✅ Needed for FXML controllers
//    opens com.example.fxproject to javafx.fxml;
//
//    exports com.example.fxproject;
//}
module com.example.fxproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires java.desktop;

    opens com.example.fxproject.controller to javafx.fxml;
    opens com.example.fxproject.entity to javafx.base;

    exports com.example.fxproject;
    exports com.example.fxproject.controller;
   // exports com.example.fxproject.bo;
    exports com.example.fxproject.dao;
    exports com.example.fxproject.model;
}
