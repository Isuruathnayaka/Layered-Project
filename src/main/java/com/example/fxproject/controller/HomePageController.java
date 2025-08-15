package com.example.fxproject.controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class HomePageController implements Initializable {
    public AnchorPane hedderANC;
    public AnchorPane imageANC;
    public Label lblLestStartTo;
    public Label lblMakeNature;
    public Button homeLoginBtn;
    public AnchorPane mainAnc;
    public AnchorPane mainAncContainer;
    public ImageView imageSlideShow;
    public ImageView imageSlide;
    public ImageView imageView;
    public FlowPane loginFlowPane;
    public FlowPane loginFlow;
    public Label lblSetTime;
    public Label lblSetDate;
    public AnchorPane loginAnc;
    public TextField txtUserName;
    public TextField txtPassword;
    public  AnchorPane ancMainLoader;
    public AnchorPane ancbtnBar;
    public Button btnManageClients;
    public Button btnManageEmployee;
    public Button btnEnroll;
    public Button btnHome;
    public FlowPane flowPaneHome;
    public FlowPane flowpaneManageClients;
    public FlowPane flowpaneManageEmployee;
    public FlowPane flowpaneEnroll;
    public TextField txtDesktopsearchbar;

    private int currentIndex = 0;

    private final String[] imagePaths = {
            "/images/01.png",
            "/images/02.png",
            "/images/03.png"
    };

    public void btnfirstLogin(ActionEvent actionEvent) throws IOException {
        showLoginPopup();

    }

    public void LoginMouseEnterd(MouseEvent mouseDragEvent) {
        homeLoginBtn.setStyle("-fx-background-color: #006bff;");
    }

    public void LoginMouseExited(MouseEvent mouseDragEvent) {
        homeLoginBtn.setStyle("-fx-background-color:  #1eff00;");
    }

    private void animateLabel(Label label, double distance) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(label);
        transition.setDuration(Duration.seconds(2));
        transition.setByX(distance); // You can use same or different values
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.play();
    }

    public static void navigateTo(String path, AnchorPane ancMainLoader) {
        try {
            ancMainLoader.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(HomePageController.class.getResource(path));

            anchorPane.prefWidthProperty().bind(  ancMainLoader.widthProperty());
            anchorPane.prefHeightProperty().bind(  ancMainLoader.heightProperty());

            ancMainLoader.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/MainDashboard.fxml", ancMainLoader);
        animateLabel(lblMakeNature, 200);
        animateLabel(lblLestStartTo, 260);
        ancbtnBar.setDisable(true);


        startClock();


    }

    private void startClock() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    LocalTime currentTime = LocalTime.now();
                    lblSetTime.setText("Time: " + currentTime.format(timeFormatter));

                    lblSetDate.setText("Date: " + java.time.LocalDate.now().format(dateFormatter));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }
    private void showLoginPopup() {
        // 1. Blur the background
        BoxBlur blur = new BoxBlur(5, 5, 3);
        mainAnc.setEffect(blur);

        // 2. Prepare and show loginPane
        loginAnc.setVisible(true);
        loginAnc.setOpacity(0);
        loginAnc.setScaleX(0.8);
        loginAnc.setScaleY(0.8);

        // 3. Animate: Fade + Scale
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300),  loginAnc);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(300),  loginAnc);
        scaleUp.setFromX(0.8);
        scaleUp.setFromY(0.8);
        scaleUp.setToX(1);
        scaleUp.setToY(1);

        ParallelTransition animation = new ParallelTransition(fadeIn, scaleUp);
        animation.play();
    }

    @FXML
    public void hideLoginPopup() {
        // Hide loginPane and remove blur
        loginAnc.setVisible(false);
        mainAnc.setEffect(null);
    }

    public void btnDadhboardLogin(ActionEvent actionEvent) {
        if (txtUserName.getText().equals("admin") && txtPassword.getText().equals("admin")) {
           mainAnc.setVisible(false);
           mainAncContainer.setVisible(true);
           loginAnc.setVisible(false);
           ancbtnBar.setDisable(false);
           txtDesktopsearchbar.setDisable(false);
           
        }
    }

    public void btnManageClients(ActionEvent actionEvent) {
        navigateTo("/view/ClientPage.fxml", ancMainLoader);
        flowpaneManageClients.setVisible(true);
        flowpaneManageEmployee.setVisible(false);
        flowpaneEnroll.setVisible(false);
        flowPaneHome.setVisible(false);


    }

    public void manageClientsClicked(MouseEvent mouseEvent) {


    }

    public void btnManageEmployee(ActionEvent actionEvent) {
        navigateTo("/view/EmployeePage.fxml", ancMainLoader);
        flowpaneManageClients.setVisible(false);
        flowpaneManageEmployee.setVisible(true);
        flowpaneEnroll.setVisible(false);
        flowPaneHome.setVisible(false);
    }

    public void manageEmployeeClicked(MouseEvent mouseEvent) {


    }

    public void btnEnroll(ActionEvent actionEvent) {
        navigateTo("/view/EnrollPage.fxml", ancMainLoader);
        flowpaneManageClients.setVisible(false);
        flowpaneManageEmployee.setVisible(false);
        flowpaneEnroll.setVisible(true);
        flowPaneHome.setVisible(false);
    }

    public void enrollClicked(MouseEvent mouseEvent) {



    }

    public void enrollMouseEntered(MouseEvent mouseEvent) {


    }

    public void manageClientsMouseEntered(MouseEvent mouseEvent) {

    }

    public void manageClientsMouseExited(MouseEvent mouseEvent) {

    }

    public void manageEmployeeMouseEntered(MouseEvent mouseEvent) {

    }

    public void manageEmployeeMouseExited(MouseEvent mouseEvent) {

    }

    public void enrollMouseExited(MouseEvent mouseEvent) {

    }

    public void btnHome(ActionEvent actionEvent) {
        navigateTo("/view/MainDashboard.fxml", ancMainLoader);
        flowpaneManageClients.setVisible(false);
        flowpaneManageEmployee.setVisible(false);
        flowpaneEnroll.setVisible(false);
        flowPaneHome.setVisible(true);
    }

    public void homeClicked(MouseEvent mouseEvent) {

    }

    public void homeMouseEntered(MouseEvent mouseEvent) {

    }
    public void searchbar(){

    }

    public void btnPayments(ActionEvent actionEvent) {
        navigateTo("/view/payment.fxml", ancMainLoader);
    }

    public void btnFacebookIcon(ActionEvent actionEvent) {
    }

    public void btnGmail(ActionEvent actionEvent) {
        
    }
}


