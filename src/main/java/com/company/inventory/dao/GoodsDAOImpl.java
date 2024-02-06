package com.company.inventory.dao;

import com.company.inventory.exceptions.DAOException;
import com.company.inventory.model.Goods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *  This class serves as an implementation of the interface GoodsDAO
 *  to make requests to the Database finding, updating, deleting or saving
 *  goods.
 */
public class GoodsDAOImpl implements GoodsDAO{

    private final Connection connection;

    private static final Logger log = LoggerFactory.getLogger(BuyerDAOImpl.class);
    public GoodsDAOImpl(Connection connection){
        this.connection = connection;
    }

    /**
     *
     * @param productId takes the ID of the product
     * @return Goods class object
     *
     */

    @Override
    public Goods findById(long productId){
        if(productId > 0) {
            String preparedStatement = "SELECT * FROM goods WHERE productId = ?";
            try (PreparedStatement statement = connection.prepareStatement(preparedStatement)) {
                statement.setLong(1, productId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapResultSetToGoods(resultSet);
                    }
                }
            } catch (SQLException e) {
                throw new DAOException("Error finding Goods by ID", e);
            }
        }else throw new IllegalArgumentException("Product ID must be greater than 0");
        return null;
    }

    /**
     *
     * @return List of Goods
     * Finds all the goods into the DB
     *
     */

    @Override
    public List<Goods> findAll() {
        String preparedStatement = "SELECT * FROM goods";
        List<Goods> goodsList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(preparedStatement);){

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    Goods goods = mapResultSetToGoods(resultSet);
                    goodsList.add(goods);
                }
            }
            if(!goodsList.isEmpty()){
                return goodsList;
            }else {
                throw new DAOException("Error in finding elements from table goods");
            }
        }catch (SQLException e){
            throw new DAOException("Error finding Goods into method findAll");
        }
    }

    /**
     *
     * @param goods
     * Saves the goods into the DB
     *
     */

    @Override
    public void save(Goods goods) {
        if(checkGoodsValidity(goods)) {
            String statement = "INSERT INTO goods (productName, quantity) VALUES(?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                connection.setAutoCommit(false);

                preparedStatement.setString(1, goods.getProductName());
                preparedStatement.setInt(2, goods.getQuantity());

                try {
                    preparedStatement.executeQuery();
                    connection.commit();

                } catch (SQLException ex) {
                    connection.rollback();
                    log.error("Error in inserting elements table goods", ex);
                }
            } catch (SQLException e) {
                throw new DAOException("Error inserting elements into table goods method save");
            }
        }
    }

    /**
     *
     * @param goods
     * Updates the DB with the specified good
     *
     */

    @Override
    public void update(Goods goods) {
        if(checkGoodsValidity(goods)) {
            String query = "UPDATE goods SET productName = ?, quantity = ? WHERE productId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                connection.setAutoCommit(false);

                preparedStatement.setString(1, goods.getProductName());
                preparedStatement.setInt(2, goods.getQuantity());
                preparedStatement.setLong(3, goods.getProductId());

                try {
                    preparedStatement.executeUpdate();
                    connection.commit();

                }catch (SQLException ex){
                    connection.rollback();
                    log.error("Error in updating table goods", ex);
                }
            } catch (SQLException e) {
                throw new DAOException("Error updating goods table", e);
            }
        }
    }

    /**
     *
     * @param goodsId
     * Deletes the goods specified with the ID
     *
     */

    @Override
    public void delete(long goodsId) {
        if(goodsId > 0) {
            String query = "DELETE FROM goods WHERE productId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, goodsId);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException("Error deleting goods from goods table", e);
            }
        }else throw new IllegalArgumentException("Goods ID must be greater than 0");
    }

    /**
     *
     * @param resultSet takes the ResultSet from findAll
     * @return the Goods mapped
     * @throws SQLException //
     * @throws DAOException //
     * Serves to map the Goods as an Object
     *
     */

    private Goods mapResultSetToGoods(ResultSet resultSet) throws SQLException{
        long productId = resultSet.getLong("productId");
        String productName = resultSet.getString("productName");
        int quantity = resultSet.getInt("quantity");

        if(productName == null || quantity < 0){
            throw new IllegalArgumentException("Invalid data retrieved from the database");
        }else {
            return new Goods(productId, productName, quantity);
        }
    }

    /**
     *
     * @param goods takes the goods Object to check the validity
     * @return boolean
     * @throws IllegalArgumentException if the argument is not valid
     *
     */
    private boolean checkGoodsValidity(Goods goods) throws IllegalArgumentException{
        if(goods == null || goods.getProductName().isEmpty()){
            throw new IllegalArgumentException("Goods object or product name cannot be null");
        }else return true;
    }
}
