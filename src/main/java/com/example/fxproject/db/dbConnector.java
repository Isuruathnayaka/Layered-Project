package com.example.fxproject.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/gardening_service";
    private static final String USER = "root";
    private static final String PASSWORD = "nirmal2002331";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println(" Connected to the database successfully.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
        return connection;
    }


    public static dbConnector getInstance() {
        return new dbConnector();
    }
}




