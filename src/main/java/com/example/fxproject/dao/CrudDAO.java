package com.example.fxproject.dao;

import com.example.fxproject.entity.Client;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO{

    public ArrayList<Client> getAll() throws SQLException, ClassNotFoundException;
    public boolean save(Client customerDTO) throws SQLException, ClassNotFoundException;
    public boolean update(Client customerDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String id) throws SQLException, ClassNotFoundException;
    public String generateNewId() throws SQLException, ClassNotFoundException;
    public Client search(String id) throws SQLException, ClassNotFoundException;


}
