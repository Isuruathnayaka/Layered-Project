package com.example.fxproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
public class Quotation implements Serializable {
    private String quotation_id;
    private String client_id;
    private  String description;
    private  double amount;
    private LocalDate date;
     public Quotation() {}

}
