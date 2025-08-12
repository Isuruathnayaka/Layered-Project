package com.example.fxproject.controller;

import com.example.fxproject.bo.custom.BOFactory;
import com.example.fxproject.bo.custom.EnrollBo;
import com.example.fxproject.model.EnrollDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EnrollPageController implements Initializable {

    public TextField txtEnrollDate;
    @FXML private TextField txtEnrollID, txtClientID, txtClientName, txtContact, txtQuatationID, txtEmpID;
    @FXML private TextArea txtDescription;
    @FXML private DatePicker datePicker;
    @FXML private TableView<EnrollDTO> tableEnroll;
    @FXML private TableColumn<EnrollDTO, String> colEnrollid, colClientID, colClientName, colContact, colQuotationID, colEmpID, colDescription;
    @FXML private TableColumn<EnrollDTO, Date> colDate, colStartingDate;

    private final EnrollBo enrollBO = (EnrollBo) BOFactory.getInstance().getBO(BOFactory.BOType.ENROLL);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableColumns();
        loadEnrollments();
        try {
            txtEnrollID.setText(enrollBO.generateNewEnrollId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTableColumns() {
        colEnrollid.setCellValueFactory(new PropertyValueFactory<>("enroll_id"));
        colClientID.setCellValueFactory(new PropertyValueFactory<>("client_id"));
        colClientName.setCellValueFactory(new PropertyValueFactory<>("client_name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colQuotationID.setCellValueFactory(new PropertyValueFactory<>(" quotation_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmpID.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        colStartingDate.setCellValueFactory(new PropertyValueFactory<>("starting_date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void loadEnrollments() {
        try {
            List<EnrollDTO> list = enrollBO.getAllEnroll();
            tableEnroll.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading enrollments: " + e.getMessage()).show();
        }
    }

    @FXML
    public void btnEnrollNow() {
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

            if (enrollBO.saveEnroll(dto)) {
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
    public void btnUpdate() {
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

            if (enrollBO.updateEnroll(dto)) {
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
    public void btnDelete() {
        EnrollDTO selected = tableEnroll.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                if (enrollBO.deleteEnroll(selected.getEnrollId())) {
                    loadEnrollments();
                    clearForm();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Delete failed!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error deleting: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Select an enrollment to delete.").show();
        }
    }

    private void clearForm() throws SQLException, ClassNotFoundException {
        txtEnrollID.setText(enrollBO.generateNewEnrollId());
        txtClientID.clear();
        txtClientName.clear();
        txtContact.clear();
        txtQuatationID.clear();
        txtEmpID.clear();
        txtDescription.clear();
        datePicker.setValue(null);
    }

    public void btnReset(ActionEvent actionEvent) {
    }

    public void btnGenereteReport(ActionEvent actionEvent) {
    }
}
