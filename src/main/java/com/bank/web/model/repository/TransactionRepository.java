package com.bank.web.model.repository;

import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.Transactions;

import java.util.List;

public interface TransactionRepository {

    List<Transactions> getAlltransactions();

    List<Transactions> getTransactions (Accounts account);

    Transactions getTransaction (Integer transactionID);

    Boolean addTransaction (Transactions transaction);
}
