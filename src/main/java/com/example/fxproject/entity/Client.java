package com.example.fxproject.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Client implements Serializable {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    public Client() {}
    public Client(String id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }


}
