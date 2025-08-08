//package com.example.fxproject.bo.custom.impl;
//
//import com.example.fxproject.bo.custom.EmployeeBo;
//import com.example.fxproject.dao.DAOFactory;
//import com.example.fxproject.entity.Employee;
//import com.example.fxproject.model.EmployeeDTO;
//
//import java.util.ArrayList;
//
//public class EmployeeImpl implements EmployeeBo {
//    private final EmployeeDTO employeeDTO= (EmployeeDTO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);
//    @Override
//    public ArrayList<EmployeeDTO> getAllEmployee() {
//        ArrayList<Employee> employees=employeeDTO.getAll();
//        ArrayList<EmployeeDTO> dtos=new ArrayList<>();
//        for (Employee employee : employees) {
//            dtos.add(new EmployeeDTO(
//                    employee.getEmp_id(),
//                    employee.getEmp_name(),
//                    employee.getEmp_phone(),
//                    employee.getEmp_email(),
//                    employee.getEmp_address(),
//                    employee.getEmp_role()
//            ));
//        }
//        return dtos;
//    }
//
//    @Override
//    public boolean saveEmployee() {
//        return false;
//    }
//
//    @Override
//    public boolean updateEmployee() {
//        return false;
//    }
//
//    @Override
//    public boolean deleteEmployee() {
//        return false;
//    }
//
//    @Override
//    public EmployeeDTO searchEmployee(String employeeId) {
//        return null;
//    }
//
//    @Override
//    public String generateNewEmployeeId() {
//        return "";
//    }
//}
