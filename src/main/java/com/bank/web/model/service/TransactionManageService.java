package com.bank.web.model.service;

import com.bank.web.model.entity.Accounts;

public interface TransactionManageService {

    Boolean createNewAccount(Accounts account);

    Boolean decreaseRest(Accounts account, Integer transactionID, Double amount);

    Boolean increaseRest(Accounts account, Integer transactionID, Double amount);

    String createNewTransaction(Integer accountDebit, Integer accountCredit, Double amount, String operationType, Boolean isReversed);
}
