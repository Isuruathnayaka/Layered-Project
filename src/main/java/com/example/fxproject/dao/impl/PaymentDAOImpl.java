package com.example.fxproject.dao.impl;

import com.example.fxproject.dao.PaymentDAO;
import com.example.fxproject.entity.Payment;
import com.example.fxproject.entity.Enroll;
import com.example.fxproject.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public Enroll getQuotationDetailsByEnrollId(String enrollId) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery(
                "SELECT e.enroll_id, e.client_id, c.name, c.contact, e.quotation_id, e.date, e.employee_id, e.starting_date, q.description " +
                        "FROM enroll e " +
                        "JOIN client c ON e.client_id = c.client_id " +
                        "JOIN quotation q ON e.quotation_id = q.quotation_id " +
                        "WHERE e.enroll_id = ?",
                enrollId
        );

        if (rs.next()) {
            return new Enroll(
                    rs.getString("enroll_id"),
                    rs.getString("client_id"),
                    rs.getString("name"),
                    rs.getString("contact"),
                    rs.getString("quotation_id"),
                    rs.getDate("date"),
                    rs.getString("employee_id"),
                    rs.getDate("starting_date"),
                    rs.getString("description")
            );
        }
        return null;
    }

    @Override
    public List<Payment> loadAllPayments() throws SQLException, ClassNotFoundException {
        List<Payment> payments = new ArrayList<>();
        ResultSet rs = SQLUtil.executeQuery("SELECT * FROM payment");

        while (rs.next()) {
            payments.add(new Payment(
                    rs.getInt("invoice_number"),
                    rs.getString("enroll_id"),
                    rs.getString("quotation_id"),
                    rs.getDouble("amount"),
                    rs.getBoolean("advance_paid"),
                    rs.getDouble("advance_amount"),
                    rs.getString("status"),
                    rs.getTimestamp("payment_date")
            ));
        }
        return payments;
    }



        @Override
    public Payment getPaymentByInvoice(int invoiceNumber) throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery("SELECT * FROM payment WHERE payment_id = ?", invoiceNumber);
        if (rs.next()) {
            return new Payment(

                    rs.getString("enroll_id"),
                    rs.getDouble("amount"),
                    rs.getDate("date"),
                    rs.getString("payment_type")
            );
        }
        return null;
    }

    @Override
    public boolean payAdvance(Payment payment) throws SQLException, ClassNotFoundException {
        SQLUtil.executeUpdate(
                "INSERT INTO payment (enroll_id, quotation_id, amount, advance_paid, advance_amount, status, payment_date) VALUES (?, ?, ?, ?, ?, ?, ?)",
                payment.getEnrollId(),
                payment.getQuotationId(),
                payment.getAmount(),
                payment.isAdvancePaid(),
                payment.getAdvanceAmount(),
                payment.getStatus(),
                new java.sql.Timestamp(System.currentTimeMillis())
        );
        return false;
    }

    @Override
    public boolean payFull(Payment payment) throws SQLException, ClassNotFoundException {
        SQLUtil.executeUpdate(
                "INSERT INTO payment (enroll_id, quotation_id, amount, advance_paid, advance_amount, status, payment_date) VALUES (?, ?, ?, ?, ?, ?, ?)",
                payment.getEnrollId(),
                payment.getQuotationId(),
                payment.getAmount(),
                payment.isAdvancePaid(),
                payment.getAdvanceAmount(),
                payment.getStatus(),
                new java.sql.Timestamp(System.currentTimeMillis())
        );
        return false;
    }

    @Override
    public int getPaymentCount() throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.executeQuery("SELECT COUNT(*) AS total FROM payment");
        if (rs.next()) {
            return rs.getInt("total");
        }
        return 0;
    }
}
