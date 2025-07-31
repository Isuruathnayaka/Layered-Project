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
    private Date   date;
    private String employeeId;
    private Date   startingDate;
    private String description;





    public EnrollDTO(String enrollId, String quotationId, String name) {
        this.enrollId = enrollId;
        this.quotationId = quotationId;
        this.enrollId = enrollId;

    }

    public EnrollDTO(String id, String quotationId) {
        this.enrollId = id;
        this.quotationId = quotationId;
    }


    public String getPhone() {
        return contact;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientID() {
        return clientId;
    }
}
