package com.example.fxproject.model;

import java.sql.Timestamp;

public class PaymentDTO {
    private int invoiceNumber;
    private String enrollId;
    private String quotationId;
    private double amount;
    private boolean advancePaid;
    private double advanceAmount;
    private String status;
    private Timestamp paymentDate;

    // ✅ No-argument constructor (required for JavaFX TableView)
    public PaymentDTO() {
    }

    // ✅ Partial constructor
    public PaymentDTO(int invoiceNumber, String enrollId, String quotationId, double amount) {
        this.invoiceNumber = invoiceNumber;
        this.enrollId = enrollId;
        this.quotationId = quotationId;
        this.amount = amount;
    }

    // ✅ Full constructor
    public PaymentDTO(int invoiceNumber, String enrollId, String quotationId, double amount,
                      boolean advancePaid, double advanceAmount, String status, Timestamp paymentDate) {
        this.invoiceNumber = invoiceNumber;
        this.enrollId = enrollId;
        this.quotationId = quotationId;
        this.amount = amount;
        this.advancePaid = advancePaid;
        this.advanceAmount = advanceAmount;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    // ✅ Getters
    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getEnrollId() {
        return enrollId;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isAdvancePaid() {
        return advancePaid;
    }

    public double getAdvanceAmount() {
        return advanceAmount;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    // ✅ Setters (optional but useful if you're populating via setters)
    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setEnrollId(String enrollId) {
        this.enrollId = enrollId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAdvancePaid(boolean advancePaid) {
        this.advancePaid = advancePaid;
    }

    public void setAdvanceAmount(double advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }
}
