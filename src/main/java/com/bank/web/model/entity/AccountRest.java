package com.bank.web.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "bank.account_rest")
public class AccountRest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rest_id")
    private Integer restID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Accounts account;

    @Column(name = "rest_sum")
    private Double restSum;

    @Column(name = "transaction_id")
    private Integer transactionID;

    @Column(name = "rest_date")
    private LocalDate restDate;

    @Column(name = "rest_time")
    private LocalDate restTime;

    public AccountRest() {}

    public AccountRest(Accounts account, Double restSum, Integer transactionID, LocalDate restDate, LocalDate restTime) {
        this.account = account;
        this.restSum = restSum;
        this.transactionID = transactionID;
        this.restDate = restDate;
        this.restTime = restTime;
    }

    public Integer getRestID() {
        return restID;
    }

    public void setRestID(Integer restID) {
        this.restID = restID;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
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

    public LocalDate getRestDate() {
        return restDate;
    }

    public void setRestDate(LocalDate restDate) {
        this.restDate = restDate;
    }

    public LocalDate getRestTime() {
        return restTime;
    }

    public void setRestTime(LocalDate restTime) {
        this.restTime = restTime;
    }

    @Override
    public String toString() {
        return "AccountRest{" +
                "restID=" + restID +
                ", account=" + account +
                ", restSum=" + restSum +
                ", transactionID=" + transactionID +
                ", restDate=" + restDate +
                ", restTime=" + restTime +
                '}';
    }
}
