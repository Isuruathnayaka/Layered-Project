package com.example.fxproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class Client implements Serializable {
    private String client_id;
    private String name;
    private String phone;
    private String email;
    private String address;
    public Client() {}


}
