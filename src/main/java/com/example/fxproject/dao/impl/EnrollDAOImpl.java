package com.example.fxproject.dao.impl;

import com.example.fxproject.dao.EnrollDAO;
import com.example.fxproject.db.dbConnector;
import com.example.fxproject.entity.Enroll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static com.example.fxproject.db.dbConnector.getConnection;

public class EnrollDAOImpl implements EnrollDAO {

    @Override
    public List<Enroll> getAll() throws SQLException, ClassNotFoundException {
        List<Enroll> list = new ArrayList<>();
        String sql = "SELECT * FROM enroll";

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Enroll(
                        rs.getString("enroll_id"),
                        rs.getString("client_id"),
                        rs.getString("client_name"),
                        rs.getString("contact"),
                        rs.getString("quotation_id"),
                        rs.getDate("date"),
                        rs.getString("employee_id"),
                        rs.getDate("starting_date"),
                        rs.getString("description")
                ));
            }
        }
        return list;
    }

    @Override
    public boolean save(Enroll enroll) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO enroll (enroll_id, client_id, client_name, contact, quotation_id, date, employee_id, starting_date, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, enroll.getEnrollId());
            pst.setString(2, enroll.getClientId());
            pst.setString(3, enroll.getClientName());
            pst.setString(4, enroll.getContact());
            pst.setString(5, enroll.getQuotationId());
            pst.setDate(6, enroll.getDate());
            pst.setString(7, enroll.getEmployeeId());
            pst.setDate(8, enroll.getStartingDate());
            pst.setString(9, enroll.getDescription());

            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Enroll enroll) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE enroll SET starting_date = ?, description = ? WHERE enroll_id = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setDate(1, enroll.getStartingDate());
            pst.setString(2, enroll.getDescription());
            pst.setString(3, enroll.getEnrollId());

            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(String enrollId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM enroll WHERE enroll_id = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, enrollId);
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public Enroll search(String enrollId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM enroll WHERE enroll_id = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, enrollId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Enroll(
                        rs.getString("enroll_id"),
                        rs.getString("client_id"),
                        rs.getString("client_name"),
                        rs.getString("contact"),
                        rs.getString("quotation_id"),
                        rs.getDate("date"),
                        rs.getString("employee_id"),
                        rs.getDate("starting_date"),
                        rs.getString("description")
                );
            }
        }
        return null;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT enroll_id FROM enroll ORDER BY enroll_id DESC LIMIT 1";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String lastId = rs.getString("enroll_id");
                int num = Integer.parseInt(lastId.substring(2)) + 1;
                return String.format("EN%03d", num);
            }
        }
        return "EN001";
    }

    @Override
    public Enroll searchLatestByClientId(String clientId) throws SQLException {
        String sql = "SELECT e.*, c.name AS client_name, c.phone, q.quotation_id " +
                "FROM enroll e " +
                "JOIN client c ON e.client_id = c.client_id " +
                "JOIN quotation q ON e.quotation_id = q.quotation_id " +
                "WHERE e.client_id = ? " +
                "ORDER BY e.date DESC LIMIT 1";

        PreparedStatement pst = getConnection().prepareStatement(sql);
        pst.setString(1, clientId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return new Enroll(
                    rs.getString("enroll_id"),
                    rs.getString("client_id"),
                    rs.getString("client_name"),
                    rs.getString("phone"),
                    rs.getString("quotation_id"),
                    rs.getDate("date"),
                    rs.getString("employee_id"),
                    rs.getDate("starting_date"),
                    rs.getString("description")
            );
        }
        return null;
    }

}
