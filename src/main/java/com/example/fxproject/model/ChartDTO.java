package com.example.fxproject.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class ChartDTO {
    private String clientID;
    private String name;
    private String phone;
    private String email;
    private String address;
}
