package com.example.fxproject.bo.custom;

import com.example.fxproject.model.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBo extends SuperBO {
 public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException;

public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;

 EmployeeDTO searchEmployee(String employeeId) throws SQLException, ClassNotFoundException;

 String generateNewEmployeeId() throws SQLException, ClassNotFoundException;


}
