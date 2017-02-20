package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

import java.util.Date;

@RowMapperClass(tableName = "bank.accounts")
public class Accounts {

    @RowMapperField(columnName = "account_id")
    private Integer accountID;

    @RowMapperField(columnName = "account_number")
    private String accountNumber;

    @RowMapperField(columnName = "account_owner")
    private Integer accountOwner;

    @RowMapperField(columnName = "date_opened")
    private java.util.Date dateOpened;

    @RowMapperField(columnName = "date_closed")
    private java.util.Date dateClosed;

    @RowMapperField(columnName = "date_created")
    private java.util.Date dateCreated;

    @RowMapperField(columnName = "date_modified")
    private java.util.Date dateModified;

    @RowMapperField(columnName = "user_id")
    private String userID;

    @RowMapperField(columnName = "account_type")
    private String accountType;

    @RowMapperField(columnName = "is_suspended")
    private Integer isSuspended;

    @RowMapperField(columnName = "comment")
    private String comment;

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(Integer accountOwner) {
        this.accountOwner = accountOwner;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Integer getIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(Integer isSuspended) {
        this.isSuspended = isSuspended;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "accountID=" + accountID +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountOwner=" + accountOwner +
                ", dateOpened=" + dateOpened +
                ", dateClosed=" + dateClosed +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", userID='" + userID + '\'' +
                ", accountType='" + accountType + '\'' +
                ", isSuspended=" + isSuspended +
                ", comment='" + comment + '\'' +
                '}';
    }
}
