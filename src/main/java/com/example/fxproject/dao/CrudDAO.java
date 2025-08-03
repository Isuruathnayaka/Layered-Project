package com.example.fxproject.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO{

public ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
public boolean save(T clientDTO);
public boolean update(T clientDTO);
public boolean delete(T clientDTO);
public String generateNewId();
public T search(String id);


}
