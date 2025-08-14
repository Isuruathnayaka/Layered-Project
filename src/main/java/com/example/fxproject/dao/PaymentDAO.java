package com.example.fxproject.dao;

import com.example.fxproject.entity.Payment;
import com.example.fxproject.entity.Enroll;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO extends SuperDAO{
   Enroll getQuotationDetailsByEnrollId(String enrollId) throws SQLException, ClassNotFoundException;

   List<Payment> loadAllPayments() throws SQLException, ClassNotFoundException;

   Payment getPaymentByInvoice(int invoiceNumber) throws SQLException, ClassNotFoundException;

   boolean payAdvance(Payment payment) throws SQLException, ClassNotFoundException;

   boolean payFull(Payment payment) throws SQLException, ClassNotFoundException;

   int getPaymentCount() throws SQLException, ClassNotFoundException;
}
