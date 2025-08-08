package com.example.fxproject.bo.custom.impl;

import com.example.fxproject.bo.custom.ClientBo;
import com.example.fxproject.dao.ClientDAO;
import com.example.fxproject.dao.DAOFactory;
import com.example.fxproject.entity.Client;
import com.example.fxproject.model.ClientDTO;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClientBOImpl implements ClientBo {

    private final ClientDAO clientDAO = (ClientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CLIENT);

    @SneakyThrows
    @Override
    public String generateNewCustomerId() throws SQLException {
        return clientDAO.generateNewId();
    }

    @Override
    public ArrayList<ClientDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Client> clients = clientDAO.getAll(); // DAO returns entities
        ArrayList<ClientDTO> dtos = new ArrayList<>();

        for (Client client : clients) {
            dtos.add(new ClientDTO(
                    client.getClient_id(),
                    client.getName(),
                    client.getPhone(),
                    client.getEmail(),
                    client.getAddress()
            ));
        }
        return dtos;
    }

    @Override
    public boolean saveClient(ClientDTO clientDTO) throws SQLException, ClassNotFoundException {
        Client client = new Client(
                (String) clientDTO.getId(),
                clientDTO.getName(),
                clientDTO.getPhone(),
                clientDTO.getEmail(),
                clientDTO.getAddress()
        );
        return clientDAO.save(client);
    }

    @Override
    public boolean updateClient(ClientDTO clientDTO) throws SQLException, ClassNotFoundException {
        Client client = new Client(
                (String) clientDTO.getId(),
                clientDTO.getName(),
                clientDTO.getPhone(),
                clientDTO.getEmail(),
                clientDTO.getAddress()
        );
        return clientDAO.update(client);
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
        if (client != null) {
            return new ClientDTO(
                    client.getClient_id(),
                    client.getName(),
                    client.getPhone(),
                    client.getEmail(),
                    client.getAddress()
            );
        }
        return null;
    }
}
