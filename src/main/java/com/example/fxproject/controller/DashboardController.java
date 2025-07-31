package com.example.fxproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label dashbord;

    public AnchorPane ANCMainContainer;

    public void btnClient(ActionEvent actionEvent) {

        navigateTo("/view/ClientPage.fxml");
    }

    public void btnEmployee(ActionEvent actionEvent) {
        navigateTo("/view/EmployeePage.fxml");
    }

    public void btnPaymeents(ActionEvent actionEvent) {
    }

    public void btnTodayTasks(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/MainDashboard.fxml");



    }
    public void navigateTo(String path) {
        try {
            ANCMainContainer.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ANCMainContainer.widthProperty());
            anchorPane.prefHeightProperty().bind(ANCMainContainer.heightProperty());

            ANCMainContainer.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }


    public void btnEnroll(ActionEvent actionEvent) {
       navigateTo("/view/EnrollPage.fxml");
    }
}


