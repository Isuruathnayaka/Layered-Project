package com.example.fxproject.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Enroll {

    private String enrollId;
    @Getter
    private String clientId;
    private String clientName;
    private String contact;
    private String quotationId;
    private Date   date;
    private String employeeId;
    private Date   startingDate;
    private String description;





    public Enroll(String enrollId, String quotationId, String name) {
        this.enrollId = enrollId;
        this.quotationId = quotationId;
        this.enrollId = enrollId;

    }

    public Enroll(String id, String quotationId) {
        this.enrollId = id;
        this.quotationId = quotationId;
    }

    public Enroll(String enrollId, String clientId, String enrollName, String clientName, String contact, String quotationId, String date, String employeeId, String startingDate, String description) {
    }


    public String getPhone() {
        return contact;
    }

    public String getClientID() {
        return clientId;
    }
}
