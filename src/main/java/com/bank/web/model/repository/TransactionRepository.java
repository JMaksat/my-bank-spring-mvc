package com.bank.web.model.repository;

import com.bank.web.model.entity.Transactions;

import java.util.List;

public interface TransactionRepository {

    List<Transactions> transactionsList();

    List<Transactions> getTransactions (Integer accountID);

    Transactions getTransaction (Integer transactionID);

    Integer getNewTransactionSeq();

    void addTransaction (Transactions transaction);
}
