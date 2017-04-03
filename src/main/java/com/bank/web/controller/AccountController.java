package com.bank.web.controller;

import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.CustomerInfo;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.entity.Transactions;
import com.bank.web.model.repository.AccountRepository;
import com.bank.web.model.repository.CustomerRepository;
import com.bank.web.model.repository.DirectoryRepository;
import com.bank.web.model.repository.TransactionRepository;
import com.bank.web.service.TransactionManageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class AccountController {
    public static final Logger logger = Logger.getLogger(AccountController.class);

    private AccountRepository accountRepository;
    private DirectoryRepository directoryRepository;
    private TransactionRepository transactionRepository;
    private TransactionManageService transactionManageService;
    private CustomerRepository customerRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository,
                             DirectoryRepository directoryRepository,
                             TransactionRepository transactionRepository,
                             TransactionManageService transactionManageService,
                             CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.directoryRepository = directoryRepository;
        this.transactionRepository = transactionRepository;
        this.transactionManageService = transactionManageService;
        this.customerRepository = customerRepository;
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    @Secured({"ROLE_ACCOUNTANT"})
    public ModelAndView getAccounts(ModelMap map) {
        List<Accounts> accounts = accountRepository.accountsList(null, null, null, null);

        if (accounts != null) {
            map.put("accounts", accounts);
            map.put("pageName", "All accounts");
            map.put("leftMenu", "accounts");
        }

        return new ModelAndView("accounts");
    }

    @RequestMapping(value = "/account/{accountID}/{invoker}", method = RequestMethod.GET)
    @Secured({"ROLE_ACCOUNTANT"})
    public ModelAndView getAccount(@PathVariable("accountID") Integer accountID, @PathVariable("invoker") String invoker, ModelMap map) {
        Accounts account = accountRepository.getAccountById(accountID);

        if (account != null) {
            CustomerInfo customer = customerRepository.customerDetails(account.getAccountOwner());

            map.put("account", account);
            map.put("customer", customer);
            map.put("invoker", invoker);
            map.put("pageName", "Account");
            map.put("leftMenu", "accounts");
        }

        return new ModelAndView("account");
    }

    @RequestMapping(value = "/account/edit/{customerID}/{accountID}/{invoker}", method = RequestMethod.GET)
    @Secured({"ROLE_ACCOUNTANT"})
    public ModelAndView updateAccount(@PathVariable("customerID") Integer customerID, @PathVariable("accountID") Integer accountID, @PathVariable("invoker") String invoker, ModelMap map) {
        List<Directory> types = directoryRepository.getAccountTypes();

        if (accountID >= 0) {
            Accounts account = accountRepository.getAccountById(accountID);

            map.put("account", account);
            map.put("pageName", "Update account entry");
        } else {
            map.put("pageName", "New account");
        }

        map.put("types", types);
        map.put("invoker", invoker);
        map.put("leftMenu", "accounts");
        map.put("accountID", accountID);
        map.put("customerID", customerID);

        return new ModelAndView("accountEntry");
    }

    @RequestMapping(path = "/account/save", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_ACCOUNTANT"})
    public String newAccount(@RequestParam Map<String, String> params) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Accounts account = new Accounts();
        String retVal = "0";

        if (params.get("account") != null &&
                params.get("customerID") != null &&
                params.get("type") != null &&
                params.get("id") != null) {

            account.setAccountNumber(params.get("account"));
            account.setAccountOwner(Integer.valueOf(params.get("customerID")));
            account.setDateOpened(new java.util.Date());
            account.setDateCreated(new java.util.Date());
            account.setDateModified(new java.util.Date());
            account.setUserID(authentication.getName());
            account.setAccountType(params.get("type"));
            account.setIsSuspended(0);
            account.setComment(params.get("comment"));

            if (Integer.valueOf(params.get("id")) >= 0) {
                account.setAccountID(Integer.valueOf(params.get("id")));
                accountRepository.updateAccount(account);
            } else {
                account.setAccountID(accountRepository.getNewAccountSeq());
                transactionManageService.createNewAccount(account);
            }

            retVal = "1";
        }

        return retVal;
    }

    @RequestMapping(path = "/account/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_ACCOUNTANT"})
    public String changeAccountState(@RequestParam Map<String, String> params) {
        String retVal = "0";

        if (params.get("accountID") != null) {
            accountRepository.changeStatus(Integer.valueOf(params.get("accountID")),
                    params.get("status").equals("1"));
            retVal = "1";
        }

        return retVal;
    }

    @RequestMapping(path = "/account/close", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_ACCOUNTANT"})
    public String closeAccount(@RequestParam Map<String, String> params) {
        String retVal = "0";

        if (params.get("accountID") != null) {
            accountRepository.closeAccount(Integer.valueOf(params.get("accountID")));
            retVal = "1";
        }

        return retVal;
    }

    @RequestMapping(value = "/transactions/{accountID}/{invoker}", method = RequestMethod.GET)
    @Secured({"ROLE_ACCOUNTANT"})
    public ModelAndView getTransactions(@PathVariable("accountID") Integer accountID, @PathVariable("invoker") String invoker, ModelMap map) {
        List<Transactions> transactions = transactionRepository.getTransactions(accountID);
        Accounts account = accountRepository.getAccountById(accountID);

        if (transactions != null) {
            map.put("accountID", accountID);
            map.put("invoker", invoker);
            map.put("transactions", transactions);
            map.put("pageName", "Transactions of account: " + account.getAccountNumber());
            map.put("leftMenu", "transactions");
        }

        return new ModelAndView("transactions");
    }

}
