package com.company.inventory.dao;

import com.company.inventory.model.Order;

import java.util.List;

public interface OrderDAO {
    Order findById(long orderId);
    List<Order> findAll();
    void save(Order order);
    void update(Order order);
    void delete(long orderId);
}
