package com.example.fxproject.bo.custom.impl;

import com.example.fxproject.bo.custom.MapUtill;
import com.example.fxproject.bo.custom.QuotationBo;
import com.example.fxproject.dao.EmployeeDAO;
import com.example.fxproject.dao.QuotationDAO;
import com.example.fxproject.dao.impl.QuotationDAOImpl;
import com.example.fxproject.entity.Quotation;
import com.example.fxproject.model.EmployeeDTO;
import com.example.fxproject.model.QuotationDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class QuotationBoImpl implements QuotationBo {

    private QuotationDAO quotationDAO;
    public QuotationBoImpl(QuotationDAO quotationDAO) {
        this.quotationDAO=quotationDAO;
    }

    @Override
    public ArrayList<QuotationDTO> getAllQuotation() throws SQLException, ClassNotFoundException {
        ArrayList<QuotationDTO> objects = new ArrayList<>();
        for (Quotation quotation : quotationDAO.getAll()) {
            Object dto = MapUtill.toDTO(quotation);
            objects.add((QuotationDTO) dto);
        }
        return objects;
    }

    @Override
    public boolean saveQuotation(QuotationDTO quotationDTO) throws SQLException, ClassNotFoundException {
        return quotationDAO.save(MapUtill.toEntity(quotationDTO));
    }

    @Override
    public boolean updateQuotation(QuotationDTO quotationDTO) throws SQLException, ClassNotFoundException {
        return quotationDAO.update(MapUtill.toEntity(quotationDTO));
    }

    @Override
    public boolean deleteQuotation(String id) throws SQLException, ClassNotFoundException {
        return quotationDAO.delete(id);
    }

    @Override
    public String generateNewQuotationId() throws SQLException, ClassNotFoundException {
        return quotationDAO.generateNewId();
    }
}
