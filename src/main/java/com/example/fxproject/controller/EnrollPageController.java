package com.example.fxproject.controller;

import com.example.fxproject.bo.custom.BOFactory;
import com.example.fxproject.bo.custom.EnrollBo;
import com.example.fxproject.model.ClientDTO;
import com.example.fxproject.model.EnrollDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EnrollPageController implements Initializable {

    @FXML
    public TextField txtEnrollID, txtClientID, txtClientName, txtContact, txtQuatationID, txtEmpID;
    @FXML
    public TextArea txtDescription;
    @FXML
    public DatePicker datePicker;
    @FXML
    public TableView<EnrollDTO> tableEnroll;
    @FXML
    public TableColumn<EnrollDTO, String> colEnrollid, colClientID, colClientName, colContact, colQuotationID, colEmpID, colDescription;
    @FXML
    public TableColumn<EnrollDTO, Date> colDate, colStartingDate;

    private final String clientIdPattern = "^C\\d{3}$";

    private final EnrollBo enrollBo = (EnrollBo) BOFactory.getInstance().getBO(BOFactory.BOType.ENROLL);
    public TextField txtEnrollDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtEnrollID.setText(generateNextId());
        datePicker.setValue(LocalDate.now());

        setTableColumns();
        loadEnrolls();
        setupTableRowClick(); // <-- Added row click event

        // Press Enter on Client ID to load details
        txtClientID.setOnAction(event -> loadClientDetails(txtClientID.getText().trim()));
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

    private void loadEnrolls() {
        try {
            List<EnrollDTO> enrollList = enrollBo.getAllEnroll();
            ObservableList<EnrollDTO> observableList = FXCollections.observableArrayList(enrollList);
            tableEnroll.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load enrolls").show();
        }
    }

    /** Row click event to load data into text fields **/
    private void setupTableRowClick() {
        tableEnroll.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtEnrollID.setText(newSelection.getEnrollId());
                txtClientID.setText(newSelection.getClientId());
                txtClientName.setText(newSelection.getClientName());
                txtContact.setText(newSelection.getContact());
                txtQuatationID.setText(newSelection.getQuotationId());

                if (newSelection.getDate() != null) {
                    txtEnrollDate.setText(String.valueOf(newSelection.getDate()));
                } else {
                    txtEnrollDate.clear();
                }

                txtEmpID.setText(newSelection.getEmployeeId());
                if (newSelection.getStartingDate() != null) {
                    datePicker.setValue(newSelection.getStartingDate().toLocalDate());
                } else {
                    datePicker.setValue(null);
                }

                txtDescription.setText(newSelection.getDescription());
            }
        });
    }

    /** FIXED METHOD: Load client details using Client ID **/
    private void loadClientDetails(String clientId) {
        if (!clientId.matches(clientIdPattern)) {
            new Alert(Alert.AlertType.WARNING, "Invalid Client ID format").show();
            return;
        }

        try {
            // Try to get latest enrollment
            EnrollDTO enrollDTO = enrollBo.searchLatestEnrollByClientId(clientId);

            if (enrollDTO != null) {
                // Enrollment exists, load details
                txtClientName.setText(enrollDTO.getClientName());
                txtContact.setText(enrollDTO.getContact());
                txtQuatationID.setText(enrollDTO.getQuotationId());
                txtEnrollDate.setText(enrollDTO.getDate() != null ? String.valueOf(enrollDTO.getDate()) : "");
            } else {
                // Enrollment not found, load basic client info
                ClientDTO clientDTO = enrollBo.getClientDetailsById(clientId); // NEW method in BO/DAO
                if (clientDTO != null) {
                    txtClientName.setText(clientDTO.getName());
                    txtContact.setText(clientDTO.getContact());
                    txtQuatationID.clear();
                    txtEnrollDate.clear();
                } else {
                    clearClientFields();
                    new Alert(Alert.AlertType.WARNING, "Client not found").show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading client data").show();
        }
    }

    private void clearClientFields() {
        txtClientName.clear();
        txtContact.clear();
        txtQuatationID.clear();
        txtEnrollDate.clear();
    }

    private String generateNextId() {
        try {
            return enrollBo.generateNewEnrollId();
        } catch (Exception e) {
            e.printStackTrace();
            return "EN001";
        }
    }

    @FXML
    public void btnEnrollNow(ActionEvent event) {
        try {
            LocalDate startDate = datePicker.getValue();
            if (startDate == null) {
                new Alert(Alert.AlertType.WARNING, "Select starting date").show();
                return;
            }

            EnrollDTO dto = new EnrollDTO(
                    txtEnrollID.getText(),
                    txtClientID.getText(),
                    txtClientName.getText(),
                    txtContact.getText(),
                    txtQuatationID.getText(),
                    Date.valueOf(LocalDate.now()),
                    txtEmpID.getText(),
                    Date.valueOf(startDate),
                    txtDescription.getText()
            );

            boolean saved = enrollBo.saveEnroll(dto);
            if (saved) {
                new Alert(Alert.AlertType.INFORMATION, "Enroll saved successfully").show();
                clearForm();
                loadEnrolls();
                txtEnrollID.setText(generateNextId());
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save enroll").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    public void btnUpdate(ActionEvent event) {
        try {
            EnrollDTO dto = new EnrollDTO();
            dto.setEnrollId(txtEnrollID.getText());
            dto.setStartingDate(Date.valueOf(datePicker.getValue()));
            dto.setDescription(txtDescription.getText());

            boolean updated = enrollBo.updateEnroll(dto);
            if (updated) {
                new Alert(Alert.AlertType.INFORMATION, "Enroll updated successfully").show();
                clearForm();
                loadEnrolls();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    public void btnDelete(ActionEvent event) {
        EnrollDTO selected = tableEnroll.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Select an enroll to delete").show();
            return;
        }

        try {
            boolean deleted = enrollBo.deleteEnroll(selected.getEnrollId());
            if (deleted) {
                new Alert(Alert.AlertType.INFORMATION, "Enroll deleted successfully").show();
                tableEnroll.getItems().remove(selected);
                clearForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delete failed").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    private void clearForm() {
        txtEnrollID.setText(generateNextId());
        txtClientID.clear();
        txtClientName.clear();
        txtContact.clear();
        txtQuatationID.clear();
        txtEmpID.clear();
        txtDescription.clear();
        txtEnrollDate.clear();
        datePicker.setValue(LocalDate.now());
    }

    public void btnGenereteReport(ActionEvent actionEvent) { }

    public void btnReset(ActionEvent actionEvent) {
        clearForm();

    }
}
