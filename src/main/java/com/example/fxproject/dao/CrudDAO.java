package com.example.fxproject.dao;

import com.example.fxproject.entity.Client;
import com.example.fxproject.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO{

    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    boolean save(T dto) throws SQLException, ClassNotFoundException;

    boolean update(T dto) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    String generateNewId() throws SQLException, ClassNotFoundException;

    T search(String id) throws SQLException, ClassNotFoundException;
    int getCount() throws Exception;
}