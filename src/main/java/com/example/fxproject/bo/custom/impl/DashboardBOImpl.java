package com.example.fxproject.bo.custom.impl;

import com.example.fxproject.bo.custom.DashboardBO;
import com.example.fxproject.bo.custom.SuperBO;
import com.example.fxproject.dao.DAOFactory;
import com.example.fxproject.dao.ClientDAO;
import com.example.fxproject.dao.EnrollDAO;
import com.example.fxproject.dao.PaymentDAO;
import com.example.fxproject.dao.QuotationDAO;
import com.example.fxproject.dao.EmployeeDAO;

public class DashboardBOImpl implements DashboardBO, SuperBO {

    private final ClientDAO clientDAO = (ClientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CLIENT);
    private final EnrollDAO enrollmentDAO = (EnrollDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ENROLL);
    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    private final QuotationDAO quotationDAO = (QuotationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUOTATION);
    private final EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);



    @Override
    public int getClientCount() throws Exception {
        return clientDAO.getCount();
    }

    @Override
    public int getEnrollmentCount() throws Exception {
        return enrollmentDAO.getCount();
    }

    @Override
    public int getPaymentCount() throws Exception {
        return paymentDAO.getCount();
    }

    @Override
    public int getQuotationCount() throws Exception {
        return quotationDAO.getCount();
    }

    @Override
    public int getEmployeeCount() throws Exception {
        return employeeDAO.getCount();
    }
}
