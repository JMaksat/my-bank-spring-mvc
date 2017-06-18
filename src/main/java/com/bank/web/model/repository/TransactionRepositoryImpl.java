package com.bank.web.model.repository;

import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.entity.Transactions;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Repository("transactionRepositoryImpl")
@Transactional
public class TransactionRepositoryImpl implements TransactionRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(TransactionRepositoryImpl.class);

    public TransactionRepositoryImpl() {}

    public TransactionRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Transactions> getAlltransactions() {
        String hql;

        hql = " from Transactions order by transactionID ";

        List<Transactions> result = sessionFactory.getCurrentSession()
                .createQuery(hql).list();

        for (Transactions transaction : result) {
            hql = " from Directory where dirID = :transaction_type and dirGroup = :dir_group and isActive = 1 ";
            List<Directory> dirList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("transaction_type", transaction.getOperationType())
                    .setParameter("dir_group", Directory.OPERATIONS)
                    .list();
            if (dirList.size() > 0)
                transaction.setOperationTypeLabel(dirList.get(0).getDirType());

            hql = " from Accounts where accountID = :account_id ";

            List<Accounts> debitList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("account_id", transaction.getAccountDebit().getAccountID())
                    .list();
            if (debitList.size() > 0)
                transaction.setAccountDebitLabel(debitList.get(0).getAccountNumber());

            List<Accounts> creditList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("account_id", transaction.getAccountCredit().getAccountID())
                    .list();
            if (creditList.size() > 0)
                transaction.setAccountCreditLabel(creditList.get(0).getAccountNumber());
        }

        logger.info("transactionsList() records found = " + result.size());

        return result;
    }

    @Override
    public List<Transactions> getTransactions(Accounts account) {
        String hql;

        hql = " from Transactions where :account in (accountDebit, accountCredit) order by transactionID ";

        List<Transactions> result = sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("account", account)
                .list();

        for (Transactions transaction : result) {
            hql = " from Directory where dirID = :transaction_type and dirGroup = :dir_group and isActive = 1 ";
            List<Directory> dirList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("transaction_type", transaction.getOperationType())
                    .setParameter("dir_group", Directory.OPERATIONS)
                    .list();
            if (dirList.size() > 0)
                transaction.setOperationTypeLabel(dirList.get(0).getDirType());

            hql = " from Accounts where accountID = :account_id ";

            List<Accounts> debitList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("account_id", transaction.getAccountDebit().getAccountID())
                    .list();
            if (debitList.size() > 0)
                transaction.setAccountDebitLabel(debitList.get(0).getAccountNumber());

            List<Accounts> creditList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("account_id", transaction.getAccountCredit().getAccountID())
                    .list();
            if (creditList.size() > 0)
                transaction.setAccountCreditLabel(creditList.get(0).getAccountNumber());
        }

        logger.info("getTransactions(" + account.getAccountID() + ") records found = " + result.size());

        return result;
    }

    @Override
    public Transactions getTransaction(Integer transactionID) {
        Transactions result;

        List<Transactions> trnList = sessionFactory.getCurrentSession()
                .createQuery(" from Transactions where transactionID = :transaction_id ")
                .setParameter("transaction_id", transactionID).list();

        result = trnList.get(0);

        logger.info("getTransaction(" + transactionID + ") records found = " + trnList.size());

        return result;
    }

    @Override
    public Boolean addTransaction(Transactions transaction) {
        Session session = sessionFactory.getCurrentSession();
        Integer transactionID;

        try {
            transactionID = (Integer) session.save(transaction);
            logger.info("addTransaction(Transactions) successfully added transaction_id=" + transactionID);
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
