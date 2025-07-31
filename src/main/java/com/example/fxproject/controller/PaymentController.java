package com.example.fxproject.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.IJSE.javafxapplication.dto.EnrollDTO;
import lk.IJSE.javafxapplication.dto.EnrollQuotationDTO;
import lk.IJSE.javafxapplication.dto.PaymentDTO;
import lk.IJSE.javafxapplication.dto.QuotationDTO;
import lk.IJSE.javafxapplication.model.dao.EnrollModel;
import lk.IJSE.javafxapplication.model.dao.PaymentModel;
import lk.IJSE.javafxapplication.model.dao.QuotationModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

//import static jdk.internal.agent.Agent.getText;

/**
 * PaymentController
 * ------------------
 * • User types an Enroll ID in <txtEnrollID> and presses ENTER.
 * • The controller:
 *      1️⃣  Looks up the enroll record → obtains quotation_id.
 *      2️⃣  Fills <txtQuotationID>.
 *      3️⃣  Looks up the quotation → fills client name, amount, description.
 */
public class PaymentController implements Initializable {

    public TextField txtInvoiceID;
    public Button btnAdvanceBtn;
    public TableView tablePayment;
    public TableColumn tabInvoiceID;
    public TableColumn tabEnrollID;
    public TableColumn tabQuoID;
    public TableColumn tabAmount;
    public TableColumn tabAdvancePaid;
    public TableColumn tabAdvanceAmount;
    public TableColumn tabStatus;
    public TableColumn tabPaymetDate;

    /* ---------------------------------------------------
     *  FXML controls
     * --------------------------------------------------- */
    @FXML private TextField txtEnrollID;
    @FXML private TextField txtQuotationID;
    @FXML private TextField txtClientName;
    @FXML private TextField txtAmount;
    @FXML private TextArea  txtDescription;

    /* ---------------------------------------------------
     *  Helpers
     * --------------------------------------------------- */
    private void showAlert(Alert.AlertType type, String msg) {
        new Alert(type, msg).show();
        txtEnrollID.setOnAction(e -> loadQuotationData());

    }

    private void loadQuotationData() {
        String enrollId = txtEnrollID.getText();

        try {
            String quotationId = EnrollModel.generateNextEnrollId();
            if (quotationId == null) {
                new Alert(Alert.AlertType.ERROR, "No quotation found for this enroll ID.").show();
                return;
            }

            txtQuotationID.setText(quotationId);

            QuotationDTO quotation = QuotationModel.findById(quotationId);
            if (quotation != null) {
                txtClientName.setText(quotation.getClientId());  // Assuming client name is stored in client_id
                txtAmount.setText(String.valueOf(quotation.getAmount()));
                txtDescription.setText(quotation.getDescription());
            } else {
                new Alert(Alert.AlertType.ERROR, "No quotation details found.").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
        }
    }

    /* ---------------------------------------------------
     *  Load data when user presses ENTER in EnrollID field
     * --------------------------------------------------- */
    private void loadDataForEnrollId() {
        String enrollId = txtEnrollID.getText().trim();
        if (enrollId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter an Enroll ID.");
            return;
        }
        try {
            /* 1️⃣  Find enroll */
            EnrollDTO enroll = EnrollModel.findById(enrollId);
            if (enroll == null) {
                showAlert(Alert.AlertType.WARNING, "No enrollment found for ID " + enrollId);
                clearFieldsExceptEnroll();
                return;
            }
            txtQuotationID.setText(enroll.getQuotationId());

            /* 2️⃣  Find quotation */
            QuotationDTO quotation = QuotationModel.findById(enroll.getQuotationId());
            if (quotation == null) {
                showAlert(Alert.AlertType.WARNING, "No quotation found for ID " + enroll.getQuotationId());
                clearFieldsExceptEnroll();
                return;
            }

            // 3️⃣  Populate remaining text fields
            txtClientName.setText(quotation.getClientName());
            txtAmount.setText(String.format("%.2f", quotation.getAmount()));
            txtDescription.setText(quotation.getDescription());

        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage());
        }
    }

    /* ---------------------------------------------------
     *  Utility helpers
     * --------------------------------------------------- */
    private void clearFieldsExceptEnroll() {
        txtQuotationID.clear();
        txtClientName.clear();
        txtAmount.clear();
        txtDescription.clear();
    }

    /* ---------------------------------------------------
     *  Initialisation
     * --------------------------------------------------- */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtEnrollID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String enrollId = txtEnrollID.getText();

                try {
                    EnrollQuotationDTO dto = PaymentModel.getQuotationDetailsByEnrollId(enrollId);
                    if (dto != null) {
                        txtQuotationID.setText(dto.getQuotationId());
                        txtClientName.setText(dto.getClientName());
                        txtAmount.setText(String.valueOf(dto.getAmount()));
                        txtDescription.setText(dto.getDescription());
                    } else {
                        new Alert(Alert.AlertType.WARNING, "No data found for this Enroll ID").show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
                }
            }
        });
        tabInvoiceID.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        tabEnrollID.setCellValueFactory(new PropertyValueFactory<>("enrollId"));
        tabQuoID.setCellValueFactory(new PropertyValueFactory<>("quotationId"));
        tabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tabAdvancePaid.setCellValueFactory(new PropertyValueFactory<>("advancePaid"));
        tabAdvanceAmount.setCellValueFactory(new PropertyValueFactory<>("advanceAmount"));
        tabStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tabPaymetDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        loadPaymentTable();

    }
    private void loadPaymentTable() {
        try {
            List<PaymentDTO> list = PaymentModel.loadAllPayments();
            tablePayment.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load payments!").show();
        }
    }

    /* ---------------------------------------------------
     *  Buttons (will implement later)
     * --------------------------------------------------- */
    @FXML
    public void btnAdvance(ActionEvent actionEvent) {
        try {
            String enrollId = txtEnrollID.getText();
            String quotationId = txtQuotationID.getText();
            double amount = Double.parseDouble(txtAmount.getText());
            double advance = amount * 0.25;

            // ✅ Create and populate the DTO properly
            PaymentDTO dto = new PaymentDTO();
            dto.setEnrollId(enrollId);
            dto.setQuotationId(quotationId);
            dto.setAmount(amount);
            dto.setAdvanceAmount(advance);
            dto.setAdvancePaid(true); // mark as advance paid
            dto.setStatus("ADVANCE PAID");

            // ✅ Pass only data, not UI components
            boolean result = PaymentModel.payAdvance(dto);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Advance Payment Successful!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Advance already paid for this Enroll ID.").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    public void btnFullpayment(ActionEvent actionEvent) {
        try {
            double amount = Double.parseDouble(txtAmount.getText());

            PaymentDTO dto = new PaymentDTO();
            dto.setEnrollId(txtEnrollID.getText());
            dto.setQuotationId(txtQuotationID.getText());
            dto.setAmount(amount);

            boolean result = PaymentModel.payFull(dto);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Full Payment Successful!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Full Payment Failed!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    public void btnSendEmail(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/mail.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Send Email");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void btnGenerateReport(ActionEvent actionEvent) { }

    public void valueSet(){
        QuotationModel dto = new QuotationModel();
        double amount = Double.parseDouble(txtAmount.getText());

    }

    public void btnCardPayment(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CardPayment.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Card Payment");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}