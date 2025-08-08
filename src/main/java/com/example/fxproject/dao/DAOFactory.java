package com.example.fxproject.dao;

import com.example.fxproject.dao.impl.ClientDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOType {
        CLIENT,
        EMPLOYEE
    }


    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case CLIENT:
                return new ClientDAOImpl(); // ✅ must implement ClientDAO
            case EMPLOYEE:
                default:
        }
        return null;
    }

}
