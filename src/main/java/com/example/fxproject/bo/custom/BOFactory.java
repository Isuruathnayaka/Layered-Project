package com.example.fxproject.bo.custom;

import com.example.fxproject.bo.custom.impl.ClientBOImpl;
import com.example.fxproject.bo.custom.impl.EmployeeBoImpl;
import com.example.fxproject.bo.custom.impl.QuotationBoImpl;
import com.example.fxproject.dao.ClientDAO;
import com.example.fxproject.dao.DAOFactory;
import com.example.fxproject.dao.EmployeeDAO;
import com.example.fxproject.dao.QuotationDAO;

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
        CLIENT, EMPLOYEE, QUOTATION
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
            default -> {
                return null;
            }
        }
    }
}
