package com.company.inventory.dao;

import com.company.inventory.model.Buyer;
import com.company.inventory.model.Goods;

import java.util.List;

public interface BuyerDAO {
    Buyer findById(long buyerId);
    List<Buyer> findAll();
    void save(Buyer buyer);
    void update(Buyer buyer);
    void delete(long buyerId);
}
