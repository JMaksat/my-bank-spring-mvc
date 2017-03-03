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

import java.util.Date;
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
    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public Boolean createNewAccount(Accounts account) {
        List<Directory> directory = directoryRepository.getTransactionTypes();
        Transactions transaction = new Transactions();
        AccountRest rest = new AccountRest();

        try {
            accountRepository.addAccount(account);

            transaction.setTransactionID(transactionRepository.getNewTransactionSeq());
            for (Directory dir : directory)
                if (dir.getDirType().equals("VOID"))
                    transaction.setOperationType(dir.getDirID().toString());
            transaction.setIsReversed(0);
            transaction.setTransactionSum(0.0);
            transaction.setTransactionDate(new Date());
            transaction.setUserID(authentication.getName());
            transaction.setAccountDebit(accountRepository.getAccountByNumber(
                    parametersRepository.getParamTransAccount().getValue()).getAccountID().toString());
            transaction.setAccountCredit(account.getAccountID().toString());
            transactionRepository.addTransaction(transaction);

            rest.setRestID(accountRepository.getNewRestSeq());
            rest.setAccountID(account.getAccountID());
            rest.setRestSum(0.0);
            rest.setTransactionID(transaction.getTransactionID());
            rest.setRestDate(new Date());
            accountRepository.newRest(rest);
        } catch (Exception e) {
            logger.error("Exception: ", e);
            return false;
        }

        return true;
    }

    public Boolean decreaseRest(Integer accountID, Integer transactionID, Double amount) {
        AccountRest rest = new AccountRest();

        try {
            Double sum = accountRepository.getAccountRest(accountID) - amount;

            rest.setRestID(accountRepository.getNewRestSeq());
            rest.setAccountID(accountID);
            rest.setRestSum(sum);
            rest.setTransactionID(transactionID);
            rest.setRestDate(new Date());
            accountRepository.newRest(rest);
        } catch (Exception e) {
            logger.error("Exception: ", e);
            return false;
        }

        return true;
    }

    public Boolean increaseRest(Integer accountID, Integer transactionID, Double amount) {
        AccountRest rest = new AccountRest();

        try {
            Double sum = accountRepository.getAccountRest(accountID) + amount;

            rest.setRestID(accountRepository.getNewRestSeq());
            rest.setAccountID(accountID);
            rest.setRestSum(sum);
            rest.setTransactionID(transactionID);
            rest.setRestDate(new Date());
            accountRepository.newRest(rest);
        } catch (Exception e) {
            logger.error("Exception: ", e);
            return false;
        }

        return true;
    }

    public String createNewTransaction(Integer accountDebit, Integer accountCredit, Double amount, String operationType, Boolean isReversed) {
        List<Directory> directory = directoryRepository.getTransactionTypes();
        Transactions transaction = new Transactions();

        try {

            transaction.setTransactionID(transactionRepository.getNewTransactionSeq());
            for (Directory dir : directory)
                if (dir.getDirID().toString().equals(operationType))
                    transaction.setOperationType(dir.getDirID().toString());
            transaction.setIsReversed(isReversed ? 1 : 0);
            transaction.setTransactionSum(amount);
            transaction.setTransactionDate(new Date());
            transaction.setUserID(authentication.getName());
            transaction.setAccountDebit(accountDebit.toString());
            transaction.setAccountCredit(accountCredit.toString());

            if (!isReversed) {
                if (amount <= accountRepository.getAccountRest(accountDebit)) {

                    transactionRepository.addTransaction(transaction);

                    if (decreaseRest(accountDebit, transaction.getTransactionID(), amount)) {
                        increaseRest(accountCredit, transaction.getTransactionID(), amount);
                    } else {
                        return "4";
                    }

                } else {
                    return "3";
                }
            }

            if (isReversed) {
                if (amount <= accountRepository.getAccountRest(accountCredit)) {

                    transactionRepository.addTransaction(transaction);

                    if (decreaseRest(accountCredit, transaction.getTransactionID(), amount)) {
                        increaseRest(accountDebit, transaction.getTransactionID(), amount);
                    } else {
                        return "4";
                    }

                } else {
                    return "2";
                }
            }

        } catch (Exception e) {
            logger.error("Exception: ", e);
            return e.getMessage();
        }

        return "1";
    }
}
