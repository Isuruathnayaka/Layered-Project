package com.example.fxproject.dao.impl;

import com.example.fxproject.dao.ClientDAO;
import com.example.fxproject.dao.SQLUtil;
import com.example.fxproject.entity.Client;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAOImpl implements ClientDAO {

    @Override
    public ArrayList<Client> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM client");
        ArrayList<Client> clients = new ArrayList<>();

        ResultSetMetaData metaData = rst.getMetaData();
        int columnCount = metaData.getColumnCount();

        System.out.println("Columns in ResultSet:");
        for (int i = 1; i <= columnCount; i++) {
            System.out.println(metaData.getColumnName(i));
        }

        while (rst.next()) {
            clients.add(new Client(
                    rst.getString("client_id"),
                    rst.getString("name"),
                    rst.getString("phone"),
                    rst.getString("email"),
                    rst.getString("address")
            ));
        }
        return clients;
    }

    @Override
    public boolean save(Client entity) throws ClassNotFoundException, SQLException {
        return SQLUtil.executeUpdate(
                "INSERT INTO client (client_id, name, phone, email, address) VALUES (?, ?, ?, ?, ?)",
                entity.getClient_id(), entity.getName(), entity.getPhone(), entity.getEmail(), entity.getAddress()
        );
    }

    @Override
    public boolean update(Client entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "UPDATE client SET name=?, phone=?, email=?, address=? WHERE client_id=?",
                entity.getName(), entity.getPhone(), entity.getEmail(), entity.getAddress(), entity.getClient_id()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM client WHERE client_id=?", id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT client_id FROM client ORDER BY client_id DESC LIMIT 1";
        ResultSet rst = SQLUtil.executeQuery(sql);

        if (rst.next()) {
            String lastId = rst.getString("client_id");  // e.g. "C00-006" or "C006"

            // Extract digits only (remove all non-digit characters)
            String numericPart = lastId.replaceAll("\\D", "");  // keeps digits only

            int newId = Integer.parseInt(numericPart) + 1;

            // Format with leading zeros (change pattern if needed)
            return String.format("C%03d", newId);
        } else {
            return "C001";
        }

    }

    @Override
    public Client search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM client WHERE client_id=?", id);
        if (rst.next()) {
            return new Client(
                    rst.getString("client_id"),
                    rst.getString("name"),
                    rst.getString("phone"),
                    rst.getString("email"),
                    rst.getString("address")
            );
        } else {
            return null;
        }
    }


}
