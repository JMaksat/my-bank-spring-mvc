package com.bank.web.model.repository;

import com.bank.web.model.entity.AccountRest;
import com.bank.web.model.entity.Accounts;

import java.util.List;

public interface AccountRepository {

    List<Accounts> accountsList(Boolean suspended, Boolean closed, Integer accountID, Boolean isOwnerBlocked);

    Accounts getAccountById(Integer accountID);

    Accounts getAccountByNumber(String accountNumber);

    Double getAccountRest (Accounts accountID);

    Boolean newRest (AccountRest accountRest);

    Boolean addAccount (Accounts account);

    Boolean updateAccount (Accounts account);

    Boolean changeStatus (Integer accountID, Boolean status);

    Boolean closeAccount(Integer accountID);
}
