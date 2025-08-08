//package com.example.fxproject.controller;
//
//import com.example.fxproject.bo.custom.BOFactory;
//import com.example.fxproject.bo.custom.ClientBo;
//import com.example.fxproject.bo.custom.EmployeeBo;
//import com.example.fxproject.view.tdm.EmployeeTM;
//import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import lk.IJSE.javafxapplication.dto.EmpDTO;
//import lk.IJSE.javafxapplication.model.dao.EmployeeModel;
//import lk.IJSE.javafxapplication.model.dbConnector.dbConnector;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.engine.design.JRDesignQuery;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import net.sf.jasperreports.view.JasperViewer;
//
//import java.io.InputStream;
//import java.net.URL;
//import java.sql.Connection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class EmployeePageController implements Initializable {
//
//    @FXML
//    private Label lblAddress, lblEmail, lblEmpDuty, lblEmpID, lblName, lblPhone;
//    @FXML
//    private TableColumn<?, ?> tabAddress, tabDuty, tabEmaail, tabEmpID, tabName, tabPhone;
//    @FXML
//    private TableView<EmployeeTM> table;
//    @FXML
//    private TextField txtEmpID, txtEmpName, txtEmpPhone, txtEmpEmail, txtEmpAddress, txtEmpRole;
//    @FXML
//    private Button btnSave;
//
//    private final String namePattern  = "^[A-Za-z ]+$";
//    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
//    private final String phonePattern = "^(?:0|94)?7\\d{8}$";      // e.g. 0771234567 or 94771234567
//
//    EmployeeBo employeeBo = (EmployeeBo) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//
//        // Initialize table
//        tabEmpID.setCellValueFactory(new PropertyValueFactory<>("empId"));
//        tabName.setCellValueFactory(new PropertyValueFactory<>("empName"));
//        tabPhone.setCellValueFactory(new PropertyValueFactory<>("empPhone"));
//        tabAddress.setCellValueFactory(new PropertyValueFactory<>("empEmail"));
//        tabDuty.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
//        tabEmaail.setCellValueFactory(new PropertyValueFactory<>("empRole"));
//
//        table.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
//            if (n != null) {
//                setDataToFields(n);
//                btnSave.setDisable(true);
//            }
//        });
//    }
//
//
//    @FXML
//    private void btnSave(ActionEvent event) {
//        if (!validateInputs()) return;
//
//        EmpDTO dto = new EmpDTO(
//                txtEmpID.getText(),
//                txtEmpName.getText(),
//                txtEmpPhone.getText(),
//                txtEmpEmail.getText(),
//                txtEmpAddress.getText(),
//                txtEmpRole.getText()
//        );
//
//        if (EmployeeModel.saveEmployee(dto)) {
//            new Alert(Alert.AlertType.INFORMATION, "Employee saved!").show();
//            loadEmployeeTable();
//            clearFields();
//            txtEmpID.setText(EmployeeModel.generateNextEmployeeId());
//        } else {
//            new Alert(Alert.AlertType.ERROR, "Failed to save employee").show();
//        }
//    }
//
//    public void btnUpdate(ActionEvent actionEvent) {
//        if (!validateInputs()) return;
//
//        EmpDTO dto = new EmpDTO(
//                txtEmpID.getText(),
//                txtEmpName.getText(),
//                txtEmpPhone.getText(),
//                txtEmpEmail.getText(),
//                txtEmpAddress.getText(),
//                txtEmpRole.getText()
//        );
//
//        if (EmployeeModel.updateEmployee(dto)) {
//            loadEmployeeTable();
//            new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully!").show();
//        } else {
//            new Alert(Alert.AlertType.ERROR, " Update failed!").show();
//        }
//    }
//
//    public void btnReset(ActionEvent actionEvent) { clearForm(); }
//
//    public void btnDelete(ActionEvent actionEvent) {
//        String empId = txtEmpID.getText();
//        if (empId.isEmpty()) {
//            new Alert(Alert.AlertType.WARNING, "Please enter or select an Employee ID").show();
//            return;
//        }
//        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
//                "Are you sure you want to delete this employee?", ButtonType.YES, ButtonType.NO);
//        confirm.showAndWait();
//        if (confirm.getResult() == ButtonType.YES) {
//            if (EmployeeModel.deleteEmployee(empId)) {
//                new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully!").show();
//                clearFields();
//                loadEmployeeTable();
//            } else {
//                new Alert(Alert.AlertType.ERROR, " Failed to delete employee!").show();
//            }
//        }
//    }
//
//    public void btnGenerateReport(ActionEvent actionEvent) {
//        try {
//            InputStream is = getClass().getResourceAsStream("/reports/EmployeeReport_1.jrxml");
//            if (is == null) throw new RuntimeException("Report file not found at /reports/EmployeeReport_1.jrxml");
//            JasperDesign design = JRXmlLoader.load(is);
//            JRDesignQuery query = new JRDesignQuery();
//            query.setText("SELECT * FROM employee");
//            design.setQuery(query);
//            JasperReport report = JasperCompileManager.compileReport(design);
//            Connection connection = dbConnector.getConnection();
//            JasperPrint print = JasperFillManager.fillReport(report, new HashMap<>(), connection);
//            JasperViewer.viewReport(print, false);
//        } catch (Exception e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Failed to generate employee report: " + e.getMessage()).show();
//        }
//    }
//
//
//    private void loadAllEmployees() {
//        List<EmpDTO> employees = EmployeeModel.getAllEmployees();
//        table.setItems(FXCollections.observableArrayList(employees));
//    }
//
//    private void loadEmployeeTable() {
//        try {
//            List<EmpDTO> employees = EmployeeModel.getAllEmployees();
//            table.setItems(FXCollections.observableArrayList(employees));
//        } catch (Exception e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, " Failed to load employee data!").show();
//        }
//    }
//
//    private void setDataToFields(EmpDTO e) {
//        txtEmpID.setText(e.getEmpId());
//        txtEmpName.setText(e.getEmpName());
//        txtEmpPhone.setText(e.getEmpPhone());
//        txtEmpEmail.setText(e.getEmpEmail());
//        txtEmpAddress.setText(e.getEmpAddress());
//        txtEmpRole.setText(e.getEmpRole());
//    }
//
//    private void clearFields() {
//        txtEmpID.setText(EmployeeModel.generateNextEmployeeId());
//        txtEmpName.clear();
//        txtEmpPhone.clear();
//        txtEmpEmail.clear();
//        txtEmpAddress.clear();
//        txtEmpRole.clear();
//        resetStyles();
//    }
//
//    private void clearForm() {
//        txtEmpName.clear();
//        txtEmpPhone.clear();
//        txtEmpAddress.clear();
//        txtEmpRole.clear();
//        txtEmpEmail.clear();
//        resetStyles();
//        btnSave.setDisable(false);
//    }
//
//
//    private boolean validateInputs() {
//        resetStyles();
//        boolean ok = true;
//
//        if (!txtEmpName.getText().matches(namePattern)) {
//            highlightError(txtEmpName);
//            ok = false;
//        }
//        if (!txtEmpEmail.getText().matches(emailPattern)) {
//            highlightError(txtEmpEmail);
//            ok = false;
//        }
//        if (!txtEmpPhone.getText().matches(phonePattern)) {
//            highlightError(txtEmpPhone);
//            ok = false;
//        }
//        if (txtEmpAddress.getText().isBlank()) {
//            highlightError(txtEmpAddress);
//            ok = false;
//        }
//        if (txtEmpRole.getText().isBlank()) {
//            highlightError(txtEmpRole);
//            ok = false;
//        }
//
//        if (!ok) {
//            new Alert(Alert.AlertType.WARNING, " Please correct highlighted fields.").show();
//        }
//        return ok;
//    }
//
//    private void highlightError(TextField tf) {
//        tf.setStyle("-fx-border-color: red;");
//    }
//
//    private void resetStyles() {
//        txtEmpName.setStyle("-fx-border-color: #7367F0;");
//        txtEmpPhone.setStyle("-fx-border-color: #7367F0;");
//        txtEmpEmail.setStyle("-fx-border-color: #7367F0;");
//        txtEmpAddress.setStyle("-fx-border-color: #7367F0;");
//        txtEmpRole.setStyle("-fx-border-color: #7367F0;");
//    }
//}
