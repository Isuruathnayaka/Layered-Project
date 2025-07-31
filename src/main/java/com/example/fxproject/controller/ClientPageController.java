package com.example.fxproject.controller;


import com.example.fxproject.model.ClientDTO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

class ClientPageController {

    public Label lblClientID;
    public TextField txtClientID;
    public Label lblName;
    public TextField txtName;
    public Label lblPhone;
    public TextField txtPhone;
    public Label lblEmail;
    public TextField txtEmail;
    public Label lblAddress;
    public TextField txtAddress;
    public TableColumn columnClientID;
    public TableColumn columnName;
    public TableColumn ColumnPhone;
    public TableColumn ColumnEmail;
    public TableColumn columnAddress;
    public Button generateBtn;
    public Button resetBtn;
    public Button saveBtn;
    public Button updateBtn;
    public Button deleteBtn;
    public Label lblEnrolledClientCount;
    public Label lblClientRegisterCount;
    public TextField txtSearch;
    public TextField txtQuoID1;
    public TextField txtQuoID;
    public AnchorPane ancQuotationGenerate;
    public AnchorPane ancDarkPane;
    public Button btnQuotationGenerate;
    public TableView table;

    public void initialize() {
        loadTableData();

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                setDataToFields(newSelection);
                saveBtn.setDisable(true);
            }
        });

        columnClientID.setCellValueFactory(new PropertyValueFactory<>("clientID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        ColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

    }

    private void setDataToFields(Object newSelection) {
    }

    private void loadTableData() {
    }
    public ClientDTO chackMach() {
        String clientID = txtClientID.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();

//        boolean isValidName = name.matches(namePattern);
//        boolean isValidPhone = phone.matches(phonePattern);
//        boolean isValidEmail = email.matches(emailPattern);

        txtName.setStyle("-fx-border-color: #7367F0;");
        txtPhone.setStyle("-fx-border-color: #7367F0;");
        txtEmail.setStyle("-fx-border-color: #7367F0;");
        txtAddress.setStyle("-fx-border-color: #7367F0;");

//        if (!isValidName) txtName.setStyle("-fx-border-color: red;");
//        if (!isValidPhone) txtPhone.setStyle("-fx-border-color: red;");
//        if (!isValidEmail) txtEmail.setStyle("-fx-border-color: red;");
//
//        if (isValidName && isValidPhone && isValidEmail) {
//            return new ClientDTO(clientID, name, phone, email, address);
//        }
        return null;
    }


    public void btnGenerateReport(ActionEvent actionEvent) {
    }

    public void btnReset(ActionEvent actionEvent) {
    }

    public void btnSave(ActionEvent actionEvent) {


    }

    public void btnUpdate(ActionEvent actionEvent) {
    }

    public void btnDeleete(ActionEvent actionEvent) {
    }

    public void btnSearch(ActionEvent actionEvent) {
    }

    public void btnClientManageToEnroll(ActionEvent actionEvent) {
    }

    public void btnQuotationGenerate(ActionEvent actionEvent) {
    }

    public void QuoBtnEntered(MouseEvent mouseEvent) {
    }

    public void QuoBtnExited(MouseEvent mouseEvent) {
    }

    public void generateCancleBtn(ActionEvent actionEvent) {
    }

    public void FinalReportGenerate(ActionEvent actionEvent) {
    }
}