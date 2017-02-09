package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

import java.util.Date;

@RowMapperClass
public class BankParameters {

    @RowMapperField(columnName = "parameter_id")
    private Integer parameterID;

    @RowMapperField(columnName = "parent_id")
    private Integer parentID;

    @RowMapperField(columnName = "parameter_name")
    private Integer parameterName;

    @RowMapperField(columnName = "value")
    private String value;

    @RowMapperField(columnName = "date_created")
    private java.util.Date dateCreated;

    @RowMapperField(columnName = "date_modified")
    private java.util.Date dateModified;

    @RowMapperField(columnName = "active_from")
    private java.util.Date activeFrom;

    @RowMapperField(columnName = "active_to")
    private java.util.Date activeTo;

    @RowMapperField(columnName = "user_id")
    private Integer userID;

    public Integer getParameterID() {
        return parameterID;
    }

    public void setParameterID(Integer parameterID) {
        this.parameterID = parameterID;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public Integer getParameterName() {
        return parameterName;
    }

    public void setParameterName(Integer parameterName) {
        this.parameterName = parameterName;
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

    public Date getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(Date activeFrom) {
        this.activeFrom = activeFrom;
    }

    public Date getActiveTo() {
        return activeTo;
    }

    public void setActiveTo(Date activeTo) {
        this.activeTo = activeTo;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "BankParameters{" +
                "parameterID=" + parameterID +
                ", parentID=" + parentID +
                ", parameterName=" + parameterName +
                ", value='" + value + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", activeFrom=" + activeFrom +
                ", activeTo=" + activeTo +
                ", userID=" + userID +
                '}';
    }
}