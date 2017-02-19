package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

import java.util.Date;

@RowMapperClass
public class CustomerPapers {

    @RowMapperField(columnName = "paper_id")
    private Integer paperID;

    @RowMapperField(columnName = "value")
    private String value;

    @RowMapperField(columnName = "date_created")
    private java.util.Date dateCreated;

    @RowMapperField(columnName = "date_modified")
    private java.util.Date dateModified;

    @RowMapperField(columnName = "is_active")
    private Integer isActive;

    @RowMapperField(columnName = "user_id")
    private String userID;

    @RowMapperField(columnName = "paper_type")
    private String paperType;

    @RowMapperField(columnName = "customer_id")
    private Integer customerID;

    public Integer getPaperID() {
        return paperID;
    }

    public void setPaperID(Integer paperID) {
        this.paperID = paperID;
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

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "CustomerPapers{" +
                "paperID=" + paperID +
                ", value='" + value + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", isActive=" + isActive +
                ", userID='" + userID + '\'' +
                ", paperType='" + paperType + '\'' +
                ", customerID=" + customerID +
                '}';
    }
}
