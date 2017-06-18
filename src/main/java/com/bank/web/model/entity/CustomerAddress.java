package com.bank.web.model.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "bank.customer_address")
public class CustomerAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressID;

    @Column(name = "value")
    private String value;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "address_type")
    private Integer addressType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private CustomerInfo customer;

    @Transient
    private String addressTypeLabel;

    public CustomerAddress() {}

    public CustomerAddress(String value, LocalDate dateCreated, LocalDate dateModified, Integer isActive, String userID, Integer addressType, CustomerInfo customer, String addressTypeLabel) {
        this.value = value;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.isActive = isActive;
        this.userID = userID;
        this.addressType = addressType;
        this.customer = customer;
        this.addressTypeLabel = addressTypeLabel;
    }

    public Integer getAddressID() {
        return addressID;
    }

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerInfo customer) {
        this.customer = customer;
    }

    public String getAddressTypeLabel() {
        return addressTypeLabel;
    }

    public void setAddressTypeLabel(String addressTypeLabel) {
        this.addressTypeLabel = addressTypeLabel;
    }

    public Date getConvertedDate(LocalDate localDate) {

        if (localDate != null)
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return null;
    }
}
