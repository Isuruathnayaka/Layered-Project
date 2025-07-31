package com.example.fxproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lk.IJSE.javafxapplication.dto.EnrollDTO;
import lk.IJSE.javafxapplication.model.dao.EnrollModel;
import lk.IJSE.javafxapplication.model.dbConnector.dbConnector;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static lk.IJSE.javafxapplication.model.dao.EnrollModel.findAll;

public class EnrollPageController implements Initializable {

    @FXML
    public TextField txtEnrollID;
    @FXML
    public TextField txtClientID;
    @FXML
    public TextField txtClientName;
    @FXML
    public TextField txtContact;
    @FXML
    public TextField txtQuatationID;
    @FXML
    public TextField txtEmpID;
    @FXML
    public TextArea txtDescription;
    @FXML
    public TextField txtEnrollDate;
    @FXML
    public DatePicker datePicker;

    @FXML
    public Button btnEnrollNow;
    @FXML
    public Button btnGenereteReport;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnDelete;


    private final String clientIdPattern = "^C\\d{3}$";
    public TableColumn tableDescriptionCol;
    public TableView tableEnroll;
    public TableColumn colEnrollid;
    public TableColumn colClientID;
    public TableColumn colContact;
    public TableColumn colClientName;
    public TableColumn colQuotationID;
    public TableColumn colDate;
    public TableColumn colEmpID;
    public TableColumn colStartingDate;
    public TableColumn colDescription;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String phonePattern = "^(\\d+)$";
    private final String enrolleIDPattern = "^EN\\d{3}$";
    private final String quotationIDPattern = "^Q{3}$";
    private final String contactIDPattern = "^07\\d{8}$\n";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String nextId = EnrollModel.generateNextEnrollId();
        txtEnrollID.setText(nextId);
        txtEnrollDate.setText(LocalDate.now().toString());
        datePicker.setDayCellFactory(getDayCellFactory());

        colEnrollid.setCellValueFactory(new PropertyValueFactory<>("enrollId"));
        colClientID.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colClientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colQuotationID.setCellValueFactory(new PropertyValueFactory<>("quotationId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmpID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colStartingDate.setCellValueFactory(new PropertyValueFactory<>("startingDate"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            loadClients();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableEnroll.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                setDataToFields((EnrollDTO) newSelection);
                btnEnrollNow.setDisable(true);
            }
        });


        txtClientID.setOnAction(event -> {
            String clientID = txtClientID.getText().trim();
            if (!clientID.matches(clientIdPattern)) {
                new Alert(Alert.AlertType.WARNING, "Invalid Client ID Format (e.g., C001)").show();
                clearForm();
                return;
            }
            loadClientDetails(clientID);
        });
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                // Disable today and past dates
                if (date != null && !empty && (date.isBefore(LocalDate.now()) || date.equals(LocalDate.now()))) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
                }
            }
        });
    }


    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(LocalDate.now().plusDays(1))) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        };
    }

    public void btnEnrollNow(ActionEvent actionEvent) {
        try {
            LocalDate dateValue = datePicker.getValue();
            if (dateValue == null) {
                new Alert(Alert.AlertType.ERROR, "Please select a date!").show();
                return;
            }

            Date sqlDate = Date.valueOf(LocalDate.now());
            Date sqlStartingDate = Date.valueOf(dateValue);

            EnrollDTO dto = new EnrollDTO(
                    txtEnrollID.getText(),
                    txtClientID.getText(),
                    txtClientName.getText(),
                    txtContact.getText(),
                    txtQuatationID.getText(),
                    sqlDate,
                    txtEmpID.getText(),
                    sqlStartingDate,
                    txtDescription.getText()
            );

            boolean saved = EnrollModel.saveEnroll(dto);
            if (saved) {
                new Alert(Alert.AlertType.INFORMATION, "Enroll saved successfully!").show();
                clearForm();
                txtEnrollID.setText(EnrollModel.generateNextEnrollId());
                txtEnrollDate.setText(LocalDate.now().toString());

            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save enroll.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    private void loadClientDetails(String clientId) {
        try (Connection con = dbConnector.getInstance().getConnection()) {
            String clientQuery = "SELECT * FROM client WHERE client_id = ?";
            PreparedStatement pstClient = con.prepareStatement(clientQuery);
            pstClient.setString(1, clientId);
            ResultSet rsClient = pstClient.executeQuery();

            if (rsClient.next()) {
                txtClientName.setText(rsClient.getString("name"));
                txtContact.setText(rsClient.getString("phone"));
            } else {
                clearForm();
                new Alert(Alert.AlertType.WARNING, "Client not found").show();
                return;
            }

            String quoteQuery = "SELECT quotation_id FROM quotation WHERE client_id = ? ORDER BY date DESC LIMIT 1";
            PreparedStatement pstQuote = con.prepareStatement(quoteQuery);
            pstQuote.setString(1, clientId);
            ResultSet rsQuote = pstQuote.executeQuery();

            if (rsQuote.next()) {
                txtQuatationID.setText(rsQuote.getString("quotation_id"));
            } else {
                txtQuatationID.clear();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading client data").show();
        }
    }

    private void clearForm() {
        txtEnrollID.setText(EnrollModel.generateNextEnrollId());
        txtClientID.clear();
        txtEmpID.clear();
        txtDescription.clear();
        datePicker.setValue(null);
        txtClientName.clear();
        txtContact.clear();
        txtQuatationID.clear();
    }

    public void btnReset(ActionEvent actionEvent) {
        clearForm();
    }

    public void btnGenereteReport(ActionEvent actionEvent) {

    }

//    public void btnUpdate(ActionEvent actionEvent, Object dto) throws SQLException {
////        EnrollDTO dto = chackMach();
////        if (dto != null) {
//        boolean isUpdated = EnrollModel.updateEnroll((EnrollDTO) dto);
//        if (isUpdated) {
//            refreshTable();
//            new Alert(Alert.AlertType.INFORMATION, "Client updated successfully.").show();
//        } else {
//            new Alert(Alert.AlertType.ERROR, "Update failed.").show();
//        }
//
//
//    }



    private void refreshTable() throws SQLException {
        List<EnrollDTO> enrollList = findAll();
        ObservableList<EnrollDTO> observableList = FXCollections.observableArrayList(enrollList);
        tableEnroll.setItems(observableList);
    }


    public void btnDelete(ActionEvent actionEvent) {
        EnrollDTO selected = (EnrollDTO) tableEnroll.getSelectionModel().getSelectedItem();

        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this enroll?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait();

            if (confirm.getResult() == ButtonType.YES) {
                boolean isDeleted = EnrollModel.deleteEnroll(selected.getEnrollId());

                if (isDeleted) {
                    tableEnroll.getItems().remove(selected); // Remove from table view
                    clearForm();
                    new Alert(Alert.AlertType.INFORMATION, "Enroll deleted successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete enroll!").show();
                }
            }

        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a row to delete.").show();
        }

    }

    private void loadClients() throws SQLException {
        ObservableList<EnrollDTO> enrollList = FXCollections.observableArrayList(findAll());
        tableEnroll.setItems(enrollList);
    }

    private void setDataToFields(EnrollDTO enrollDTO) {
        txtClientID.setText(enrollDTO.getClientID());
        txtEnrollID.setText(enrollDTO.getEnrollId());
        txtClientName.setText(enrollDTO.getClientName());
        txtContact.setText(enrollDTO.getPhone());
        txtDescription.setText(enrollDTO.getDescription());
        txtEmpID.setText(enrollDTO.getEmployeeId());
        txtQuatationID.setText(enrollDTO.getQuotationId());
        datePicker.setValue(enrollDTO.getStartingDate().toLocalDate());
        txtEnrollDate.setText(LocalDate.now().toString());

    }

    public void btnUpdate(ActionEvent actionEvent) {
        try {
            EnrollDTO dto = new EnrollDTO();
            dto.setEnrollId(txtEnrollID.getText());
            dto.setStartingDate(Date.valueOf(datePicker.getValue()));
            dto.setDescription(txtDescription.getText());

            boolean isUpdated = EnrollModel.updateEnroll(dto);
            if (isUpdated) {
                refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "Client updated successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }


    public void btnPayment(ActionEvent actionEvent) {
    }
}

