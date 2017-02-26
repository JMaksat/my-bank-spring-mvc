package com.bank.web.controller;

import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.entity.Transactions;
import com.bank.web.model.repository.AccountRepository;
import com.bank.web.model.repository.DirectoryRepository;
import com.bank.web.model.repository.TransactionRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class TransactionController {
    public static final Logger logger = Logger.getLogger(AccountController.class);

    private TransactionRepository transactionRepository;
    private DirectoryRepository directoryRepository;
    private AccountRepository accountRepository;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository,
                                 DirectoryRepository directoryRepository,
                                 AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.directoryRepository = directoryRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(path = "/transactions", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
    public ModelAndView getTransactions(ModelMap map) {
        List<Transactions> transactions = transactionRepository.transactionsList();

        if (transactions != null) {
            map.put("transactions", transactions);
            map.put("pageName", "All transactions");
            map.put("leftMenu", "transactions");
        }

        return new ModelAndView("transactions");
    }

    @RequestMapping(value = "/transactions/new/{accountID}/{invoker}", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
    public ModelAndView newTransaction(@PathVariable("accountID") Integer accountID, @PathVariable("invoker") String invoker, ModelMap map) {
        List<Accounts> accounts = accountRepository.accountsList(false, false);
        List<Directory> operations = directoryRepository.getTransactionTypes();
        Accounts account = accountRepository.getAccountById(accountID);

        if (accounts != null) {
            map.put("accountID", accountID);
            map.put("invoker", invoker);
            map.put("account", account);
            map.put("accounts", accounts);
            map.put("operations", operations);
            map.put("pageName", "New transaction");
            map.put("leftMenu", "transactions");
        }

        return new ModelAndView("transaction");
    }

}
