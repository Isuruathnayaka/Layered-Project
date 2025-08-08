package com.example.fxproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class Employee implements Serializable {
    private String emp_id;
    private String emp_name;
    private String emp_phone;
    private String emp_email;
    private String emp_address;
    private String emp_role;
    public Employee() {}

}
