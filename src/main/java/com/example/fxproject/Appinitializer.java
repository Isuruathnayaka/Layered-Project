package com.example.fxproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Appinitializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Appinitializer.class.getResource("/view/HomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("IJSE");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
