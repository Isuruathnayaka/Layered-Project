package com.example.fxproject.controller;

import com.example.fxproject.bo.custom.BOFactory;
import com.example.fxproject.bo.custom.EmployeeBo;
import com.example.fxproject.model.EmployeeDTO;
import com.example.fxproject.view.tdm.EmployeeTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmpController implements Initializable {

    public Label lblEmpID;
    public TextField txtEmpID;
    public Label lblName;
    public TextField txtEmpName;
    public Label lblPhone;
    public TextField txtEmpPhone;
    public Label lblEmail;
    public TextField txtEmpEmail;
    public Label lblAddress;
    public TextField txtEmpAddress;
    public Label lblEmpDuty;
    public TextField txtEmpRole;
    public TableView<EmployeeTM> table;
    public TableColumn<EmployeeTM, String> tabEmpID;
    public TableColumn<EmployeeTM, String> tabName;
    public TableColumn<EmployeeTM, String> tabPhone;
    public TableColumn<EmployeeTM, String> tabEmail;
    public TableColumn<EmployeeTM, String> tabAddress;
    public TableColumn<EmployeeTM, String> tabDuty;

    private final EmployeeBo employeeBo = (EmployeeBo) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabEmpID.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        tabName.setCellValueFactory(new PropertyValueFactory<>("emp_name"));
        tabPhone.setCellValueFactory(new PropertyValueFactory<>("emp_phone"));
        tabEmail.setCellValueFactory(new PropertyValueFactory<>("emp_email"));
        tabAddress.setCellValueFactory(new PropertyValueFactory<>("emp_address"));
        tabDuty.setCellValueFactory(new PropertyValueFactory<>("emp_role"));

        try {
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading employee data.").show();
        }

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, selectedEmp) -> {
            if (selectedEmp != null) {
                txtEmpID.setText(selectedEmp.getEmp_id());
                txtEmpName.setText(selectedEmp.getEmp_name());
                txtEmpPhone.setText(selectedEmp.getEmp_phone());
                txtEmpEmail.setText(selectedEmp.getEmp_email());
                txtEmpAddress.setText(selectedEmp.getEmp_address());
                txtEmpRole.setText(selectedEmp.getEmp_role());
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
        txtEmpName.requestFocus();
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        table.getItems().clear();
        for (EmployeeDTO empDTO : employeeBo.getAllEmployee()) {
            table.getItems().add(new EmployeeTM(
                    empDTO.getEmp_id(),
                    empDTO.getEmp_name(),
                    empDTO.getEmp_phone(),
                    empDTO.getEmp_email(),
                    empDTO.getEmp_address(),
                    empDTO.getEmp_role()
            ));
        }
    }

    private String generateNewId() {
        try {
            return employeeBo.generateNewEmployeeId();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new employee id: " + e.getMessage()).show();
        }
        // fallback id pattern
        return "E001";
    }

    public void btnSave(ActionEvent actionEvent) {
        try {
            EmployeeDTO emp = new EmployeeDTO(
                    txtEmpID.getText(),
                    txtEmpName.getText(),
                    txtEmpPhone.getText(),
                    txtEmpEmail.getText(),
                    txtEmpAddress.getText(),
                    txtEmpRole.getText()
            );
            boolean saved = employeeBo.saveEmployee(emp);
            if (saved) {
                new Alert(Alert.AlertType.INFORMATION, "Employee saved successfully!").show();
                loadTableData();
                initUI();
            } else {
                new Alert(Alert.AlertType.WARNING, "Failed to save employee. Try again.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error saving employee: " + e.getMessage()).show();
        }
    }

    public void btnUpdate(ActionEvent actionEvent) {
        try {
            EmployeeDTO emp = new EmployeeDTO(
                    txtEmpID.getText(),
                    txtEmpName.getText(),
                    txtEmpPhone.getText(),
                    txtEmpEmail.getText(),
                    txtEmpAddress.getText(),
                    txtEmpRole.getText()
            );
            boolean updated = employeeBo.updateEmployee(emp);
            if (updated) {
                new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully!").show();
                loadTableData();
                initUI();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update employee.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error updating employee: " + e.getMessage()).show();
        }
    }

    public void btnDelete(ActionEvent actionEvent) {
        String empId = txtEmpID.getText();
        if (!empId.isEmpty()) {
            try {
                boolean deleted = employeeBo.deleteEmployee(empId);
                if (deleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully!").show();
                    loadTableData();
                    initUI();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete employee.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error deleting employee: " + e.getMessage()).show();
            }
        }
    }

    public void btnReset(ActionEvent actionEvent) {
        initUI();
        try {
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading employee data.").show();
        }
    }

    public void btnGenerateReport(ActionEvent actionEvent) {
        // TODO: implement report generation if needed
    }
}
