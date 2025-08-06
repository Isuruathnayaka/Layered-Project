package com.example.fxproject.bo.custom;

import com.example.fxproject.model.ClientDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClientBo extends SuperBO {
    public ArrayList<ClientDTO> getAllCustomer() throws SQLException, ClassNotFoundException ;
    public void saveClient(ClientDTO clientDTO) throws SQLException,ClassNotFoundException ;
    public void updateClient(ClientDTO clientDTO) throws SQLException, ClassNotFoundException ;
    public boolean existClient(String id) throws SQLException, ClassNotFoundException;
    public void deleteClient(String id) throws SQLException, ClassNotFoundException;
    public String generateNewClientId() throws SQLException, ClassNotFoundException;
}
