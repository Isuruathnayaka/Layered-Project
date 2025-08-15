package com.example.fxproject.bo.custom;

import com.example.fxproject.model.EnrollQuotationDTO;
import com.example.fxproject.model.PaymentDTO;
import com.example.fxproject.model.EnrollDTO;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO{
    EnrollDTO getQuotationDetailsByEnrollId(String enrollId) throws SQLException, ClassNotFoundException;

    List<PaymentDTO> loadAllPayments() throws SQLException, ClassNotFoundException;

    boolean payAdvance(PaymentDTO payment) throws SQLException, ClassNotFoundException;

    boolean payFull(PaymentDTO payment) throws SQLException, ClassNotFoundException;

    int getPaymentCount() throws SQLException, ClassNotFoundException;


    EnrollQuotationDTO getQuotationDetails(String enrollId) throws SQLException, ClassNotFoundException;
}
