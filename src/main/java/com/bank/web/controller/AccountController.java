package com.bank.web.controller;

import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.repository.AccountRepository;
import com.bank.web.model.repository.DirectoryRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
public class AccountController {
    public static final Logger logger = Logger.getLogger(AccountController.class);

    private AccountRepository accountRepository;
    private DirectoryRepository directoryRepository;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public AccountController(AccountRepository accountRepository, DirectoryRepository directoryRepository) {
        this.accountRepository = accountRepository;
        this.directoryRepository = directoryRepository;
    }

    @RequestMapping(value = "/account/{accountID}/{invoker}", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
    public ModelAndView getAccountData(@PathVariable("accountID") Integer accountID, @PathVariable("invoker") String invoker, ModelMap map) {
        List<Accounts> accounts = accountRepository.getAccount(accountID);

        Accounts account = (accounts != null && accounts.size() >0) ? accounts.iterator().next() : null;

        if (account != null) {
            map.put("account", account);
            map.put("invoker", invoker);
            map.put("pageName", "Account");
            map.put("leftMenu", "accounts");
        }

        return new ModelAndView("account");
    }

    @RequestMapping(value = "/account/edit/{customerID}/{accountID}", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
    public ModelAndView updateAccount(@PathVariable("customerID") Integer customerID, @PathVariable("accountID") Integer accountID, ModelMap map) {
        List<Directory> types = directoryRepository.getAccountTypes();

        if (accountID >= 0) {
            List<Accounts> accounts = accountRepository.getAccount(accountID);
            Accounts account = (accounts != null && accounts.size() > 0) ? accounts.iterator().next() : null;

            map.put("account", account);
            map.put("pageName", "Update account entry");
        } else {
            map.put("pageName", "New account");
        }

        map.put("types", types);
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

        try {
            account.setAccountID(Integer.valueOf(params.get("id")));
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
                accountRepository.updateAccount(account);
            } else {
                accountRepository.addAccount(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "1";
    }

    @RequestMapping(path = "/account/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    //@Secured({"ROLE_USER"})
    public String changeAccountState(@RequestParam Map<String, String> params) {
        try {

            if (params.get("status").equals("1")) {
                accountRepository.changeStatus(Integer.valueOf(params.get("accountID")), false);
            } else {
                accountRepository.changeStatus(Integer.valueOf(params.get("accountID")), true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "1";
    }

    @RequestMapping(path = "/account/close", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    //@Secured({"ROLE_USER"})
    public String closeAccount(@RequestParam Map<String, String> params) {

        try {
            accountRepository.closeAccount(Integer.valueOf(params.get("accountID")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "1";
    }
}
