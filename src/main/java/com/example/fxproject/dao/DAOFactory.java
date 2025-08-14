package com.example.fxproject.dao;


import com.example.fxproject.dao.impl.*;


import static com.example.fxproject.bo.custom.BOFactory.BOType.PAYMENT;
import static com.example.fxproject.bo.custom.BOFactory.BOType.QUOTATION;

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
        QUOTATION,
        EMPLOYEE,
        ENROLL,
        PAYMENT
    }


    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case CLIENT:
                return new ClientDAOImpl(); //  must implement ClientDAO
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case QUOTATION:
                return new QuotationDAOImpl();
            case ENROLL:
                return new EnrollDAOImpl();
                case PAYMENT:
                return new PaymentDAOImpl();

        }

        return null;
    }

}
