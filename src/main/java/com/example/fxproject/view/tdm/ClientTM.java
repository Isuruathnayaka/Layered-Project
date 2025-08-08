package com.example.fxproject.view.tdm;

public class ClientTM implements Comparable<ClientTM> {

    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;

    public ClientTM() {}

    public ClientTM(String client_id, String name, String phone, String email, String address) {
        this.id = client_id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;

    }

    // Getters and setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ClientTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public int compareTo(ClientTM o) {
        return id.compareTo(o.getId());
    }
}
