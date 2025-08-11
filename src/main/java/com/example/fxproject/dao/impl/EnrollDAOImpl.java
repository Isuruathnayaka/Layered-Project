package com.example.fxproject.dao.impl;

import com.example.fxproject.dao.EnrollDAO;
import com.example.fxproject.dao.SQLUtil;
import com.example.fxproject.db.dbConnector;
import com.example.fxproject.entity.Enroll;
import com.example.fxproject.model.EnrollDTO;
import com.example.fxproject.model.EnrollQuotationDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnrollDAOImpl implements EnrollDAO {



    @Override
    public ArrayList<Enroll> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst =SQLUtil.executeQuery("select * from enroll");
        ArrayList<Enroll> enrolls = new ArrayList<>();

        ResultSetMetaData metaData = rst.getMetaData();
        int columnCount = metaData.getColumnCount();

        System.out.println("Columns in ResultSet: " + columnCount);
        for (int i = 1; i <= columnCount; i++) {
            System.out.println(metaData.getColumnName(i));
        }
        while (rst.next()) {
            enrolls.add(new Enroll(
                    rst.getString("enroll_id"),
                    rst.getString("client_id"),
                    rst.getString("enroll_name"),
                    rst.getString("client_name"),
                    rst.getString("contact"),
                    rst.getString("quotation_id"),
                    rst.getString("date"),
                    rst.getString("employee_id"),
                    rst.getString("starting_date"),
                    rst.getString("description")
            ));
        }
     return enrolls;
    }

    @Override
    public boolean save(Enroll dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "INSERT INTO enroll (enroll_id, client_id, client_name, contact, quotation_id, date, employee_id, starting_date, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                dto.getEnrollId(),dto.getClientID(),dto.getClientName(),dto.getContact(),dto.getQuotationId(),dto.getDate(),dto.getEmployeeId(),dto.getStartingDate(),dto.getDescription()
        );
    }

    @Override
    public boolean update(Enroll dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "UPDATE enroll SET starting_date = ?, description = ? WHERE enroll_id = ?",
                dto.getClientID(),dto.getClientName(),dto.getContact(),dto.getQuotationId(),dto.getDate(),dto.getEmployeeId(),dto.getStartingDate(),dto.getDescription(),dto.getEnrollId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "DELETE FROM enroll WHERE enroll_id = ?"

        );
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT enroll_id FROM enroll ORDER BY enroll_id DESC LIMIT 1";
        ResultSet rst = SQLUtil.executeQuery(sql);

        if (rst.next()) {
            String lastId = rst.getString("enroll_id");


            String numericPart = lastId.replaceAll("\\D", "");

            int newId = Integer.parseInt(numericPart) + 1;


            return String.format("EN%03d", newId);
        } else {
            return "EN001";
        }
    }

    @Override
    public Enroll search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
