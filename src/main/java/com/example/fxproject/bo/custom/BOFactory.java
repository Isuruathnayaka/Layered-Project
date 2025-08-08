package com.example.fxproject.bo.custom;

import com.example.fxproject.bo.custom.impl.ClientBOImpl;
import com.example.fxproject.dao.ClientDAO;
import com.example.fxproject.dao.DAOFactory;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getInstance(){
        if (boFactory == null){
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum BOType {
        CLIENT, ITEM, PLACE_ORDER
    }

    public SuperBO getBO(BOType boType) {
        switch (boType) {
            case CLIENT -> {
                // Get the DAO instance from DAOFactory
                ClientDAO clientDAO = (ClientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CLIENT);
                // Inject DAO into BO
                return new ClientBOImpl(clientDAO);
            }
            case ITEM -> {
                return null; // Will be implemented later
            }
            case PLACE_ORDER -> {
                return null; // Will be implemented later
            }
            default -> {
                return null;
            }
        }
    }
}
