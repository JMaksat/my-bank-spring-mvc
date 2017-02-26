package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

import java.util.Date;

@RowMapperClass(tableName = "bank.transactions")
public class Transactions {

    @RowMapperField(columnName = "transaction_id")
    private Integer transactionID;

    @RowMapperField(columnName = "operation_type")
    private String operationType;

    @RowMapperField(columnName = "is_reversed")
    private Integer isReversed;

    @RowMapperField(columnName = "transaction_sum")
    private Double transactionSum;

    @RowMapperField(columnName = "transaction_date")
    private Date transactionDate;

    @RowMapperField(columnName = "transaction_time")
    private String transactionTime;

    @RowMapperField(columnName = "user_id")
    private String userID;

    @RowMapperField(columnName = "account_debit")
    private String accountDebit;

    @RowMapperField(columnName = "account_credit")
    private String accountCredit;

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
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

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAccountDebit() {
        return accountDebit;
    }

    public void setAccountDebit(String accountDebit) {
        this.accountDebit = accountDebit;
    }

    public String getAccountCredit() {
        return accountCredit;
    }

    public void setAccountCredit(String accountCredit) {
        this.accountCredit = accountCredit;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "transactionID=" + transactionID +
                ", operationType='" + operationType + '\'' +
                ", isReversed=" + isReversed +
                ", transactionSum=" + transactionSum +
                ", transactionDate=" + transactionDate +
                ", transactionTime='" + transactionTime + '\'' +
                ", userID='" + userID + '\'' +
                ", accountDebit='" + accountDebit + '\'' +
                ", accountCredit='" + accountCredit + '\'' +
                '}';
    }
}
