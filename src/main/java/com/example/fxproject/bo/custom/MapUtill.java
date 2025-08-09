package com.example.fxproject.bo.custom;



import com.example.fxproject.entity.Client;
import com.example.fxproject.entity.Employee;
import com.example.fxproject.model.ClientDTO;
import com.example.fxproject.model.EmployeeDTO;

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

    public static EmployeeDTO toDTO(Employee entity) {
        return new EmployeeDTO(
                entity.getEmp_id(),
                entity.getEmp_name(),
                entity.getEmp_phone(),
                entity.getEmp_email(),
                entity.getEmp_address(),
                entity.getEmp_role()
        );
    }
    public static Employee toEntity(EmployeeDTO dto) {
        return new Employee(
              dto.getEmp_id(),
                dto.getEmp_name(),
                dto.getEmp_phone(),
                dto.getEmp_email(),
                dto.getEmp_address(),
                dto.getEmp_role()
        );
    }
}
