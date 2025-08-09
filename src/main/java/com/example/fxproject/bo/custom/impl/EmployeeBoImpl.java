package com.example.fxproject.bo.custom.impl;

import com.example.fxproject.bo.custom.EmployeeBo;
import com.example.fxproject.bo.custom.MapUtill;
import com.example.fxproject.dao.EmployeeDAO;
import com.example.fxproject.entity.Employee;
import com.example.fxproject.model.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EmployeeBoImpl implements EmployeeBo {

    private final EmployeeDAO employeeDAO;

    public EmployeeBoImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        // Directly return the entity list from DAO since method type is <Employee>
        return employeeDAO.getAll()
                .stream()
                .map(MapUtill::toDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(MapUtill.toEntity(employeeDTO));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(MapUtill.toEntity(employeeDTO));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public EmployeeDTO searchEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.search(employeeId);
        return (employee != null) ? MapUtill.toDTO(employee) : null;
    }

    @Override
    public String generateNewEmployeeId() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewId();
    }
}
