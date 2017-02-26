package com.bank.web.model.entity;

import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperClass;
import org.springframework.jdbc.roma.api.config.provider.annotation.RowMapperField;

import java.util.Date;

@RowMapperClass(tableName = "bank.account_rest")
public class AccountRest {

    @RowMapperField(columnName = "rest_id")
    private Integer restID;

    @RowMapperField(columnName = "account_id")
    private Integer accountID;

    @RowMapperField(columnName = "rest_sum")
    private Double restSum;

    @RowMapperField(columnName = "transaction_id")
    private Integer transactionID;

    @RowMapperField(columnName = "rest_date")
    private Date restDate;

    @RowMapperField(columnName = "rest_time")
    private Date restTime;

    public Integer getRestID() {
        return restID;
    }

    public void setRestID(Integer restID) {
        this.restID = restID;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Double getRestSum() {
        return restSum;
    }

    public void setRestSum(Double restSum) {
        this.restSum = restSum;
    }

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public Date getRestDate() {
        return restDate;
    }

    public void setRestDate(Date restDate) {
        this.restDate = restDate;
    }

    public Date getRestTime() {
        return restTime;
    }

    public void setRestTime(Date restTime) {
        this.restTime = restTime;
    }

    @Override
    public String toString() {
        return "AccountRest{" +
                "restID=" + restID +
                ", accountID=" + accountID +
                ", restSum=" + restSum +
                ", transactionID=" + transactionID +
                ", restDate=" + restDate +
                ", restTime=" + restTime +
                '}';
    }
}
