package com.company.inventory.model;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *  This class represents the orders made immutable
 *
 */
public final class Order implements Serializable {

    private final long orderId;
    private final Buyer orderBuyer;
    private final Seller orderSeller;
    private final List<Goods> goodsSold;
    private final int quantitySold;
    private final LocalDate saleDate;

    public Order(long orderId, Buyer orderBuyer, Seller orderSeller, List<Goods> goodsSold, int quantitySold, LocalDate saleDate){
        this.orderId = orderId;
        this.orderBuyer = orderBuyer;
        this.orderSeller = orderSeller;
        this.goodsSold = Collections.unmodifiableList(goodsSold); // Ensures immutability of the list
        this.quantitySold = quantitySold;
        this.saleDate = saleDate;
    }

    public long getOrderId() {
        return orderId;
    }

    public Buyer getOrderBuyer() {
        return orderBuyer;
    }

    public Seller getOrderSeller() {
        return orderSeller;
    }

    public List<Goods> getGoodsSold() {
        return goodsSold;
    }

    public int getQuantitySold() {
        return quantitySold;
    }


    public LocalDate getSaleDate() {
        return saleDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getOrderId() == order.getOrderId() && getQuantitySold() == order.getQuantitySold() && Objects.equals(getOrderBuyer(), order.getOrderBuyer()) && Objects.equals(getOrderSeller(), order.getOrderSeller()) && Objects.equals(getGoodsSold(), order.getGoodsSold()) && Objects.equals(getSaleDate(), order.getSaleDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getOrderBuyer(), getOrderSeller(), getGoodsSold(), getQuantitySold(), getSaleDate());
    }

}
