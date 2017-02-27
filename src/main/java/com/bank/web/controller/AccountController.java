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

    @Autowired
    public AccountController(AccountRepository accountRepository,
                             DirectoryRepository directoryRepository,
                             TransactionRepository transactionRepository,
                             TransactionManageService transactionManageService) {
        this.accountRepository = accountRepository;
        this.directoryRepository = directoryRepository;
        this.transactionRepository = transactionRepository;
        this.transactionManageService = transactionManageService;
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
    public ModelAndView getAccounts(ModelMap map) {
        List<Accounts> accounts = accountRepository.accountsList(null, null, null);

        if (accounts != null) {
            map.put("accounts", accounts);
            map.put("pageName", "All accounts");
            map.put("leftMenu", "accounts");
        }

        return new ModelAndView("accounts");
    }

    @RequestMapping(value = "/account/{accountID}/{invoker}", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
    public ModelAndView getAccount(@PathVariable("accountID") Integer accountID, @PathVariable("invoker") String invoker, ModelMap map) {
        Accounts account = accountRepository.getAccountById(accountID);

        if (account != null) {
            map.put("account", account);
            map.put("invoker", invoker);
            map.put("pageName", "Account");
            map.put("leftMenu", "accounts");
        }

        return new ModelAndView("account");
    }

    @RequestMapping(value = "/account/edit/{customerID}/{accountID}/{invoker}", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
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
    //@Secured({"ROLE_USER"})
    public String newAccount(@RequestParam Map<String, String> params) {
        Accounts account = new Accounts();

        account.setAccountNumber(params.get("account"));
        account.setAccountOwner(Integer.valueOf(params.get("customerID")));
        account.setDateOpened(new java.util.Date());
        //account.getDateClosed();
        account.setDateCreated(new java.util.Date());
        account.setDateModified(new java.util.Date());
    /* Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            username = authentication.getName(); */
        account.setUserID("temp_user");
        account.setAccountType(params.get("type"));
        account.setIsSuspended(0);
        account.setComment(params.get("comment"));

        if (Integer.valueOf(params.get("id")) >= 0) {
            account.setAccountID(Integer.valueOf(params.get("id")));
            accountRepository.updateAccount(account);
        } else {
            account.setAccountID(accountRepository.getNewAccountSeq());
            transactionManageService.createNewAccount(account);
            //accountRepository.addAccount(account);
        }

        return "1";
    }

    @RequestMapping(path = "/account/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    //@Secured({"ROLE_USER"})
    public String changeAccountState(@RequestParam Map<String, String> params) {

        if (params.get("status").equals("1")) {
            accountRepository.changeStatus(Integer.valueOf(params.get("accountID")), false);
        } else {
            accountRepository.changeStatus(Integer.valueOf(params.get("accountID")), true);
        }

        return "1";
    }

    @RequestMapping(path = "/account/close", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    //@Secured({"ROLE_USER"})
    public String closeAccount(@RequestParam Map<String, String> params) {

        accountRepository.closeAccount(Integer.valueOf(params.get("accountID")));

        return "1";
    }

    @RequestMapping(value = "/transactions/{accountID}/{invoker}", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
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
