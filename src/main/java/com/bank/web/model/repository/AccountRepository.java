package com.bank.web.model.repository;

import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.Transactions;

import java.util.List;

public interface AccountRepository {

    List<Accounts> accountsList();

    void changeAccountStatus(Accounts account);

    void suspendAccount(Accounts account);

    void releaseAccount(Accounts account);

    void decreaseRest (Accounts account);

    void increaseRest (Accounts account);

    void getAccountRest (Accounts account);

    List<Transactions> getTransactions (Accounts account);

    void addAccount(Accounts account);

    void addTransaction (Transactions transaction);

    void updateAccount (Accounts account);
}
