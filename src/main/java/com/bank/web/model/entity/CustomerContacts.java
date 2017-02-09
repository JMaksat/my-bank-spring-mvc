package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

import java.util.Date;

@RowMapperClass
public class CustomerContacts {

    @RowMapperField(columnName = "contact_id")
    private Integer contactID;

    @RowMapperField(columnName = "value")
    private String value;

    @RowMapperField(columnName = "date_created")
    private java.util.Date dateCreated;

    @RowMapperField(columnName = "date_modified")
    private java.util.Date dateModified;

    @RowMapperField(columnName = "is_active")
    private Boolean isActive;

    @RowMapperField(columnName = "user_id")
    private Integer userID;

    @RowMapperField(columnName = "contact_type")
    private Integer contactType;

    @RowMapperField(columnName = "customer_id")
    private Integer customerID;

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getContactType() {
        return contactType;
    }

    public void setContactType(Integer contactType) {
        this.contactType = contactType;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "CustomerContacts{" +
                "contactID=" + contactID +
                ", value='" + value + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", isActive=" + isActive +
                ", userID=" + userID +
                ", contactType=" + contactType +
                ", customerID=" + customerID +
                '}';
    }
}
