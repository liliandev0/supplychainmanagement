package com.company.inventory.dao;

import com.company.inventory.model.Goods;
import com.company.inventory.model.Seller;

import java.util.List;


public interface GoodsDAO {
    Goods findById(long productId);
    List<Goods> findAll();
    void save(Goods goods);
    void update(Goods goods);
    void delete(long sellerId);
}