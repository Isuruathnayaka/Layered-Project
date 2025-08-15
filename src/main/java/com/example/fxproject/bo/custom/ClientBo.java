package com.example.fxproject.bo.custom;

import com.example.fxproject.model.ClientDTO;
import com.example.fxproject.model.QuotationDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClientBo extends SuperBO {





    public ArrayList<ClientDTO> getAllCustomer() throws SQLException, ClassNotFoundException ;
    public boolean saveClient(ClientDTO clientDTO) throws SQLException,ClassNotFoundException ;
    public boolean updateClient(ClientDTO clientDTO) throws SQLException, ClassNotFoundException ;

    boolean existClient(String id) throws SQLException, ClassNotFoundException;


    public boolean deleteClient(String id) throws SQLException, ClassNotFoundException;


    String generateNewClientId() throws SQLException, ClassNotFoundException;

    ClientDTO searchClient(String clientID) throws SQLException, ClassNotFoundException;

    String generateNewCustomerId() throws SQLException, ClassNotFoundException;

    boolean saveQuotation(QuotationDTO dto);
}
