package com.company.inventory.dao;

import com.company.inventory.exceptions.DAOException;
import com.company.inventory.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class serves to implement OrderDAO interface and to make
 * requests to the database for retrieving data, deleting or modifying it.
 *
 */
public class OrderDAOImpl implements OrderDAO {

    private final Connection connection;
    private static final Logger log = LoggerFactory.getLogger(OrderDAOImpl.class);

    public OrderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * @param orderId order Id
     * @return the Object Order
     */
    @Override
    public Order findById(long orderId) {
        if (orderId > 0) {
            String preparedStatement = "SELECT * FROM orders WHERE orderId = ?";
            try (PreparedStatement statement = connection.prepareStatement(preparedStatement)) {
                statement.setLong(1, orderId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToOrder(resultSet);
                    }
                }
            } catch (SQLException e) {
                throw new DAOException("Error finding Order by ID", e);
            }
        } else throw new IllegalArgumentException("Order ID must be greater than 0");
        return null;
    }

    /**
     * @return returns all orders from DB
     */
    @Override
    public List<Order> findAll() {
        String preparedStatement = "SELECT * FROM orders";
        List<Order> orderList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(preparedStatement)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = mapResultSetToOrder(resultSet);
                orderList.add(order);
            }
            if (!orderList.isEmpty()) {
                return orderList;
            } else {
                throw new DAOException("Error in finding elements from table orders");
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding Orders into method findAll");
        }
    }

    /**
     * @param order Saves the order into the DB
     */
    @Override
    public void save(Order order) {
        if (checkOrderValidity(order)) {
            String statement = "INSERT INTO orders (orderSellerId, goodsSoldId, saleDate, quantitySold, orderBuyerId) VALUES(?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                connection.setAutoCommit(false);

                preparedStatement.setInt(1, order.getOrderSellerId());
                preparedStatement.setInt(2, order.getGoodsSoldId());
                preparedStatement.setDate(3, order.getSaleDate());
                preparedStatement.setInt(4, order.getQuantitySoldId());
                preparedStatement.setInt(5, order.getOrderBuyerId());

                try {
                    preparedStatement.executeQuery();
                    connection.commit();

                } catch (SQLException ex) {
                    connection.rollback();
                    log.error("Error in inserting elements table orders", ex);
                }
            } catch (SQLException e) {
                throw new DAOException("Error inserting elements into table orders method save");
            }
        }
    }

    /**
     *
     * @param order
     * Updates the table orders
     *
     */
    @Override
    public void update(Order order) {
        if(checkOrderValidity(order)) {
            String query = "UPDATE orders SET orderSellerId = ?, goodsSoldId = ?, saleDate = ?, quantitySold = ?, orderBuyerId = ?, WHERE orderId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);

                preparedStatement.setInt(1, order.getOrderSellerId());
                preparedStatement.setInt(2, order.getGoodsSoldId());
                preparedStatement.setDate(3, order.getSaleDate());
                preparedStatement.setInt(4, order.getQuantitySoldId());
                preparedStatement.setInt(5, order.getOrderBuyerId());

                try {
                    preparedStatement.executeUpdate();
                    connection.commit();

                }catch (SQLException ex){
                    connection.rollback();
                    log.error("Error in updating table orders", ex);
                }
            } catch (SQLException e) {
                throw new DAOException("Error updating orders table", e);
            }
        }
    }

    /**
     *
     * @param orderId
     * Deletes the order specified with the ID
     */
    @Override
    public void delete(long orderId) {
        if(orderId > 0) {
            String query = "DELETE FROM orders WHERE orderId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, orderId);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException("Error deleting order from orders table", e);
            }
        }else throw new IllegalArgumentException("Order ID must be greater than 0");

    }

    /**
     *
     * @param resultSet takes the ResultSet from findAll
     * @return the Orders object mapped
     * @throws SQLException //
     * @throws DAOException //
     * Serves to map the Orders as an Object
     *
     */
    private Order mapResultSetToOrder(ResultSet resultSet) throws SQLException {
        int orderId = resultSet.getInt("orderId");
        int orderSellerId = resultSet.getInt("orderSellerId");
        int goodsSoldId = resultSet.getInt("goodsSoldId");
        java.sql.Date saleDate = resultSet.getDate("saleDate");
        int quantitySold = resultSet.getInt("quantitySold");
        int orderBuyerId = resultSet.getInt("orderBuyerId");

        if (orderId < 0 || orderSellerId < 0 || goodsSoldId < 0 || quantitySold < 0 || orderBuyerId < 0) {
            throw new IllegalArgumentException("Invalid data retrieved from the database");
        } else {
            return new Order(orderId, orderBuyerId, orderSellerId, goodsSoldId, quantitySold, saleDate);
        }
    }

    /**
     *
     * @param order takes the order Object to check the validity
     * @return boolean
     * @throws IllegalArgumentException if the argument is not valid
     *
     */
    private boolean checkOrderValidity(Order order) throws IllegalArgumentException{
        if(order == null || order.getOrderId() < 0){
            throw new IllegalArgumentException("Goods object or product name cannot be null");
        }else return true;
    }
}


