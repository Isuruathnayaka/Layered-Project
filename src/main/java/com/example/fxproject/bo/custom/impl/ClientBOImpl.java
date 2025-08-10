package com.example.fxproject.bo.custom.impl;

import com.example.fxproject.bo.custom.ClientBo;
import com.example.fxproject.dao.ClientDAO;
import com.example.fxproject.entity.Client;
import com.example.fxproject.model.ClientDTO;
import com.example.fxproject.bo.custom.MapUtill;
import com.example.fxproject.model.QuotationDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClientBOImpl implements ClientBo {

    private final ClientDAO clientDAO;

    // Dependency Injection via constructor — no hard-coded DAO creation
    public ClientBOImpl(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public String generateNewCustomerId() throws SQLException, ClassNotFoundException {
        return clientDAO.generateNewId();
    }

    @Override
    public boolean saveQuotation(QuotationDTO dto) {
        return false;
    }

    @Override
    public ArrayList<ClientDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        return clientDAO.getAll()
                .stream()
                .map(MapUtill::toDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean saveClient(ClientDTO clientDTO) throws SQLException, ClassNotFoundException {
        return clientDAO.save(MapUtill.toEntity(clientDTO));
    }

    @Override
    public boolean updateClient(ClientDTO clientDTO) throws SQLException, ClassNotFoundException {
        return clientDAO.update(MapUtill.toEntity(clientDTO));
    }

    @Override
    public boolean existClient(String id) throws SQLException, ClassNotFoundException {
        return clientDAO.search(id) != null;
    }

    @Override
    public boolean deleteClient(String id) throws SQLException, ClassNotFoundException {
        return clientDAO.delete(id);
    }

    @Override
    public String generateNewClientId() throws SQLException, ClassNotFoundException {
        return clientDAO.generateNewId();
    }

    @Override
    public ClientDTO searchClient(String clientID) throws SQLException, ClassNotFoundException {
        Client client = clientDAO.search(clientID);
        return (client != null) ? MapUtill.toDTO(client) : null;
    }
}
