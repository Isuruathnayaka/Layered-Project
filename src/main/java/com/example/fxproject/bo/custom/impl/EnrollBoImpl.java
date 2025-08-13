package com.example.fxproject.bo.custom.impl;

import com.example.fxproject.bo.custom.EnrollBo;
import com.example.fxproject.dao.DAOFactory;
import com.example.fxproject.dao.EnrollDAO;
import com.example.fxproject.entity.Enroll;
import com.example.fxproject.model.EnrollDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollBoImpl implements EnrollBo {

    private final EnrollDAO enrollDAO = (EnrollDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ENROLL);

    public EnrollBoImpl(EnrollDAO enrollDAO) {

    }

    @Override
    public List<EnrollDTO> getAllEnroll() throws SQLException, ClassNotFoundException {
        List<EnrollDTO> dtoList = new ArrayList<>();
        for (Enroll e : enrollDAO.getAll()) {
            dtoList.add(new EnrollDTO(
                    e.getEnrollId(),
                    e.getClientId(),
                    e.getClientName(),
                    e.getContact(),
                    e.getQuotationId(),
                    e.getDate(),
                    e.getEmployeeId(),
                    e.getStartingDate(),
                    e.getDescription()
            ));
        }
        return dtoList;
    }

    @Override
    public boolean saveEnroll(EnrollDTO enrollDTO) throws SQLException, ClassNotFoundException {
        Enroll enroll = new Enroll(
                enrollDTO.getEnrollId(),
                enrollDTO.getClientId(),
                enrollDTO.getClientName(),
                enrollDTO.getContact(),
                enrollDTO.getQuotationId(),
                enrollDTO.getDate(),
                enrollDTO.getEmployeeId(),
                enrollDTO.getStartingDate(),
                enrollDTO.getDescription()
        );
        return enrollDAO.save(enroll);
    }

    @Override
    public boolean updateEnroll(EnrollDTO enrollDTO) throws SQLException, ClassNotFoundException {
        Enroll enroll = new Enroll(
                enrollDTO.getEnrollId(),
                enrollDTO.getClientId(),
                enrollDTO.getClientName(),
                enrollDTO.getContact(),
                enrollDTO.getQuotationId(),
                enrollDTO.getDate(),
                enrollDTO.getEmployeeId(),
                enrollDTO.getStartingDate(),
                enrollDTO.getDescription()
        );
        return enrollDAO.update(enroll);
    }

    @Override
    public boolean deleteEnroll(String id) throws SQLException, ClassNotFoundException {
        return enrollDAO.delete(id);
    }

    @Override
    public EnrollDTO searchEnroll(String enrollId) throws SQLException, ClassNotFoundException {
        Enroll e = enrollDAO.search(enrollId);
        if (e != null) {
            return new EnrollDTO(
                    e.getEnrollId(),
                    e.getClientId(),
                    e.getClientName(),
                    e.getContact(),
                    e.getQuotationId(),
                    e.getDate(),
                    e.getEmployeeId(),
                    e.getStartingDate(),
                    e.getDescription()
            );
        }
        return null;
    }

    @Override
    public String generateNewEnrollId() throws SQLException, ClassNotFoundException {
        return enrollDAO.generateNewId();
    }

    @Override
    public EnrollDTO searchLatestEnrollByClientId(String clientId) throws SQLException {
        Enroll enroll = enrollDAO.searchLatestByClientId(clientId);
        return (enroll != null) ? new EnrollDTO(
                enroll.getEnrollId(),
                enroll.getClientId(),
                enroll.getClientName(),
                enroll.getContact(),
                enroll.getQuotationId(),
                enroll.getDate(),
                enroll.getEmployeeId(),
                enroll.getStartingDate(),
                enroll.getDescription()
        ) : null;
    }

}
