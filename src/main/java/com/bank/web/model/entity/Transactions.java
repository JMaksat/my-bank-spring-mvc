package com.bank.web.model.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "bank.transactions")
public class Transactions implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionID;

    @Column(name = "operation_type")
    private Integer operationType;

    @Column(name = "is_reversed")
    private Integer isReversed;

    @Column(name = "transaction_sum")
    private Double transactionSum;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "transaction_time")
    private LocalTime transactionTime;

    @Column(name = "user_id")
    private String userID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_debit")
    private Accounts accountDebit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_credit")
    private Accounts accountCredit;

    @Transient
    private String operationTypeLabel;

    @Transient
    private String accountDebitLabel;

    @Transient
    private String accountCreditLabel;

    public Transactions() {}

    public Transactions(Integer operationType, Integer isReversed, Double transactionSum, LocalDate transactionDate, LocalTime transactionTime, String userID, Accounts accountDebit, Accounts accountCredit, String operationTypeLabel, String accountDebitLabel, String accountCreditLabel) {
        this.operationType = operationType;
        this.isReversed = isReversed;
        this.transactionSum = transactionSum;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.userID = userID;
        this.accountDebit = accountDebit;
        this.accountCredit = accountCredit;
        this.operationTypeLabel = operationTypeLabel;
        this.accountDebitLabel = accountDebitLabel;
        this.accountCreditLabel = accountCreditLabel;
    }

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

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Accounts getAccountDebit() {
        return accountDebit;
    }

    public void setAccountDebit(Accounts accountDebit) {
        this.accountDebit = accountDebit;
    }

    public Accounts getAccountCredit() {
        return accountCredit;
    }

    public void setAccountCredit(Accounts accountCredit) {
        this.accountCredit = accountCredit;
    }

    public String getOperationTypeLabel() {
        return operationTypeLabel;
    }

    public void setOperationTypeLabel(String operationTypeLabel) {
        this.operationTypeLabel = operationTypeLabel;
    }

    public String getAccountDebitLabel() {
        return accountDebitLabel;
    }

    public void setAccountDebitLabel(String accountDebitLabel) {
        this.accountDebitLabel = accountDebitLabel;
    }

    public String getAccountCreditLabel() {
        return accountCreditLabel;
    }

    public void setAccountCreditLabel(String accountCreditLabel) {
        this.accountCreditLabel = accountCreditLabel;
    }

    public Date getConvertedDate(LocalDate localDate) {

        if (localDate != null)
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return null;
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
                ", operationTypeLabel='" + operationTypeLabel + '\'' +
                ", accountDebitLabel='" + accountDebitLabel + '\'' +
                ", accountCreditLabel='" + accountCreditLabel + '\'' +
                '}';
    }
}
