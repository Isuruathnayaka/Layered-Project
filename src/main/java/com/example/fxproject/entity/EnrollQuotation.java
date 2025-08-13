package com.example.fxproject.entity;

public class EnrollQuotation {
    private String quotationId;
    private String clientName;
    private double amount;
    private String description;

    // Constructor




    public EnrollQuotation(String quotationId, String clientName, String client_name, double amount, String description) {
        this.quotationId = quotationId;
        this.clientName = clientName;
        this.amount = amount;
        this.description = description;

    }

    // Getters
    public String getQuotationId() { return quotationId; }
    public String getClientName() { return clientName; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
}

