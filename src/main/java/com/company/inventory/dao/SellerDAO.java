package com.company.inventory.dao;

import com.company.inventory.model.Seller;

import java.util.List;

public interface SellerDAO {
    Seller findById(long sellerId);
    List<Seller> findAll();
    void save(Seller seller);
    void update(Seller seller);
    void delete(long sellerId);
}
