package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

import java.util.Date;

@RowMapperClass
public class Transactions {

    @RowMapperField(columnName = "transaction_id")
    private Integer transactionID;

    @RowMapperField(columnName = "operation_type")
    private Integer operationType;

    @RowMapperField(columnName = "is_reversed")
    private Integer isReversed;

    @RowMapperField(columnName = "transaction_sum")
    private Double transactionSum;

    @RowMapperField(columnName = "transaction_date")
    private java.util.Date transactionDate;

    @RowMapperField(columnName = "transaction_time")
    private java.util.Date transactionTime;

    @RowMapperField(columnName = "user_id")
    private String userID;

    @RowMapperField(columnName = "account_debit")
    private Integer accountDebit;

    @RowMapperField(columnName = "account_credit")
    private Integer accountCredit;

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getIsReversed() {
        return isReversed;
    }

    public void setIsReversed(Integer isReversed) {
        this.isReversed = isReversed;
    }

    public Double getTransactionSum() {
        return transactionSum;
    }

    public void setTransactionSum(Double transactionSum) {
        this.transactionSum = transactionSum;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getAccountDebit() {
        return accountDebit;
    }

    public void setAccountDebit(Integer accountDebit) {
        this.accountDebit = accountDebit;
    }

    public Integer getAccountCredit() {
        return accountCredit;
    }

    public void setAccountCredit(Integer accountCredit) {
        this.accountCredit = accountCredit;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "transactionID=" + transactionID +
                ", operationType=" + operationType +
                ", isReversed=" + isReversed +
                ", transactionSum=" + transactionSum +
                ", transactionDate=" + transactionDate +
                ", transactionTime=" + transactionTime +
                ", userID='" + userID + '\'' +
                ", accountDebit=" + accountDebit +
                ", accountCredit=" + accountCredit +
                '}';
    }
}
