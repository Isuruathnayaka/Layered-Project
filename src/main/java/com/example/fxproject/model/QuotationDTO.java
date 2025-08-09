package com.example.fxproject.model;

import java.time.LocalDate;

public class QuotationDTO {
    private String quotationId;
    private String clientId;
    private LocalDate date;
    private double amount;
    private String description;

    public QuotationDTO(String quotationId, String clientId, double amount, String description, LocalDate date) {
        this.quotationId = quotationId;
        this.clientId = clientId;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public QuotationDTO(String quotationId, String clientId, String description, double amount, LocalDate date) {
        this.quotationId = quotationId;
        this.clientId = clientId;
        this.amount = amount;
        this.description = description;
        this.date = date;

    }

    public String getQuotationId() {
        return quotationId;
    }

    public String getClientId() {
        return clientId;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {

        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getClientName() {
        return clientId;
    }
}
