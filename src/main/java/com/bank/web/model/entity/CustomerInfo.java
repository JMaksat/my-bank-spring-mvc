package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

import java.util.Date;

@RowMapperClass
public class CustomerInfo {

    @RowMapperField(columnName = "customer_id")
    private Integer customerID;

    @RowMapperField(columnName = "first_name")
    private String firstName;

    @RowMapperField(columnName = "last_name")
    private String lastName;

    @RowMapperField(columnName = "middle_name")
    private String middleName;

    @RowMapperField(columnName = "birth_date")
    private java.util.Date birthDate;

    @RowMapperField(columnName = "date_modified")
    private java.util.Date dateModified;

    @RowMapperField(columnName = "is_active")
    private Integer isActive;

    @RowMapperField(columnName = "user_id")
    private String userID;

    @RowMapperField(columnName = "date_created")
    private java.util.Date dateCreated;

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "CustomerInfo{" +
                "customerID=" + customerID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthDate=" + birthDate +
                ", dateModified=" + dateModified +
                ", isActive=" + isActive +
                ", userID='" + userID + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
