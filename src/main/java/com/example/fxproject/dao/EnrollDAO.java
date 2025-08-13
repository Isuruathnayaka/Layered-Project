package com.example.fxproject.dao;

import com.example.fxproject.entity.Enroll;

import java.sql.SQLException;
import java.util.List;

public interface EnrollDAO extends SuperDAO {
    List<Enroll> getAll() throws SQLException, ClassNotFoundException;
    boolean save(Enroll enroll) throws SQLException, ClassNotFoundException;
    boolean update(Enroll enroll) throws SQLException, ClassNotFoundException;
    boolean delete(String enrollId) throws SQLException, ClassNotFoundException;
    Enroll search(String enrollId) throws SQLException, ClassNotFoundException;
    String generateNewId() throws SQLException, ClassNotFoundException;

    Enroll searchLatestByClientId(String clientId) throws SQLException;
}
