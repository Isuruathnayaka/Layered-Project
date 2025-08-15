package com.example.fxproject.bo.custom.impl;

import com.example.fxproject.bo.custom.PaymentBO;
import com.example.fxproject.dao.PaymentDAO;
import com.example.fxproject.model.EnrollQuotationDTO;
import com.example.fxproject.model.PaymentDTO;
import com.example.fxproject.model.EnrollDTO;
import com.example.fxproject.entity.Payment;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAO paymentDAO;


    public PaymentBOImpl(PaymentDAO paymentDAO) {
        if (paymentDAO == null) throw new RuntimeException("PaymentDAO cannot be null!");
        this.paymentDAO = paymentDAO;
    }

    @Override
    public EnrollDTO getQuotationDetailsByEnrollId(String enrollId) throws SQLException, ClassNotFoundException {
        EnrollQuotationDTO enroll = paymentDAO.getQuotationDetailsByEnrollId(enrollId);
        if (enroll == null) return null;

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
        List<Payment> payments = paymentDAO.loadAllPayments();
        return payments.stream().map(p -> new PaymentDTO(
                p.getInvoiceNumber(),
                p.getEnrollId(),
                p.getQuotationId(),
                p.getAmount(),
                p.isAdvancePaid(),
                p.getAdvanceAmount(),
                p.getStatus(),
                p.getPaymentDate()
        )).collect(Collectors.toList());
    }

    @Override
    public boolean payAdvance(PaymentDTO dto) throws SQLException, ClassNotFoundException {


        return paymentDAO.payAdvance(new Payment(
                dto.getEnrollId(),
                dto.getQuotationId(),
                dto.getAmount(),
                dto.isAdvancePaid(),
                dto.getAdvanceAmount(),
                "ADVANCE PAID"
        ));
    }

    @Override
    public boolean payFull(PaymentDTO dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.payFull(new Payment(
                dto.getEnrollId(),
                dto.getQuotationId(),
                dto.getAmount(),
                false,
                0,
                "FULL PAID"
        ));
    }

    @Override
    public int getPaymentCount() throws SQLException, ClassNotFoundException {
        return paymentDAO.getPaymentCount();
    }

    @Override
    public EnrollQuotationDTO getQuotationDetails(String enrollId) throws SQLException, ClassNotFoundException {
        return paymentDAO.getQuotationDetailsByEnrollId(enrollId);
    }
}
