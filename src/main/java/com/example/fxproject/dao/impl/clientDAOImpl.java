package com.example.fxproject.dao.impl;

import com.example.fxproject.dao.ClientDAO;
import com.example.fxproject.dao.SQLUtil;
import com.example.fxproject.model.ClientDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class clientDAOImpl implements ClientDAO {

    @Override
    public ArrayList<ClientDTO> getAll() throws SQLException, ClassNotFoundException {

            ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Client");
            ArrayList<ClientDTO> customers = new ArrayList<>();
            while (rst.next()) {
                ClientDTO customerDTO = new ClientDTO(rst.getString("id"), rst.getString("name"), rst.getString("address"));
                customers.add(customerDTO);
            }
            return customers;
        }


    @Override
    public boolean save(ClientDTO clientDTO) {
        return false;
    }

    @Override
    public boolean update(ClientDTO clientDTO) {
        return false;
    }

    @Override
    public boolean delete(ClientDTO clientDTO) {
        return false;
    }

    @Override
    public String generateNewId() {
        return "";
    }

    @Override
    public ClientDTO search(String id) {
        return null;
    }
}
