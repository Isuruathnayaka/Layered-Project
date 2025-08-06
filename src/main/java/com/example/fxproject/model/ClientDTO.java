package com.example.fxproject.model;

import lombok.*;


@Getter

@Setter
@ToString
@AllArgsConstructor


public class ClientDTO {
    private String clientID;

    private String clientName;
    private String phone;
    private String email;
    private String address;

    public ClientDTO(String clientId, String name, String address) {
        this.clientID = clientId;
        this.clientName = name;
        this.address = address;

    }

    public String getName(){
        return clientName;
    }
    public void setName(String name){
        clientName = name;
    }


    public Object getId() {
        return clientID;
    }
}