package com.company.inventory.model;

import java.io.Serializable;

/**
 *  Represents goods and item in the inventory
 *
 */
public class Goods implements Serializable {

    private long productId;

    private String productName;

    private int quantity;

    public Goods(long productId, String productName, int quantity) {
        setProductId(productId);
        setProductName(productName);
        setQuantity(quantity);
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        if (productId > 0) {
            this.productId = productId;
        } else {
            throw new IllegalArgumentException("Product Id must be greater than 0");
        }
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName != null && !productName.isEmpty()) {
            this.productName = productName;
        } else {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {

        if (quantity >= 0) {
            this.quantity = quantity;
        } else {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
    }

    @Override
    public String toString() {
        return "Goods{" +
                "productId=" + productId + "\n" +
                "productName=" + productName + "\n" +
                "quantity=" + quantity +
                "}";
    }
}
