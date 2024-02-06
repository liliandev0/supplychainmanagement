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
    private final int orderBuyerId;
    private final int orderSellerId;
    private final int goodsSoldId;
    private final int quantitySoldId;
    private final java.sql.Date saleDate;

    public Order(long orderId, int orderBuyerId, int orderSellerId, int goodsSoldId, int quantitySoldId, java.sql.Date saleDate){
        this.orderId = orderId;
        this.orderBuyerId = orderBuyerId;
        this.orderSellerId = orderSellerId;
        this.goodsSoldId = goodsSoldId;
        this.quantitySoldId = quantitySoldId;
        this.saleDate = saleDate;
    }

    public long getOrderId() {
        return orderId;
    }

    public int getOrderBuyerId() {
        return orderBuyerId;
    }

    public int getOrderSellerId() {
        return orderSellerId;
    }

    public int getGoodsSoldId() {
        return goodsSoldId;
    }

    public int getQuantitySoldId() {
        return quantitySoldId;
    }


    public java.sql.Date getSaleDate() {
        return saleDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getOrderId() == order.getOrderId() && getQuantitySoldId() == order.getQuantitySoldId() && Objects.equals(getOrderBuyerId(), order.getOrderBuyerId()) && Objects.equals(getOrderSellerId(), order.getOrderSellerId()) && Objects.equals(getGoodsSoldId(), order.getGoodsSoldId()) && Objects.equals(getSaleDate(), order.getSaleDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getOrderBuyerId(), getOrderSellerId(), getGoodsSoldId(), getQuantitySoldId(), getSaleDate());
    }

}
