package com.company.inventory.initializer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public final class DatabaseInitializer {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/SupplyChainManagement";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error loading database driver", e);
        }
    }


    public static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {


            // Create Seller table
            String createSellerTableSQL = "CREATE TABLE IF NOT EXISTS seller (" +
                    "sellerId BIGINT PRIMARY KEY," +
                    "sellerName VARCHAR(255) NOT NULL," +
                    "contactEmail VARCHAR(255) NOT NULL," +
                    "contactPhone VARCHAR(255) NOT NULL" +
                    ")";

            // Create Buyer table
            String createBuyerTableSQL = "CREATE TABLE IF NOT EXISTS buyer (" +
                    "buyerId BIGINT PRIMARY KEY," +
                    "buyerName VARCHAR(255) NOT NULL," +
                    "buyerSurname VARCHAR(255) NOT NULL," +
                    "buyerPhoneNumber VARCHAR(255) NOT NULL" +
                    ")";

            // Create Goods table with foreign key reference to Buyer
            String createGoodsTableSQL = "CREATE TABLE IF NOT EXISTS goods (" +
                    "productId BIGINT PRIMARY KEY," +
                    "productName VARCHAR(255) NOT NULL," +
                    "quantity INT NOT NULL," +
                    "buyerId BIGINT" +
                    ")";

            // Create Orders table with foreign key references to Seller and Goods
            String createOrdersTableSQL = "CREATE TABLE IF NOT EXISTS orders (" +
                    "orderId BIGINT PRIMARY KEY," +
                    "orderSellerId BIGINT," +
                    "FOREIGN KEY (orderSellerId) REFERENCES seller(sellerId)," +
                    "goodsSoldId BIGINT," +
                    "FOREIGN KEY (goodsSoldId) REFERENCES goods(productId)," +
                    "saleDate DATE" +
                    ")";



            statement.executeUpdate(createGoodsTableSQL);
            statement.executeUpdate(createSellerTableSQL);
            statement.executeUpdate(createBuyerTableSQL);
            statement.executeUpdate(createOrdersTableSQL);



            System.out.println("Database initialization successful.");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing database", e);
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}
