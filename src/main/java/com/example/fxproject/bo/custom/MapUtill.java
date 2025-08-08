package com.example.fxproject.bo.custom;



import com.example.fxproject.entity.Client;
import com.example.fxproject.model.ClientDTO;

public class MapUtill {
    public static Client toEntity(ClientDTO dto) {
        return new Client(
                (String) dto.getId(),
                dto.getName(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getAddress()
        );
    }

    public static ClientDTO toDTO(Client entity) {
        return new ClientDTO(
                entity.getClient_id(),
                entity.getName(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getAddress()
        );
    }
}
