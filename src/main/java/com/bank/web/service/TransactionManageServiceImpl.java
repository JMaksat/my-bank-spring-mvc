package com.bank.web.service;

import com.bank.web.model.entity.AccountRest;
import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.entity.Transactions;
import com.bank.web.model.repository.AccountRepository;
import com.bank.web.model.repository.DirectoryRepository;
import com.bank.web.model.repository.ParametersRepository;
import com.bank.web.model.repository.TransactionRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service("transactionManageService")
public class TransactionManageServiceImpl implements TransactionManageService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private DirectoryRepository directoryRepository;

    @Autowired
    private ParametersRepository parametersRepository;

    private static final Logger logger = Logger.getLogger(TransactionManageServiceImpl.class);

    public Boolean createNewAccount(Accounts account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Directory> directory = directoryRepository.getTransactionTypes();
        Transactions transaction = new Transactions();
        AccountRest rest = new AccountRest();

        try {
            accountRepository.addAccount(account);

            for (Directory dir : directory)
                if (dir.getDirType().equals("VOID"))
                    transaction.setOperationType(dir.getDirID());
            transaction.setIsReversed(0);
            transaction.setTransactionSum(0.0);
            transaction.setTransactionDate(LocalDate.now());
            transaction.setTransactionTime(LocalTime.now());
            transaction.setUserID(authentication.getName());
            transaction.setAccountDebit(accountRepository.getAccountByNumber(
                    parametersRepository.getParamTransAccount().getValue()));
            transaction.setAccountCredit(account);
            transactionRepository.addTransaction(transaction);

            rest.setAccount(account);
            rest.setRestSum(0.0);
            rest.setTransactionID(transaction.getTransactionID());
            rest.setRestDate(LocalDate.now());
            rest.setRestTime(LocalTime.now());
            accountRepository.newRest(rest);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public Boolean decreaseRest(Accounts account, Integer transactionID, Double amount) {
        AccountRest rest = new AccountRest();

        try {
            Double sum = accountRepository.getAccountRest(account) - amount;

            rest.setAccount(account);
            rest.setRestSum(sum);
            rest.setTransactionID(transactionID);
            rest.setRestDate(LocalDate.now());
            accountRepository.newRest(rest);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public Boolean increaseRest(Accounts account, Integer transactionID, Double amount) {
        AccountRest rest = new AccountRest();

        try {
            Double sum = accountRepository.getAccountRest(account) + amount;

            rest.setAccount(account);
            rest.setRestSum(sum);
            rest.setTransactionID(transactionID);
            rest.setRestDate(LocalDate.now());
            accountRepository.newRest(rest);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public String createNewTransaction(Integer accountDebit, Integer accountCredit, Double amount, String operationType, Boolean isReversed) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Directory> directory = directoryRepository.getTransactionTypes();
        Accounts debit = accountRepository.getAccountById(accountDebit);
        Accounts credit = accountRepository.getAccountById(accountCredit);
        Transactions transaction = new Transactions();

        try {
            for (Directory dir : directory)
                if (dir.getDirID().toString().equals(operationType))
                    transaction.setOperationType(dir.getDirID());
            transaction.setIsReversed(isReversed ? 1 : 0);
            transaction.setTransactionSum(amount);
            transaction.setTransactionDate(LocalDate.now());
            transaction.setTransactionTime(LocalTime.now());
            transaction.setUserID(authentication.getName());
            transaction.setAccountDebit(debit);
            transaction.setAccountCredit(credit);

            if (!isReversed) {
                if (amount <= accountRepository.getAccountRest(debit)) {

                    transactionRepository.addTransaction(transaction);

                    if (decreaseRest(debit, transaction.getTransactionID(), amount)) {
                        increaseRest(credit, transaction.getTransactionID(), amount);
                    } else {
                        return "4";
                    }

                } else {
                    return "3";
                }
            }

            if (isReversed) {
                if (amount <= accountRepository.getAccountRest(credit)) {

                    transactionRepository.addTransaction(transaction);

                    if (decreaseRest(credit, transaction.getTransactionID(), amount)) {
                        increaseRest(debit, transaction.getTransactionID(), amount);
                    } else {
                        return "4";
                    }

                } else {
                    return "2";
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return e.getMessage();
        }

        return "1";
    }
}
