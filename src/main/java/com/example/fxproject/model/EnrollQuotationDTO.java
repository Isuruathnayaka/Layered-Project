package com.example.fxproject.model;

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

    // Getters
    public String getQuotationId() { return quotationId; }
    public String getClientName() { return clientName; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
}

