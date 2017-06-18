package com.bank.web.model.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bank.accounts")
public class Accounts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountID;

    @Column(name = "account_number")
    private String accountNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_owner")
    private CustomerInfo accountOwner;

    @Column(name = "date_opened")
    private LocalDate dateOpened;

    @Column(name = "date_closed")
    private LocalDate dateClosed;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "account_type")
    private Integer accountType;

    @Column(name = "is_suspended")
    private Integer isSuspended;

    @Column(name = "comment")
    private String comment;

    @Transient
    private Double restSum;

    @Transient
    private String accountTypeLabel;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accountDebit")
    private Set<Transactions> transactionsByDebit = new HashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accountCredit")
    private Set<Transactions> transactionsByCredit = new HashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private Set<AccountRest> accountRests = new HashSet<>(0);

    public Accounts(){}

    public Accounts(String accountNumber, CustomerInfo accountOwner, LocalDate dateOpened, LocalDate dateClosed, LocalDate dateCreated, LocalDate dateModified, String userID, Integer accountType, Integer isSuspended, String comment, Double restSum, String accountTypeLabel, Set<Transactions> transactionsByDebit, Set<Transactions> transactionsByCredit, Set<AccountRest> accountRests) {
        this.accountNumber = accountNumber;
        this.accountOwner = accountOwner;
        this.dateOpened = dateOpened;
        this.dateClosed = dateClosed;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.userID = userID;
        this.accountType = accountType;
        this.isSuspended = isSuspended;
        this.comment = comment;
        this.restSum = restSum;
        this.accountTypeLabel = accountTypeLabel;
        this.transactionsByDebit = transactionsByDebit;
        this.transactionsByCredit = transactionsByCredit;
        this.accountRests = accountRests;
    }

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

    public CustomerInfo getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(CustomerInfo accountOwner) {
        this.accountOwner = accountOwner;
    }

    public LocalDate getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(LocalDate dateOpened) {
        this.dateOpened = dateOpened;
    }

    public LocalDate getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(LocalDate dateClosed) {
        this.dateClosed = dateClosed;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
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

    public Double getRestSum() {
        return restSum;
    }

    public void setRestSum(Double restSum) {
        this.restSum = restSum;
    }

    public String getAccountTypeLabel() {
        return accountTypeLabel;
    }

    public void setAccountTypeLabel(String accountTypeLabel) {
        this.accountTypeLabel = accountTypeLabel;
    }

    public Set<Transactions> getTransactionsByDebit() {
        return transactionsByDebit;
    }

    public void setTransactionsByDebit(Set<Transactions> transactionsByDebit) {
        this.transactionsByDebit = transactionsByDebit;
    }

    public Set<Transactions> getTransactionsByCredit() {
        return transactionsByCredit;
    }

    public void setTransactionsByCredit(Set<Transactions> transactionsByCredit) {
        this.transactionsByCredit = transactionsByCredit;
    }

    public Set<AccountRest> getAccountRests() {
        return accountRests;
    }

    public void setAccountRests(Set<AccountRest> accountRests) {
        this.accountRests = accountRests;
    }

    public Date getConvertedDate(LocalDate localDate) {

        if (localDate != null)
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return null;
    }
}
