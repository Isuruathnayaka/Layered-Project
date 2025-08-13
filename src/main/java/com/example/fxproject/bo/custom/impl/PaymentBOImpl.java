package com.example.fxproject.bo.custom.impl;

import com.example.fxproject.bo.custom.PaymentBO;
import com.example.fxproject.dao.DAOFactory;
import com.example.fxproject.dao.PaymentDAO;
import com.example.fxproject.model.PaymentDTO;
import com.example.fxproject.model.EnrollDTO;
import com.example.fxproject.entity.Payment;
import com.example.fxproject.entity.Enroll;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    public PaymentBOImpl(PaymentDAO paymentDAO) {
    }

    @Override
    public EnrollDTO getQuotationDetailsByEnrollId(String enrollId) throws SQLException, ClassNotFoundException {
        Enroll enroll = paymentDAO.getQuotationDetailsByEnrollId(enrollId);
        return new EnrollDTO(
                enroll.getEnrollId(),
                enroll.getClientId(),
                enroll.getClientName(),
                enroll.getContact(),
                enroll.getQuotationId(),
                enroll.getDate(),
                enroll.getEmployeeId(),
                enroll.getStartingDate(),
                enroll.getDescription()
        );
    }

    @Override
    public List<PaymentDTO> loadAllPayments() throws SQLException, ClassNotFoundException {
        return paymentDAO.loadAllPayments().stream()
                .map(p -> new PaymentDTO(
                        p.getPaymentId(),
                        p.getEnrollId(),
                        p.getAmount(),
                        p.getDate(),
                        p.getPaymentType()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public boolean payAdvance(PaymentDTO dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.payAdvance(new Payment(
                dto.getPaymentId(),
                dto.getEnrollId(),
                dto.getAmount(),
                dto.getDate(),
                dto.getPaymentType()
        ));
    }

    @Override
    public boolean payFull(PaymentDTO dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.payFull(new Payment(
                dto.getPaymentId(),
                dto.getEnrollId(),
                dto.getAmount(),
                dto.getDate(),
                dto.getPaymentType()
        ));
    }

    @Override
    public int getPaymentCount() throws SQLException, ClassNotFoundException {
        return paymentDAO.getPaymentCount();
    }
}
