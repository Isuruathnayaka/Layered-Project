package com.example.fxproject.dao.impl;

import com.example.fxproject.dao.CrudDAO;
import com.example.fxproject.dao.SQLUtil;
import com.example.fxproject.entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAOImpl implements CrudDAO<Client> {

    @Override
    public ArrayList<Client> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Client");
        ArrayList<Client> clients = new ArrayList<>();
        while (rst.next()) {
            Client client = new Client(
                    rst.getString("id"),
                    rst.getString("name"),
                    rst.getString("phone"),
                    rst.getString("email"),
                    rst.getString("address")
            );
            clients.add(client);
        }
        return clients;
    }

    @Override
    public boolean save(Client entity) throws ClassNotFoundException, SQLException {
        return SQLUtil.executeUpdate(
                "INSERT INTO Client (id, name, phone, email, address) VALUES (?, ?, ?, ?, ?)",
                entity.getId(), entity.getName(), entity.getPhone(), entity.getEmail(), entity.getAddress()
        );
    }

    @Override
    public boolean update(Client entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "UPDATE Client SET name=?, phone=?, email=?, address=? WHERE id=?",
                entity.getName(), entity.getPhone(), entity.getEmail(), entity.getAddress(), entity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Client WHERE id=?", id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT id FROM Client ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newId);
        } else {
            return "C00-001";
        }
    }

    @Override
    public Client search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Client WHERE id=?", id);
        if (rst.next()) {
            return new Client(
                    rst.getString("id"),
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
