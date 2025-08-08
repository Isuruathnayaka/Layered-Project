package com.example.fxproject.controller;

import com.example.fxproject.bo.custom.BOFactory;
import com.example.fxproject.bo.custom.ClientBo;
import com.example.fxproject.model.ClientDTO;
import com.example.fxproject.view.tdm.ClientTM;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class ClientPageController implements Initializable {

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
    public TableColumn<ClientTM, String> columnClientID;
    public TableColumn<ClientTM, String> columnName;
    public TableColumn<ClientTM, String> ColumnPhone;
    public TableColumn<ClientTM, String> ColumnEmail;
    public TableColumn<ClientTM, String> columnAddress;
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
    public TableView<ClientTM> table;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String phonePattern = "^(\\d+)$";

    ClientBo clientBo = (ClientBo) BOFactory.getInstance().getBO(BOFactory.BOType.CLIENT);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        columnClientID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        ColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading table data.").show();
        }

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, selectedClient) -> {
            if (selectedClient != null) {
                txtClientID.setText(selectedClient.getId());
                txtName.setText(selectedClient.getName());
                txtPhone.setText(selectedClient.getPhone());
                txtEmail.setText(selectedClient.getEmail());
                txtAddress.setText(selectedClient.getAddress());

            }
        });

        initUI();

    }

    private void initUI() {
        txtClientID.setText(generateNewId());
        txtName.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtSearch.clear();
        txtQuoID1.clear();
        txtQuoID.clear();
        txtName.requestFocus();
        saveBtn.setDisable(false);
    }

    private void setDataToFields(ClientTM client) {
        txtName.setText(client.getName());
        txtPhone.setText(client.getPhone());
        txtEmail.setText(client.getEmail());
        txtAddress.setText(client.getAddress());
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        table.getItems().clear();

        ArrayList<ClientDTO> allClients = clientBo.getAllCustomer();
        for (ClientDTO clientDTO : allClients) {
            // Fix: Pass parameters in the correct order: id, name, address, phone, email
            table.getItems().add(new ClientTM(
                    (String) clientDTO.getId(),
                    clientDTO.getName(),
                    clientDTO.getPhone(),
                    clientDTO.getEmail(),
                    clientDTO.getAddress()  // <-- address here
                       // <-- phone here
                        // <-- email here
            ));
        }
    }

    public ClientDTO checkMatch() {
        String clientID = txtClientID.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();

        boolean isValidName = name.matches(namePattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidEmail = email.matches(emailPattern);

        txtName.setStyle("-fx-border-color: #7367F0;");
        txtPhone.setStyle("-fx-border-color: #7367F0;");
        txtEmail.setStyle("-fx-border-color: #7367F0;");
        txtAddress.setStyle("-fx-border-color: #7367F0;");

        if (!isValidName) txtName.setStyle("-fx-border-color: red;");
        if (!isValidPhone) txtPhone.setStyle("-fx-border-color: red;");
        if (!isValidEmail) txtEmail.setStyle("-fx-border-color: red;");

        if (isValidName && isValidPhone && isValidEmail) {
            return new ClientDTO(clientID, name, phone, email, address);
        }
        return null;
    }

    private String generateNewId() {
        try {
            return clientBo.generateNewCustomerId();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        }

        if (table.getItems().isEmpty()) {
            return "C001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        }
    }

    private String getLastCustomerId() {
        List<ClientTM> tempCustomersList = new ArrayList<>(table.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getId();
    }

    public void btnGenerateReport(ActionEvent actionEvent) {
        // TODO: implement report generation
    }

    public void btnReset(ActionEvent actionEvent) {
        initUI();
    }

    public void btnSave(ActionEvent actionEvent) {
        try {
            // Validate and get DTO from your method
            ClientDTO clientDTO = checkMatch();

            if (clientDTO == null) {
                // If validation fails, checkMatch() should already show the alert
                return;
            }

            // Call BO layer to save
            boolean isSaved = clientBo.saveClient(clientDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "saved successfully!").show();
                loadTableData(); // Reload table from DB
                initUI();        // Clear input fields
            } else {
                new Alert(Alert.AlertType.WARNING, " Save failed. Please try again.").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, " Error occurred while saving client: " + e.getMessage()).show();
        }
    }

    public void btnUpdate(ActionEvent actionEvent) {
        ClientDTO clientDTO = checkMatch();
        if (clientDTO != null) {
            try {
                boolean isUpdated = clientBo.updateClient(clientDTO);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Client updated successfully.").show();
                    loadTableData();
                    initUI();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update client.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while updating client.").show();
            }
        }
    }

    public void btnDelete(ActionEvent actionEvent) {
        String clientID = txtClientID.getText();
        if (!clientID.isEmpty()) {
            try {
                boolean isDeleted = clientBo.deleteClient(clientID);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Client deleted successfully.").show();
                    loadTableData();
                    initUI();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete client.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting client.").show();
            }
        }
    }

    public void btnSearch(ActionEvent actionEvent) {
        String clientID = txtSearch.getText();
        if (!clientID.isEmpty()) {
            try {
                ClientDTO client = clientBo.searchClient(clientID);
                if (client != null) {
                    setDataToFields(new ClientTM(
                            (String) client.getId(),
                            client.getName(),
                            client.getPhone(),
                            client.getEmail(),
                            client.getAddress()
                    ));
                    saveBtn.setDisable(true);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Client not found.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while searching client.").show();
            }
        }
    }

    public void btnClientManageToEnroll(ActionEvent actionEvent) {
        // TODO: navigate to enroll screen
    }

    public void btnQuotationGenerate(ActionEvent actionEvent) {
        // TODO: implement quotation generation
    }

    public void QuoBtnEntered(MouseEvent mouseEvent) {
        // TODO: implement hover effect
    }

    public void QuoBtnExited(MouseEvent mouseEvent) {
        // TODO: implement hover effect end
    }

    public void generateCancelBtn(ActionEvent actionEvent) {
        // TODO: implement cancel action
    }

    public void FinalReportGenerate(ActionEvent actionEvent) {
        // TODO: implement final report generation
    }

    public void btnDeleete(ActionEvent actionEvent) {
        String clientID = txtClientID.getText();
        if (!clientID.isEmpty()) {
            try {
                boolean isDeleted = clientBo.deleteClient(clientID);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Client deleted successfully.").show();
                    loadTableData();
                    initUI();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete client.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting client.").show();
            }
        }


    }

    public void generateCancleBtn(ActionEvent actionEvent) {
    }
}
