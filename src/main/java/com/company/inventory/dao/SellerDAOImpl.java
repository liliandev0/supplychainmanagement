package com.company.inventory.dao;

import com.company.inventory.exceptions.DAOException;
import com.company.inventory.model.Seller;
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
 * This class serves to implement SellerDAO interface
 * and to make requests to the Database for retrieving data,
 * deleting it or modifying it.
 *
 */
public class SellerDAOImpl implements SellerDAO {

    private final Connection connection;
    private static final Logger log = LoggerFactory.getLogger(SellerDAOImpl.class);

    public SellerDAOImpl(Connection connection){
        this.connection = connection;
    }

    /**
     *
     * @param sellerId sellerId
     * @return returns the Seller by the ID
     *
     */

    @Override
    public Seller findById(long sellerId) {
        if (sellerId > 0) {
            String preparedStatement = "SELECT * FROM seller WHERE sellerId = ?";
            try (PreparedStatement statement = connection.prepareStatement(preparedStatement)) {
                statement.setLong(1, sellerId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToSeller(resultSet);
                    }
                }
            } catch (SQLException e) {
                throw new DAOException("Error finding Order by ID", e);
            }
        } else throw new IllegalArgumentException("Order ID must be greater than 0");
        return null;
    }

    /**
     *
     * @return returns the Sellers from the DB
     *
     */

    @Override
    public List<Seller> findAll() {
        String preparedStatement = "SELECT * FROM seller";
        List<Seller> sellerList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(preparedStatement)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Seller seller = mapResultSetToSeller(resultSet);
                sellerList.add(seller);
            }
            if (!sellerList.isEmpty()) {
                return sellerList;
            } else {
                throw new DAOException("Error in finding elements from table seller");
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding Seller into method findAll");
        }
    }

    /**
     *
     * @param seller saves the Seller into the DB
     *
     */

    @Override
    public void save(Seller seller) {
        if (checkSellerValidity(seller)) {
            String statement = "INSERT INTO seller (sellerName, contactEmail, contactPhone) VALUES(?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                connection.setAutoCommit(false);

                preparedStatement.setString(1, seller.getSellerName());
                preparedStatement.setString(2, seller.getContactEmail());
                preparedStatement.setString(3, seller.getContactPhone());

                try {
                    preparedStatement.executeQuery();
                    connection.commit();

                } catch (SQLException ex) {
                    connection.rollback();
                    log.error("Error inserting elements into table seller", ex);
                }
            } catch (SQLException e) {
                throw new DAOException("Error inserting elements into table seller method save");
            }
        }
    }

    /**
     *
     * @param seller updates the Seller
     *
     */

    @Override
    public void update(Seller seller) {
        if(checkSellerValidity(seller)) {
            String query = "UPDATE seller SET sellerName = ?, contactEmail = ?, contactPhone = ?, WHERE orderId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);

                preparedStatement.setString(1, seller.getSellerName());
                preparedStatement.setString(2, seller.getContactEmail());
                preparedStatement.setString(3, seller.getContactPhone());

                try {
                    preparedStatement.executeUpdate();
                    connection.commit();

                }catch (SQLException ex){
                    connection.rollback();
                    log.error("Error in updating table seller", ex);
                }
            } catch (SQLException e) {
                throw new DAOException("Error updating seller table", e);
            }
        }

    }

    /**
     *
     * @param sellerId deletes the Seller
     *
     */

    @Override
    public void delete(long sellerId) {
        if(sellerId > 0) {
            String query = "DELETE FROM seller WHERE sellerId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, sellerId);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException("Error deleting seller from seller table", e);
            }
        }else throw new IllegalArgumentException("Seller ID must be greater than 0");
    }

    /**
     *
     * @param resultSet resultSet
     * @return returns the ResultSet mapped to the Seller
     * @throws SQLException throws Exception in case of error
     *
     */

    private Seller mapResultSetToSeller(ResultSet resultSet) throws SQLException {
        int sellerId = resultSet.getInt("sellerId");
        String sellerName = resultSet.getString("sellerName");
        String contactEmail = resultSet.getString("contactEmail");
        String contactPhone = resultSet.getString("contactPhone");

        if(sellerName.isEmpty() || contactEmail.isEmpty() || contactPhone.isEmpty()){
            throw new IllegalArgumentException("Illegal data retrieved from the Database");
        }else{
            return new Seller(sellerId, sellerName, contactEmail, contactPhone);
        }
    }

    /**
     *
     * @param seller Seller
     * @return true if Seller is valid
     * @throws IllegalArgumentException if it is not valid
     *
     */

    private boolean checkSellerValidity(Seller seller) throws IllegalArgumentException{
        if(seller == null || seller.getSellerId() < 0){
            throw new IllegalArgumentException("Seller object or ID cannot be null");
        }else return true;
    }
}

