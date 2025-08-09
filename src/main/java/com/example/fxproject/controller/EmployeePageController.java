package com.example.fxproject.controller;

import com.example.fxproject.bo.custom.BOFactory;
import com.example.fxproject.bo.custom.EmployeeBo;
import com.example.fxproject.model.EmployeeDTO;
import com.example.fxproject.view.tdm.EmployeeTM;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeePageController implements Initializable {
    @FXML
    public Label lblEmpID, lblName, lblPhone, lblEmail, lblAddress, lblDuty;
    @FXML
    public TextField txtEmpID, txtEmpName, txtEmpPhone, txtEmpEmail, txtEmpAddress, txtEmpRole;
     @FXML
    public TableColumn<EmployeeTM, String> tabEmpID, tabName, tabPhone, tabEmail, tabAddress, tabDuty;
   @FXML
    public TableView<EmployeeTM> table;
  @FXML
    public Button btnSave, btnUpdate, btnDelete, btnReset;

    private final String namePattern  = "^[A-Za-z ]+$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String phonePattern = "^(\\d+)$";

    private final EmployeeBo employeeBo = (EmployeeBo) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);
    @FXML
    public Label lblEmpDuty;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabEmpID.setCellValueFactory(new PropertyValueFactory<>("emp_Id"));
        tabName.setCellValueFactory(new PropertyValueFactory<>("emp_ame"));
        tabPhone.setCellValueFactory(new PropertyValueFactory<>("emp_phone"));
        tabEmail.setCellValueFactory(new PropertyValueFactory<>("emp_email"));
        tabAddress.setCellValueFactory(new PropertyValueFactory<>("emp_address"));
        tabDuty.setCellValueFactory(new PropertyValueFactory<>("emp_role"));

//        try {
//           // loadTableData();
//        } catch (Exception e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Error loading employee data.").show();
//        }

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, selectedEmp) -> {
            if (selectedEmp != null) {
                txtEmpID.setText(selectedEmp.getEmp_id());
                txtEmpName.setText(selectedEmp.getEmp_name());
                txtEmpPhone.setText(selectedEmp.getEmp_phone());
                txtEmpEmail.setText(selectedEmp.getEmp_email());
                txtEmpAddress.setText(selectedEmp.getEmp_address());
                txtEmpRole.setText(selectedEmp.getEmp_role());
                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            }
        });

        initUI();
    }

    private void initUI() {
        txtEmpID.setText(generateNewId());
        txtEmpName.clear();
        txtEmpPhone.clear();
        txtEmpEmail.clear();
        txtEmpAddress.clear();
        txtEmpRole.clear();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        table.getItems().clear();

        ArrayList<EmployeeDTO> allEmployees = employeeBo.getAllEmployee();
        for (EmployeeDTO dto : allEmployees) {
            table.getItems().add(new EmployeeTM(
                    dto.getEmp_id(),
                    dto.getEmp_name(),
                    dto.getEmp_phone(),
                    dto.getEmp_email(),
                    dto.getEmp_address(),
                    dto.getEmp_role()
            ));
        }
    }

    private String generateNewId() {
        try {
            return employeeBo.generateNewEmployeeId();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate new ID: " + e.getMessage()).show();
            if (table.getItems().isEmpty()) {
                return "E001";
            } else {
                return getLastEmployeeId();
            }
        }
    }

    private String getLastEmployeeId() {
        List<EmployeeTM> tempList = new ArrayList<>(table.getItems());
        Collections.sort(tempList, (a, b) -> a.getEmp_id().compareTo(b.getEmp_id()));
        return tempList.get(tempList.size() - 1).getEmp_id();
    }

    private EmployeeDTO validateAndGetEmployeeDTO() {
        boolean valid = true;

        if (!txtEmpName.getText().matches(namePattern)) {
            txtEmpName.setStyle("-fx-border-color: red;");
            valid = false;
        } else {
            txtEmpName.setStyle(null);
        }

        if (!txtEmpEmail.getText().matches(emailPattern)) {
            txtEmpEmail.setStyle("-fx-border-color: red;");
            valid = false;
        } else {
            txtEmpEmail.setStyle(null);
        }

        if (!txtEmpPhone.getText().matches(phonePattern)) {
            txtEmpPhone.setStyle("-fx-border-color: red;");
            valid = false;
        } else {
            txtEmpPhone.setStyle(null);
        }

        if (txtEmpAddress.getText().isEmpty()) {
            txtEmpAddress.setStyle("-fx-border-color: red;");
            valid = false;
        } else {
            txtEmpAddress.setStyle(null);
        }

        if (txtEmpRole.getText().isEmpty()) {
            txtEmpRole.setStyle("-fx-border-color: red;");
            valid = false;
        } else {
            txtEmpRole.setStyle(null);
        }

        if (!valid) {
            new Alert(Alert.AlertType.WARNING, "Please correct highlighted fields").show();
            return null;
        }

        return new EmployeeDTO(
                txtEmpID.getText(),
                txtEmpName.getText(),
                txtEmpPhone.getText(),
                txtEmpEmail.getText(),
                txtEmpAddress.getText(),
                txtEmpRole.getText()
        );
    }

    public void btnSave(ActionEvent actionEvent) {
        EmployeeDTO dto = validateAndGetEmployeeDTO();
        if (dto == null) return;

        try {
            boolean saved = employeeBo.saveEmployee(dto);
            if (saved) {
                new Alert(Alert.AlertType.INFORMATION, "Employee saved successfully").show();
                initUI();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save employee").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error saving employee: " + e.getMessage()).show();
        }
    }

    public void btnUpdate(ActionEvent actionEvent) {
        EmployeeDTO dto = validateAndGetEmployeeDTO();
        if (dto == null) return;

        try {
            boolean updated = employeeBo.updateEmployee(dto);
            if (updated) {
                new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully").show();
                initUI();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update employee").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error updating employee: " + e.getMessage()).show();
        }
    }

    public void btnDelete(ActionEvent actionEvent) {
        String empId = txtEmpID.getText();
        if (empId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select an employee to delete").show();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this employee?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            try {
                boolean deleted = employeeBo.deleteEmployee(empId);
                if (deleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully").show();
                    initUI();
                    loadTableData();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete employee").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error deleting employee: " + e.getMessage()).show();
            }
        }
    }

    public void btnReset(ActionEvent actionEvent) {
        initUI();
        try {
            loadTableData();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load employee data: " + e.getMessage()).show();
        }
    }



    public void btnGenerateReport(ActionEvent actionEvent) {
    }
}
