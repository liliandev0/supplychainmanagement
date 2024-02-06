package com.company.inventory.model;

import com.company.inventory.dao.SellerDAOImpl;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.Test;

import java.io.Serializable;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

/**
 * Represents the seller and the company's name
 *
 */

public class Seller implements Serializable {

    private long sellerId;
    private String companyName;
    private String sellerName;
    private String contactEmail;
    private String contactPhoneNumber;

    public Seller(int sellerId, String sellerName, String contactEmail, String contactPhoneNumber){
        setSellerId(sellerId);
        setSellerName(sellerName);
        setContactEmail(contactEmail);
        setContactPhone(contactPhoneNumber);
    }


    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        if(sellerId > 0){
            this.sellerId = sellerId;
        }else {
            throw new IllegalArgumentException("Seller Id must be greater than 0");
        }
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        if(companyName != null && !companyName.isEmpty()){
            this.companyName = companyName;
        }else{
            throw new IllegalArgumentException("Company name cannot be null or empty");
        }
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        if(sellerName != null && !sellerName.isEmpty()) {
            this.sellerName = sellerName;
        }else{
            throw new IllegalArgumentException("Seller name cannot be null or empty");
        }
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        if(sellerName != null && !sellerName.isEmpty()) {
            if(emailValidator(contactEmail)){
                this.contactEmail = contactEmail;
            }else{
                throw new IllegalArgumentException("Email must be valid");
            }
        }else{
            throw new IllegalArgumentException("Contact email cannot be null or empty");
        }
    }

    public String getContactPhone() {
        return contactPhoneNumber;
    }

    public void setContactPhone(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return sellerId == seller.sellerId && Objects.equals(companyName, seller.companyName) && Objects.equals(sellerName, seller.sellerName) && Objects.equals(contactEmail, seller.contactEmail) && Objects.equals(contactPhoneNumber, seller.contactPhoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerId, companyName, sellerName, contactEmail, contactPhoneNumber);
    }

    @Test
    private boolean emailValidator(String email){
        try {
            assertTrue(EmailValidator.getInstance().isValid(email));
            return true;
        }catch (AssertionError error){
            System.out.println("Email validation error: " + error.getMessage());
            return false;
        }
    }
}
