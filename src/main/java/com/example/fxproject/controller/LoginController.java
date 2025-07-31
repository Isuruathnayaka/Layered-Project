package com.example.fxproject.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginController{

    @FXML
    private Label lblLoginErrorMassage;

    @FXML
    private Label lblSinIn;

    @FXML
    private AnchorPane logonAnchPane;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnSinIn(ActionEvent event) throws IOException {
        if (txtUserName.getText().equals("admin") && txtPassword.getText().equals("admin")) {
            lblLoginErrorMassage.setVisible(false);

            HomePageController homePageController = new HomePageController();
            homePageController.mainAnc.setVisible(false);
            homePageController.mainAncContainer.setVisible(true);
            System.out.printf("Login Successful");



        } else {

            lblLoginErrorMassage.setVisible(true);
            txtUserName.clear();
            txtPassword.clear();
            txtUserName.requestFocus();
        }

    }


    public void playWelcomeAnimation() {
    }
}
