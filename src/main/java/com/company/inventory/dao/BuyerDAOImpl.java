package com.company.inventory.dao;

import com.company.inventory.exceptions.DAOException;
import com.company.inventory.model.Buyer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *
 *  This class serves as an implementation of the interface GoodsDAO
 *  to make requests to the Database finding, updating, deleting or saving
 *  goods.
 *
 */


public class BuyerDAOImpl implements BuyerDAO{

    private final Connection connection;

    private static final Logger log = LoggerFactory.getLogger(BuyerDAOImpl.class);

    public BuyerDAOImpl(Connection connection){
        this.connection = connection;
    }

    /**
     *
     * @param buyerId takes the ID of the buyer
     * @return Buyer class object
     *
     */

    @Override
    public Buyer findById(long buyerId){
        String preparedStatement = "SELECT * FROM buyer WHERE buyerId = ?";
        try(PreparedStatement statement = connection.prepareStatement(preparedStatement)){
            statement.setLong(1, buyerId);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return mapResultSetToBuyer(resultSet);
                }
            }
        }catch (SQLException e){
            throw new DAOException("Error finding Buyer by ID into 'findById()' ", e);
        }
        return null;
    }

    /**
     *
     * @return List of
     * Finds all the goods into the DB
     *
     */

    @Override
    public List<Buyer> findAll() {
        String preparedStatement = "SELECT * FROM buyer";
        List<Buyer> buyerList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(preparedStatement)){

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    Buyer buyer = mapResultSetToBuyer(resultSet);
                    buyerList.add(buyer);
                }
            }
            if(!buyerList.isEmpty()){
                return buyerList;
            }else {
                throw new DAOException("Error in finding elements from table 'buyer'");
            }
        }catch (SQLException e){
            throw new DAOException("Error finding Goods into 'findAll()' ", e);
        }
    }

    /**
     *
     * @param buyer takes the object Buyer to insert
     *
     */

    @Override
    public void save(Buyer buyer) {
        if(checkBuyerValidity(buyer)) {
            String statement = "INSERT INTO buyer (buyerName, buyerSurname, buyerPhoneNumber) VALUES(?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                connection.setAutoCommit(false);

                preparedStatement.setString(1, buyer.getBuyerName());
                preparedStatement.setString(2, buyer.getBuyerSurname());
                preparedStatement.setString(3, buyer.getBuyerPhoneNumber());

                try {
                    preparedStatement.executeQuery();
                    connection.commit();

                } catch (SQLException ex) {
                    connection.rollback();
                    log.error("Error inserting elements into table buyer 'save()", ex);
                }
            } catch (SQLException e) {
                throw new DAOException("Error inserting elements into table buyer 'save()' ", e);
            }
        }
    }

    /**
     *
     * @param buyer
     * Updates the DB with the specified buyer
     *
     */

    @Override
    public void update(Buyer buyer) {
        if(checkBuyerValidity(buyer)) {
            String query = "UPDATE buyer SET buyerName = ?, buyerSurname = ?, buyerPhoneNumber = ? WHERE buyerId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);

                preparedStatement.setString(1, buyer.getBuyerName());
                preparedStatement.setString(2, buyer.getBuyerSurname());
                preparedStatement.setString(3, buyer.getBuyerPhoneNumber());

                try {
                    preparedStatement.executeUpdate();
                }catch (SQLException ex){
                    connection.rollback();
                    log.error("Error occurred during update of buyer table", ex);
                }
            } catch (SQLException e) {
                throw new DAOException("Error updating goods in 'goods' table", e);
            }
        }
    }

    /**
     *
     * @param buyerId
     * Deletes the buyer specified with the ID
     *
     */

    @Override
    public void delete(long buyerId) {
        if(buyerId > 0) {
            String query = "DELETE FROM buyer WHERE buyerId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);
                preparedStatement.setLong(1, buyerId);
                try {
                    preparedStatement.executeUpdate();
                }catch (SQLException ex){
                    connection.rollback();
                    log.error("Error in deleting buyer", ex);
                }
            } catch (SQLException e) {
                throw new DAOException("Error deleting buyer from 'buyer' table", e);
            }
        }
    }

    /**
     *
     * @param resultSet takes the result of the findAll ResultSet
     * @return a Buyer object
     * @throws SQLException
     * Serves to map the Buyer to an Object
     *
     */

    private Buyer mapResultSetToBuyer(ResultSet resultSet) throws SQLException{
        long buyerId = resultSet.getLong("buyerId");
        String buyerName = resultSet.getString("buyerName");
        String buyerSurname = resultSet.getString("buyerSurname");
        String buyerPhoneNumber = resultSet.getString("buyerPhoneNumber");

        if(buyerName == null || buyerSurname == null || buyerPhoneNumber == null){
            throw new IllegalArgumentException("Invalid data retrieved from the database");
        }else{
            return new Buyer(buyerId, buyerName, buyerSurname, buyerPhoneNumber);
        }
    }

    /**
     *
     * @param buyer takes the buyer Object to check the validity
     * @return boolean
     * @throws IllegalArgumentException if the argument is not valid
     *
     */

    private boolean checkBuyerValidity(Buyer buyer) throws IllegalArgumentException{
        if(buyer == null){
            throw new IllegalArgumentException("Buyer object cannot be null");
        }else if(buyer.getBuyerName() == null || buyer.getBuyerSurname() == null
                || buyer.getBuyerPhoneNumber() == null){
            throw new IllegalArgumentException("Buyer attributes (name, surname, phone number)" +
                    " cannot be null");
        }else return true;
    }

}
