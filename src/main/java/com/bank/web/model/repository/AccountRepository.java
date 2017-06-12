package com.bank.web.model.repository;

import com.bank.web.model.entity.AccountRest;
import com.bank.web.model.entity.Accounts;

import java.util.List;

public interface AccountRepository {

    List<Accounts> accountsList(Boolean suspended, Boolean closed, Integer accountID, Boolean isOwnerBlocked);

    Accounts getAccountById(Integer accountID);

    Accounts getAccountByNumber(String accountNumber);

    Double getAccountRest (Accounts accountID);

    void newRest (AccountRest accountRest);

    void addAccount (Accounts account);

    void updateAccount (Accounts account);

    void changeStatus (Integer accountID, Boolean status);

    void closeAccount(Integer accountID);
}
