package com.example.fxproject.bo.custom.impl;


import com.example.fxproject.bo.custom.ClientBo;
import com.example.fxproject.dao.ClientDAO;
import com.example.fxproject.dao.DAOFactory;
import com.example.fxproject.model.ClientDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClientBOImpl implements ClientBo {
      ClientDAO clientDAO=(ClientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.Client);
    @Override
    public ArrayList<ClientDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        return clientDAO.getAll();
    }

    @Override
    public void saveClient(ClientDTO clientDTO) throws SQLException, ClassNotFoundException {
      clientDAO.save(clientDTO);
    }

    @Override
    public void updateClient(ClientDTO clientDTO) throws SQLException, ClassNotFoundException {
      clientDAO.update(clientDTO);
    }

    @Override
    public boolean existClient(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public void deleteClient(String id) throws SQLException, ClassNotFoundException {
         clientDAO.delete(id);
    }

    @Override
    public String generateNewClientId() throws SQLException, ClassNotFoundException {
        return  clientDAO.generateNewId();
    }
}
