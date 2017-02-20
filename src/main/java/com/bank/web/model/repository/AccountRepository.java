package com.bank.web.model.repository;

import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.Transactions;

import java.util.List;

public interface AccountRepository {

    List<Accounts> accountsList(Boolean suspended);

    List<Accounts> getAccount(Integer accountID);

    void changeStatus (Integer accountID, Boolean status);

    void decreaseRest (Integer accountID, Double amount);

    void increaseRest (Integer accountID, Double amount);

    void getAccountRest (Integer accountID);

    List<Transactions> getTransactions (Integer accountID);

    void addAccount (Accounts account);

    void addTransaction (Transactions transaction);

    void updateAccount (Accounts account);

    void closeAccount(Integer accountID);
}
