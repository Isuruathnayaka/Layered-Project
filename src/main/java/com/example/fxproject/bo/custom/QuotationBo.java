package com.example.fxproject.bo.custom;

import com.example.fxproject.model.EmployeeDTO;
import com.example.fxproject.model.QuotationDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QuotationBo extends SuperBO{
    public ArrayList<QuotationDTO> getAllQuotation() throws SQLException, ClassNotFoundException;
    public boolean saveQuotation(QuotationDTO quotationDTO) throws SQLException, ClassNotFoundException;

    public boolean updateQuotation(QuotationDTO quotationDTO) throws SQLException, ClassNotFoundException;

    public boolean deleteQuotation(String id) throws SQLException, ClassNotFoundException;

}
