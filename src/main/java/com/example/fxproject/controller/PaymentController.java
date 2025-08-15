package com.example.fxproject.controller;

import com.example.fxproject.bo.custom.BOFactory;
import com.example.fxproject.bo.custom.PaymentBO;
import com.example.fxproject.entity.Enroll;
import com.example.fxproject.model.EnrollQuotationDTO;
import com.example.fxproject.model.PaymentDTO;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML private TextField txtInvoiceID;
    @FXML private Button btnAdvanceBtn;
    @FXML private TableView<PaymentDTO> tablePayment;
    @FXML private TableColumn<PaymentDTO, String> tabInvoiceID;
    @FXML private TableColumn<PaymentDTO, String> tabEnrollID;
    @FXML private TableColumn<PaymentDTO, String> tabQuoID;
    @FXML private TableColumn<PaymentDTO, Double> tabAmount;
    @FXML private TableColumn<PaymentDTO, Boolean> tabAdvancePaid;
    @FXML private TableColumn<PaymentDTO, Double> tabAdvanceAmount;
    @FXML private TableColumn<PaymentDTO, String> tabStatus;
    @FXML private TableColumn<PaymentDTO, String> tabPaymetDate;

    @FXML private TextField txtEnrollID;
    @FXML private TextField txtQuotationID;
    @FXML private TextField txtClientName;
    @FXML private TextField txtAmount;
    @FXML private TextArea txtDescription;

    // Loose coupling via BO interface
    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load quotation details on Enter key press
        txtEnrollID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    loadQuotationDetails();
                } catch (SQLException | ClassNotFoundException e) {
                    showAlert(Alert.AlertType.ERROR, "Error loading quotation: " + e.getMessage());
                }
            }
        });

        // Set table columns
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

    // Load quotation details into text fields
    private void loadQuotationDetails() throws SQLException, ClassNotFoundException {
        String enrollId = txtEnrollID.getText().trim();
        if (enrollId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter an Enroll ID.");
            return;
        }

        EnrollQuotationDTO dto = paymentBO.getQuotationDetails(enrollId);
        if (dto != null) {
            txtQuotationID.setText(dto.getQuotationId());
            txtClientName.setText(dto.getClientName());
            txtAmount.setText(String.valueOf(dto.getAmount()));
            txtDescription.setText(dto.getDescription());
        } else {
            showAlert(Alert.AlertType.WARNING, "No quotation found for this Enroll ID.");
            clearFieldsExceptEnroll();
        }
    }

    private void loadPaymentTable() {
        try {
            List<PaymentDTO> list = paymentBO.loadAllPayments();
            tablePayment.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException | ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Failed to load payments: " + e.getMessage());
        }
    }

    @FXML
    public void btnAdvance(ActionEvent actionEvent) {
        try {
            String enrollId = txtEnrollID.getText();
            String quotationId = txtQuotationID.getText();
            double amount = Double.parseDouble(txtAmount.getText());
            double advance = amount * 0.25;

            PaymentDTO dto = new PaymentDTO();
            dto.setEnrollId(enrollId);
            dto.setQuotationId(quotationId);
            dto.setAmount(amount);
            dto.setAdvanceAmount(advance);
            dto.setAdvancePaid(true);
            dto.setStatus("ADVANCE PAID");

            boolean result = paymentBO.payAdvance(dto);
            if (result) {
                showAlert(Alert.AlertType.INFORMATION, "Advance Payment Successful!");
                loadPaymentTable();
            } else {
                showAlert(Alert.AlertType.WARNING, "Advance already paid for this Enroll ID.");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }
    }

    @FXML
    public void btnFullpayment(ActionEvent actionEvent) {
        try {
            double amount = Double.parseDouble(txtAmount.getText());
            PaymentDTO dto = new PaymentDTO();
            dto.setEnrollId(txtEnrollID.getText());
            dto.setQuotationId(txtQuotationID.getText());
            dto.setAmount(amount);

            boolean result = paymentBO.payFull(dto);
            if (result) {
                showAlert(Alert.AlertType.INFORMATION, "Full Payment Successful!");
                loadPaymentTable();
            } else {
                showAlert(Alert.AlertType.ERROR, "Full Payment Failed!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }
    }

    @FXML
    public void btnSendEmail(ActionEvent actionEvent) {
        loadWindow("/view/mail.fxml", "Send Email");
    }

    @FXML
    public void btnCardPayment(ActionEvent actionEvent) {
        loadWindow("/view/CardPayment.fxml", "Card Payment");
    }

    private void loadWindow(String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading window: " + e.getMessage());
        }
    }

    private void clearFieldsExceptEnroll() {
        txtQuotationID.clear();
        txtClientName.clear();
        txtAmount.clear();
        txtDescription.clear();
    }

    private void showAlert(Alert.AlertType type, String msg) {
        new Alert(type, msg).show();
    }

    public void btnGenerateReport(ActionEvent actionEvent) {
        // Implement report generation
    }
}
