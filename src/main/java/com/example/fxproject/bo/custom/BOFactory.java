package com.example.fxproject.bo.custom;

import com.example.fxproject.bo.custom.impl.*;

import com.example.fxproject.dao.*;

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
        CLIENT, EMPLOYEE, QUOTATION,ENROLL,PAYMENT
    }

    public SuperBO getBO(BOType boType) {
        switch (boType) {
            case CLIENT -> {
                // Get the DAO instance from DAOFactory
                ClientDAO clientDAO = (ClientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CLIENT);
                // Inject DAO into BO
                return new ClientBOImpl(clientDAO);
            }
            case EMPLOYEE -> {
                EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);
                return new EmployeeBoImpl(employeeDAO); // Will be implemented later
            }
            case QUOTATION -> {
                QuotationDAO quotationDAO=(QuotationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUOTATION);
                return new QuotationBoImpl(quotationDAO); // Will be implemented later
            }
            case ENROLL -> {
                EnrollDAO enrollDAO=(EnrollDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ENROLL);
                return new EnrollBoImpl(enrollDAO);

            }
            case PAYMENT -> {
                PaymentDAO paymentDAO= (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
                return new PaymentBOImpl(paymentDAO);
            }
            default -> {
                return null;
            }
        }
    }
}
