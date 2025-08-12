package com.example.fxproject.bo.custom.impl;

import com.example.fxproject.bo.custom.EnrollBo;
import com.example.fxproject.bo.custom.MapUtill;
import com.example.fxproject.dao.EnrollDAO;
import com.example.fxproject.entity.Enroll;
import com.example.fxproject.model.EnrollDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EnrollBoImpl implements EnrollBo {
    private EnrollDAO enrollDAO;

    public EnrollBoImpl(EnrollDAO enrollDAO) {
        this.enrollDAO = enrollDAO;
    }

    @Override
    public ArrayList<EnrollDTO> getAllEnroll() throws SQLException, ClassNotFoundException {
        ArrayList<EnrollDTO> objects = new ArrayList<>();
        for (Enroll enroll : enrollDAO.getAll()) {
            Object dto = MapUtill.toDTO(enroll);
            objects.add((EnrollDTO) dto);
        }
        return objects;
    }

    @Override
    public boolean saveEnroll(EnrollDTO enrollDTO) throws SQLException, ClassNotFoundException {
        return enrollDAO.save(MapUtill.toEntity(enrollDTO));
    }

    @Override
    public boolean updateEnroll(EnrollDTO enrollDTO) throws SQLException, ClassNotFoundException {
        return enrollDAO.update(MapUtill.toEntity(enrollDTO));
    }

    @Override
    public boolean deleteEnroll(String id) throws SQLException, ClassNotFoundException {
        return enrollDAO.delete(id);
    }

    @Override
    public EnrollDTO searchEnroll(String enrollId) throws SQLException, ClassNotFoundException {
        Enroll enroll = enrollDAO.search(enrollId);
        return (enroll != null) ? (EnrollDTO) MapUtill.toDTO(enroll) : null;
    }

    @Override
    public String generateNewEnrollId() throws SQLException, ClassNotFoundException {
        return enrollDAO.generateNewId();
    }
}
