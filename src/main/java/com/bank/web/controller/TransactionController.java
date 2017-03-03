package com.bank.web.controller;

import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.entity.Transactions;
import com.bank.web.model.repository.AccountRepository;
import com.bank.web.model.repository.DirectoryRepository;
import com.bank.web.model.repository.TransactionRepository;
import com.bank.web.service.TransactionManageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class TransactionController {
    public static final Logger logger = Logger.getLogger(AccountController.class);

    private TransactionRepository transactionRepository;
    private DirectoryRepository directoryRepository;
    private AccountRepository accountRepository;
    private TransactionManageService transactionManageService;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository,
                                 DirectoryRepository directoryRepository,
                                 AccountRepository accountRepository,
                                 TransactionManageService transactionManageService) {
        this.transactionRepository = transactionRepository;
        this.directoryRepository = directoryRepository;
        this.accountRepository = accountRepository;
        this.transactionManageService = transactionManageService;
    }

    @RequestMapping(path = "/transactions", method = RequestMethod.GET)
    @Secured({"ROLE_ACCOUNTANT"})
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
    @Secured({"ROLE_ACCOUNTANT"})
    public ModelAndView newTransaction(@PathVariable("accountID") Integer accountID, @PathVariable("invoker") String invoker, ModelMap map) {
        List<Accounts> accounts = accountRepository.accountsList(false, false, accountID);
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

    @RequestMapping(path = "/transactions/save", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_ACCOUNTANT"})
    public String saveTransaction(@RequestParam Map<String, String> params) {

        String result;

        result = transactionManageService.createNewTransaction(Integer.valueOf(params.get("accountDebit")),
                Integer.valueOf(params.get("accountCredit")),
                Double.valueOf(params.get("amount")),
                params.get("type"),
                params.get("reversed").equals("1")?true:false);

        return result;
    }
}
