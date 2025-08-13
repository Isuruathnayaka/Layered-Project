package com.example.fxproject.bo.custom;

import com.example.fxproject.model.EnrollDTO;

import java.sql.SQLException;
import java.util.List;

public interface EnrollBo extends SuperBO {
    List<EnrollDTO> getAllEnroll() throws SQLException, ClassNotFoundException;
    boolean saveEnroll(EnrollDTO enrollDTO) throws SQLException, ClassNotFoundException;
    boolean updateEnroll(EnrollDTO enrollDTO) throws SQLException, ClassNotFoundException;
    boolean deleteEnroll(String id) throws SQLException, ClassNotFoundException;
    EnrollDTO searchEnroll(String enrollID) throws SQLException, ClassNotFoundException;
    String generateNewEnrollId() throws SQLException, ClassNotFoundException;

    EnrollDTO searchLatestEnrollByClientId(String clientId) throws SQLException;
}
