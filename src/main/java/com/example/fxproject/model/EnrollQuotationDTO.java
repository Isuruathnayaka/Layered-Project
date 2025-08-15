package com.example.fxproject.model;

import java.sql.Date;

public class EnrollQuotationDTO {
    private String quotationId;
    private String clientName;
    private double amount;
    private String description;

    // Constructor




    public EnrollQuotationDTO(String quotationId, String clientName, String client_name, double amount, String description) {
        this.quotationId = quotationId;
        this.clientName = clientName;
        this.amount = amount;
        this.description = description;

    }

    public EnrollQuotationDTO(String quotationId, String clientName, Object amount, String description) {
    }

    public EnrollQuotationDTO() {

    }

    // Getters
    public String getQuotationId() { return quotationId; }
    public String getClientName() { return clientName; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }

    public void setEnrollId(String enrollId) {
        this.quotationId = enrollId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnrollId() {
        return quotationId;
    }

    public String getClientId() {
        return clientName;
    }

    public String getContact() {
        return quotationId;
    }

    public Date getDate() {
        return getDate();
    }

    public String getEmployeeId() {
        return quotationId;
    }

    public Date getStartingDate() {
        return getStartingDate();
    }
}

