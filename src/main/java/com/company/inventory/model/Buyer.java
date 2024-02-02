package com.company.inventory.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Buyer implements Serializable {

    private long buyerId;
    private String buyerName;
    private String buyerSurname;
    private String buyerPhoneNumber;
    private List<Goods> interestedGoods;

    public Buyer(long buyerId, String buyerName, String buyerSurname, String buyerPhoneNumber){
        setBuyerId(buyerId);
        setBuyerName(buyerName);
        setBuyerSurname(buyerSurname);
        setBuyerPhoneNumber(buyerPhoneNumber);
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        if(buyerId >= 0){
            this.buyerId = buyerId;
        }else{
            throw new IllegalArgumentException("Buyer Id must be greater than 0");
        }
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        if (buyerName != null && !buyerName.isEmpty()){
            this.buyerName = buyerName;
        }else{
            throw new IllegalArgumentException("Buyer name cannot be null or empty");
        }
    }

    public String getBuyerSurname() {
        return buyerSurname;
    }

    public void setBuyerSurname(String buyerSurname) {
        if (buyerSurname != null && !buyerSurname.isEmpty()){
            this.buyerSurname = buyerSurname;
        }else{
            throw new IllegalArgumentException("Buyer surname cannot be null or empty");
        }
    }

    public String getBuyerPhoneNumber() {
        return buyerPhoneNumber;
    }

    public void setBuyerPhoneNumber(String buyerPhoneNumber) {
        if (buyerPhoneNumber != null && !buyerPhoneNumber.isEmpty()){
            this.buyerPhoneNumber = buyerPhoneNumber;
        }else{
            throw new IllegalArgumentException("Buyer phone number cannot be null or empty");
        }
    }

    public List<Goods> getInterestedGoods() {
        return interestedGoods;
    }

    public void setInterestedGoods(List<Goods> interestedGoods) {
        if(interestedGoods!= null && !interestedGoods.isEmpty()){
            this.interestedGoods = interestedGoods;
        }else{
            throw new IllegalArgumentException("List interested goods must contins elements");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buyer buyer = (Buyer) o;
        return getBuyerId() == buyer.getBuyerId() && Objects.equals(getBuyerName(), buyer.getBuyerName()) && Objects.equals(getBuyerSurname(), buyer.getBuyerSurname()) && Objects.equals(getBuyerPhoneNumber(), buyer.getBuyerPhoneNumber()) && Objects.equals(getInterestedGoods(), buyer.getInterestedGoods());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBuyerId(), getBuyerName(), getBuyerSurname(), getBuyerPhoneNumber(), getInterestedGoods());
    }
}
