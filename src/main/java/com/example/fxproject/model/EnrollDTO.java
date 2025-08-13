package com.example.fxproject.model;

import lombok.*;
import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EnrollDTO {

    private String enrollId;
    private String clientId;
    private String clientName;
    private String contact;
    private String quotationId;
    private Date date;
    private String employeeId;
    private Date startingDate;
    private String description;

    // Partial constructor (optional, for custom use)
    public EnrollDTO(String enrollId, String clientId, String clientName, String contact, String quotationId) {
        this.enrollId = enrollId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.contact = contact;
        this.quotationId = quotationId;
    }

    // Convenience method for TableView (optional)
    public String getEnrollId() {
        return enrollId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getContact() {
        return contact;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public Date getDate() {
        return date;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public String getDescription() {
        return description;
    }

    public String getClientID() {
        return clientId;
    }

    public char[] getAmount() {
        return null;
    }
}
