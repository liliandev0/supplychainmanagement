package com.company.inventory.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/SupplyChainManagement";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.fillInStackTrace();
            throw new RuntimeException("Error loading database driver", e);
        }
    }

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        }catch (SQLException e){
            e.fillInStackTrace();
            throw new RuntimeException("Error getting database connection");
        }
    }

    public static void closeConnection(Connection connection){
        if(connection != null){
            try {
                connection.close();
            }catch (SQLException e){
                e.fillInStackTrace();
                throw new RuntimeException("Error closing database connection");
            }
        }
    }
}
