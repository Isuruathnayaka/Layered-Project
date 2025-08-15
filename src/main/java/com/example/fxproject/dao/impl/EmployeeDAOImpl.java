package com.example.fxproject.dao.impl;

import com.example.fxproject.dao.EmployeeDAO;
import com.example.fxproject.dao.SQLUtil;
import com.example.fxproject.entity.Client;
import com.example.fxproject.entity.Employee;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM employee");
        ArrayList<Employee> e,employees = new ArrayList<>();

        ResultSetMetaData metaData = rst.getMetaData();
        int columnCount = metaData.getColumnCount();

        System.out.println("Columns in ResultSet:");
        for (int i = 1; i <= columnCount; i++) {
            System.out.println(metaData.getColumnName(i));
        }

        while (rst.next()) {
            employees.add(new Employee(
                    rst.getString("emp_id"),
                    rst.getString("emp_name"),
                    rst.getString("emp_phone"),
                    rst.getString("emp_email"),
                    rst.getString("emp_address"),
                    rst.getString("emp_role")
            ));
        }
        return employees;
    }


    @Override
    public boolean save(Employee dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "INSERT INTO employee (emp_id, emp_name, emp_phone, emp_email, emp_address, emp_role) VALUES (?, ?, ?, ?, ?, ?)",
                dto.getEmp_id(),dto.getEmp_name(),dto.getEmp_phone(),dto.getEmp_email(),dto.getEmp_address(),dto.getEmp_role());

    }

    @Override
    public boolean update(Employee dto) throws SQLException, ClassNotFoundException {
      return SQLUtil.executeUpdate(
               "UPDATE employee SET emp_name = ?, emp_phone = ?, emp_email = ?, emp_address = ?, emp_role = ? WHERE emp_id = ?",
               dto.getEmp_name(),
              dto.getEmp_phone(),
              dto.getEmp_email(),
              dto.getEmp_address(),
              dto.getEmp_role(),
               dto.getEmp_id());

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "DELETE FROM employee WHERE emp_id = ?",id
        );
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1";
        ResultSet rst = SQLUtil.executeQuery(sql);

        if (rst.next()) {
            String lastId = rst.getString("emp_id");  // e.g. "C00-006" or "C006"

            // Extract digits only (remove all non-digit characters)
            String numericPart = lastId.replaceAll("\\D", "");  // keeps digits only

            int newId = Integer.parseInt(numericPart) + 1;

            // Format with leading zeros (change pattern if needed)
            return String.format("E%03d", newId);
        } else {
            return "E001";
    }
    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst   =SQLUtil.executeQuery("SELECT * FROM employee WHERE emp_id=?",id);
        if (rst.next()) {
            return new Employee(
                    rst.getString("emp_id"),
                    rst.getString("emp_name"),
                    rst.getString("emp_phone"),
                    rst.getString("emp_email"),
                    rst.getString("emp_address"),
                    rst.getString("emp_role")



            );
        }else {return null;}
    }

    @Override
    public int getCount() throws Exception {
        ResultSet rs = SQLUtil.executeQuery("SELECT COUNT(*) FROM employee");
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
}
