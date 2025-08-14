package com.example.fxproject.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Payment {
    private int invoiceNumber;
    private String enrollId;
    private String quotationId;
    private double amount;
    private boolean advancePaid;
    private double advanceAmount;
    private String status;
    private Timestamp paymentDate;

    // Optional extra fields if you want to store more payment info
    private String paymentType;
    private String description;
    private String clientName;

    public Payment() {}

    public Payment(int invoiceNumber, String enrollId, String quotationId, double amount, boolean advancePaid, double advanceAmount, String status, Timestamp paymentDate) {
        this.invoiceNumber = invoiceNumber;
        this.enrollId = enrollId;
        this.quotationId = quotationId;
        this.amount = amount;
        this.advancePaid = advancePaid;
        this.advanceAmount = advanceAmount;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    // For advance/full payment insert without invoiceNumber/paymentDate
    public Payment(String enrollId, String quotationId, double amount, boolean advancePaid, double advanceAmount, String status) {
        this.enrollId = enrollId;
        this.quotationId = quotationId;
        this.amount = amount;
        this.advancePaid = advancePaid;
        this.advanceAmount = advanceAmount;
        this.status = status;
    }

    public Payment(String enrollId, double amount, Date date, String paymentType) {
        this.enrollId = enrollId;
        this.amount = amount;
        this.paymentDate = new Timestamp(date.getTime());
        this.paymentType = paymentType;
    }

    // Getters and setters
    public int getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(int invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public String getEnrollId() { return enrollId; }
    public void setEnrollId(String enrollId) { this.enrollId = enrollId; }

    public String getQuotationId() { return quotationId; }
    public void setQuotationId(String quotationId) { this.quotationId = quotationId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public boolean isAdvancePaid() { return advancePaid; }
    public void setAdvancePaid(boolean advancePaid) { this.advancePaid = advancePaid; }

    public double getAdvanceAmount() { return advanceAmount; }
    public void setAdvanceAmount(double advanceAmount) { this.advanceAmount = advanceAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Timestamp paymentDate) { this.paymentDate = paymentDate; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public Object getDate() {
        return paymentDate;
    }
}
