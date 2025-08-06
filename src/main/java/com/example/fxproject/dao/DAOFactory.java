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
        Client,
        DAO_TYPE,
        Enrollment,

    }
    public ClientDAOImpl getDAO(DAOType daoType) {
        switch (daoType) {
            case Client->{
                return new ClientDAOImpl();
            }
        }
        return null;
    }
}
