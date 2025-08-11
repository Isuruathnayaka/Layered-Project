package com.example.fxproject.controller;

import com.example.fxproject.bo.*;
import com.example.fxproject.bo.custom.BOFactory;
import com.example.fxproject.bo.*;
import com.example.fxproject.model.EnrollDTO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EnrollPageController implements Initializable {

    @FXML private TextField txtEnrollID, txtClientID, txtClientName, txtContact, txtQuatationID, txtEmpID, txtEnrollDate;
    @FXML private TextArea txtDescription;
    @FXML private DatePicker datePicker;
    @FXML private Button btnEnrollNow, btnGenereteReport, btnUpdate, btnDelete;
    @FXML private TableView<EnrollDTO> tableEnroll;
    @FXML private TableColumn<EnrollDTO, String> colEnrollid, colClientID, colClientName, colContact, colQuotationID, colEmpID, colDescription;
    @FXML private TableColumn<EnrollDTO, Date> colDate, colStartingDate;

    private final String clientIdPattern = "^C\\d{3}$";

    private final Enro enrollBO = (EnrollBo) BOFactory.getInstance().getBO(BOFactory.BOType.ENROLL);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableColumns();
        loadEnrollments();
        setupClientIDSearch();
        setupDatePicker();
        txtEnrollID.setText(enrollBO.generateNextEnrollId());
    }

    private void setTableColumns() {
        colEnrollid.setCellValueFactory(new PropertyValueFactory<>("enrollId"));
        colClientID.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colClientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colQuotationID.setCellValueFactory(new PropertyValueFactory<>("quotationId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmpID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colStartingDate.setCellValueFactory(new PropertyValueFactory<>("startingDate"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void loadEnrollments() {
        try {
            List<EnrollDTO> list = enrollBO.getAllEnrollments();
            tableEnroll.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading enrollments: " + e.getMessage()).show();
        }
    }

    private void setupClientIDSearch() {
        txtClientID.setOnAction(event -> {
            String clientID = txtClientID.getText().trim();
            if (!clientID.matches(clientIdPattern)) {
                new Alert(Alert.AlertType.WARNING, "Invalid Client ID Format (e.g., C001)").show();
                clearForm();
                return;
            }
            loadClientDetails(clientID);
        });
    }

    private void setupDatePicker() {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date != null && !empty && (date.isBefore(LocalDate.now()) || date.equals(LocalDate.now()))) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
                }
            }
        });
    }

    private void loadClientDetails(String clientId) {
        try {
            ClientDTO client = enrollBO.getClientDetails(clientId);
            if (client != null) {
                txtClientName.setText(client.getName());
                txtContact.setText(client.getPhone());
                txtQuatationID.setText(enrollBO.getLatestQuotationId(clientId));
            } else {
                clearForm();
                new Alert(Alert.AlertType.WARNING, "Client not found").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading client data: " + e.getMessage()).show();
        }
    }

    @FXML
    public void btnEnrollNow(ActionEvent actionEvent) {
        try {
            EnrollDTO dto = new EnrollDTO(
                    txtEnrollID.getText(),
                    txtClientID.getText(),
                    txtClientName.getText(),
                    txtContact.getText(),
                    txtQuatationID.getText(),
                    Date.valueOf(LocalDate.now()), // current date as enroll date
                    txtEmpID.getText(),
                    datePicker.getValue() != null ? Date.valueOf(datePicker.getValue()) : null,
                    txtDescription.getText()
            );

            boolean isSaved = enrollBO.saveEnroll(dto);
            if (isSaved) {
                loadEnrollments();
                clearForm();
                new Alert(Alert.AlertType.INFORMATION, "Enrollment saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save enrollment.").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    public void btnUpdate(ActionEvent actionEvent) {
        try {
            EnrollDTO dto = new EnrollDTO(
                    txtEnrollID.getText(),
                    txtClientID.getText(),
                    txtClientName.getText(),
                    txtContact.getText(),
                    txtQuatationID.getText(),
                    Date.valueOf(LocalDate.now()),
                    txtEmpID.getText(),
                    datePicker.getValue() != null ? Date.valueOf(datePicker.getValue()) : null,
                    txtDescription.getText()
            );

            boolean isUpdated = enrollBO.updateEnroll(dto);
            if (isUpdated) {
                loadEnrollments();
                new Alert(Alert.AlertType.INFORMATION, "Enrollment updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed.").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    public void btnDelete(ActionEvent actionEvent) {
        EnrollDTO selected = tableEnroll.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete this enrollment?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.YES) {
                try {
                    boolean isDeleted = enrollBO.deleteEnroll(selected.getEnrollId());
                    if (isDeleted) {
                        loadEnrollments();
                        clearForm();
                        new Alert(Alert.AlertType.INFORMATION, "Deleted successfully!").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Delete failed!").show();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Error deleting: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Select an enrollment to delete.").show();
        }
    }

    @FXML
    public void btnReset(ActionEvent actionEvent) {
        clearForm();
    }

    private void clearForm() {
        txtEnrollID.setText(enrollBO.generateNextEnrollId());
        txtClientID.clear();
        txtClientName.clear();
        txtContact.clear();
        txtQuatationID.clear();
        txtEmpID.clear();
        txtDescription.clear();
        datePicker.setValue(null);
    }
}
