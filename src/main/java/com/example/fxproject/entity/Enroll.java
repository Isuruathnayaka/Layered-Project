package com.example.fxproject.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Enroll {
    private String enrollId;
    private String clientId;
    private String clientName;
    private String contact;
    private String quotationId;
    private Date date;
    private String employeeId;
    private Date startingDate;
    private String description;

    public Enroll(String quotationId, String clientName, double amount, String description) {
    }

    public Object getAmount() {
        return null;
    }

    public String getClientID() {
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

    public Date getStartingDate() {
        return startingDate;
    }
    public String getDescription() {

        return description;
    }


    public String getEmployeeId() {
        return employeeId;
    }

    public String getClientId() {
        return clientId;
    }
}
