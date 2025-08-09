package com.example.fxproject.bo.custom;



import com.example.fxproject.entity.Client;
import com.example.fxproject.entity.Employee;
import com.example.fxproject.entity.Quotation;
import com.example.fxproject.model.ClientDTO;
import com.example.fxproject.model.EmployeeDTO;
import com.example.fxproject.model.QuotationDTO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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

    public static Quotation toEntity(QuotationDTO dto) {
        return new Quotation(
                dto.getQuotationId(),
                dto.getClientId(),
                dto.getDescription(),
                dto.getAmount(),
                dto.getDate()
        );
    }

    public static Object toDTO(Quotation quotation) {
        return new QuotationDTO(
                quotation.getQuotation_id(),
                quotation.getClient_id(),
                quotation.getDescription(),
                quotation.getAmount(),
                quotation.getDate()
        );
    }

    public class NavigationUtil {

        public static void openWindow(String fxmlPath, String title) throws IOException {
            Parent root = FXMLLoader.load(NavigationUtil.class.getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
