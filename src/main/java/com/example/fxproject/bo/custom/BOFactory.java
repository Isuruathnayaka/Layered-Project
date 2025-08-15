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
        CLIENT, EMPLOYEE, QUOTATION,ENROLL, DASHBOARD, PAYMENT
    }

    public SuperBO getBO(BOType boType) {
        switch (boType) {
            case CLIENT -> {

                ClientDAO clientDAO = (ClientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CLIENT);

                return new ClientBOImpl(clientDAO);
            }
            case EMPLOYEE -> {
                EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);
                return new EmployeeBoImpl(employeeDAO);
            }
            case QUOTATION -> {
                QuotationDAO quotationDAO=(QuotationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUOTATION);
                return new QuotationBoImpl(quotationDAO);
            }
            case ENROLL -> {
                EnrollDAO enrollDAO=(EnrollDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ENROLL);
                return new EnrollBoImpl(enrollDAO);

            }
            case PAYMENT -> {
                PaymentDAO paymentDAO= (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
                return new PaymentBOImpl(paymentDAO);
            }
            case DASHBOARD -> {
                return  new DashboardBOImpl();
            }
            default -> {
                return null;
            }
        }
    }
}
