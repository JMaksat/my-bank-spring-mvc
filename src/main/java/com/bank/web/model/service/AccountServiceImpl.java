package com.bank.web.model.service;

import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.CustomerInfo;
import com.bank.web.model.repository.AccountRepository;
import com.bank.web.model.repository.CustomerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    public static final Logger logger = Logger.getLogger(AccountServiceImpl.class);

    AccountRepository accountRepository;
    CustomerRepository customerRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Map<String, Object> getAccount(Integer accountID) {
        Map<String, Object> objMap = new HashMap<>();
        Accounts account = accountRepository.getAccountById(accountID);
        CustomerInfo customer = customerRepository.customerDetails(account.getAccountOwner().getCustomerID());

        objMap.put("account", account);
        objMap.put("customer", customer);

        return objMap;
    }
}
