package com.example.fxproject.bo.custom;

public interface DashboardBO {
    int getClientCount() throws Exception;
    int getEnrollmentCount() throws Exception;
    int getPaymentCount() throws Exception;
    int getQuotationCount() throws Exception;
    int getEmployeeCount() throws Exception;
}