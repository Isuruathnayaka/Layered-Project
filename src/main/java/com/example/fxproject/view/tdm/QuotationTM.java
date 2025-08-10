package com.example.fxproject.view.tdm;

import java.time.LocalDate;

public class QuotationTM {
    private String quotation_id;
    private String client_id;
    private String description;
    private double amount;
    private LocalDate date;
    public QuotationTM() {}

    public QuotationTM(String quotation_id, String client_id, String description, double amount, LocalDate date) {
        this.quotation_id = quotation_id;
        this.client_id = client_id;
        this.description = description;
        this.amount = amount;
        this.date = date;

    }
    public String getQuotation_id() {
        return quotation_id;
    }
    public void setQuotation_id(String quotation_id) {
        this.quotation_id = quotation_id;
    }
    public String getClient_id() {
        return client_id;
    }
    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAmount() {
        return String.valueOf(amount);
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

}
