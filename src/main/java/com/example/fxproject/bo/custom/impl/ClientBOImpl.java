package com.example.fxproject.bo.custom.impl;


import com.example.fxproject.bo.custom.ClientBo;
import com.example.fxproject.dao.ClientDAO;
import com.example.fxproject.dao.DAOFactory;
import com.example.fxproject.entity.Client;
import com.example.fxproject.model.ClientDTO;


import java.sql.SQLException;
import java.util.ArrayList;

public class ClientBOImpl implements ClientBo {
    ClientDAO clientDAO = (ClientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CLIENT);
    @Override
    public ArrayList<ClientDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<ClientDTO> clients=clientDAO.getAll();
        ArrayList<ClientDTO> dtos =new ArrayList<>();
        for(ClientDTO client:clients){
            dtos.add(new ClientDTO(
                    (String) client.getId(),
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
      Client client=new Client(clientDTO.getClientID(),clientDTO.getName(),clientDTO.getPhone(),clientDTO.getEmail(),clientDTO.getAddress());
      return clientDAO.save(client);
    }

    @Override
    public boolean updateClient(ClientDTO clientDTO) throws SQLException, ClassNotFoundException {
        Client client=new Client(clientDTO.getClientID(),clientDTO.getName(),clientDTO.getPhone(),clientDTO.getEmail(),clientDTO.getAddress());
        return clientDAO.update(client);
    }

    @Override
    public boolean existClient(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteClient(String id) throws SQLException, ClassNotFoundException {
         return clientDAO.delete(id);
    }

    @Override
    public String generateNewClientId() throws SQLException, ClassNotFoundException {
        return  clientDAO.generateNewId();
    }
}
