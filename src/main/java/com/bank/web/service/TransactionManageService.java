package com.bank.web.service;

import com.bank.web.model.entity.AccountRest;
import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.Transactions;

public interface TransactionManageService {

    Boolean createNewAccount(Accounts account);

    Boolean decreaseRest(Integer accountID, Integer transactionID, Double amount);

    Boolean increaseRest(Integer accountID, Integer transactionID, Double amount);

    String createNewTransaction(Integer accountDebit, Integer accountCredit, Double amount, String operationType, Boolean isReversed);
}
