package com.example.fxproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter

@Setter
@ToString
@AllArgsConstructor

public class EmployeeDTO {
    // Getters
    private String emp_id;
    private String emp_name;
    private String emp_phone;
    private String emp_email;
    private String emp_address;
    private String emp_role;

    public EmployeeDTO() {}


}
