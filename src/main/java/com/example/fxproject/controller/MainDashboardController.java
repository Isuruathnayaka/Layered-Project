package com.example.fxproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.IJSE.javafxapplication.model.dao.ClientModel;
import lk.IJSE.javafxapplication.model.dao.EmployeeModel;
import lk.IJSE.javafxapplication.model.dao.PaymentModel;
import lk.IJSE.javafxapplication.model.dao.QuotationModel;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainDashboardController implements Initializable {
    @FXML
    private AnchorPane ancMainLoader;
    public Label lblClientCount;
    public Label lblPlantCount;
    public Label lblActiveEmp1;
    public ImageView btnFacebookIcon;
    public ImageView btnGmail;
    public ImageView btnWhatsApp;

    public Button btnTofacebook;
    public Button btnToEmail;
    public Button btnToWhatsApp;
    public Label lblPaymentCount;
    public Label lblSavedClients;
    public Label lblQuotationCount;
    public Label lblEmployeeCount;




    public void btnFacebookIcon(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URI("https://www.facebook.com"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void btnGmail(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URI("https://www.gmail.com"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void btnWhatsAppIcon(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URI("https://www.webwhatsapp.com"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int paymentCount = PaymentModel.getPaymentCount();
            lblPaymentCount.setText(String.valueOf(paymentCount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            int ClientCount = ClientModel.getClientCount();
            lblSavedClients.setText(String.valueOf(ClientCount));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            int quotationCount = QuotationModel.getQuotationCount();
            lblQuotationCount.setText(String.valueOf(quotationCount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            int employeeCount = EmployeeModel.getEmployeeCount();
            lblEmployeeCount.setText(String.valueOf(employeeCount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    }

