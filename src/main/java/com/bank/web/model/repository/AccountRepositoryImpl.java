package com.bank.web.model.repository;

import com.bank.web.model.entity.AccountRest;
import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.Directory;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.*;
import org.hibernate.query.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.util.List;

@Repository("accountRepository")
@Transactional
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(AccountRepositoryImpl.class);

    public AccountRepositoryImpl() {}

    public AccountRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Accounts> accountsList(Boolean suspended, Boolean closed, Integer accountID, Boolean isOwnerBlocked) {
        String hql;

        hql = " from Accounts where 1=1 ";

        if (suspended != null) {
            if (suspended) {
                hql += " and isSuspended = 1 ";
            } else {
                hql += " and isSuspended = 0 ";
            }
        }

        if (closed != null) {
            if (closed) {
                hql += " and dateClosed is not null ";
            } else {
                hql += " and dateClosed is null ";
            }
        }

        if (accountID != null) {
            hql += " and accountID not in (:account_id) ";
        }

        if (isOwnerBlocked != null && isOwnerBlocked) {
            hql += " and accountOwner not in (select customerID from CustomerInfo where isActive = 0) ";
        }

        hql += " order by accountID ";

        Query<Accounts> qry = sessionFactory.getCurrentSession().createQuery(hql);

        if (accountID != null)
            qry.setParameter("account_id", accountID);

        List<Accounts> result = qry.list();

        for (Accounts account : result) {
            hql = " from AccountRest ar where ar.account = :account "
                    + " and ar.restID = (select max(z.restID) from AccountRest z where z.account = ar.account) ";
            List<AccountRest> restList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("account", account)
                    .list();
            if (restList.size() > 0)
                account.setRestSum(restList.get(0).getRestSum());

            hql = " from Directory where dirID = :account_type and dirGroup = :dir_group and isActive = 1 ";
            List<Directory> dirList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("account_type", account.getAccountType())
                    .setParameter("dir_group", Directory.ACCOUNTS)
                    .list();
            if (dirList.size() > 0)
                account.setAccountTypeLabel(dirList.get(0).getDirType());
        }

        logger.info("accountsList(" + suspended + ", " + closed + ", " + accountID + ", "
                + isOwnerBlocked + ") records found = " + result.size());

        return result;
    }

    @Override
    public Accounts getAccountById(Integer accountID) {
        String hql;

        hql = " from Accounts where accountID = :account_id ";

        List<Accounts> accList = sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("account_id", accountID).list();

        for (Accounts account : accList) {
            hql = " from AccountRest ar where ar.account = :account "
                    + " and ar.restID = (select max(z.restID) from AccountRest z where z.account = ar.account) ";
            List<AccountRest> restList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("account", account)
                    .list();
            if (restList.size() > 0)
                account.setRestSum(restList.get(0).getRestSum());

            hql = " from Directory where dirID = :account_type and dirGroup = :dir_group and isActive = 1 ";
            List<Directory> dirList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("account_type", account.getAccountType())
                    .setParameter("dir_group", Directory.ACCOUNTS)
                    .list();
            if (dirList.size() > 0)
                account.setAccountTypeLabel(dirList.get(0).getDirType());
        }

        Accounts result = accList.get(0);

        logger.info("getAccountById(" + accountID + ") records found = " + accList.size());

        return result;
    }

    @Override
    public Accounts getAccountByNumber(String accountNumber) {
        String hql;

        hql = " from Accounts where accountNumber = :account_number ";

        List<Accounts> accList = sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("account_number", accountNumber).list();

        for (Accounts account : accList) {
            hql = " from AccountRest ar where ar.account = :account "
                    + " and ar.restID = (select max(z.restID) from AccountRest z where z.account = ar.account) ";
            List<AccountRest> restList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("account", account)
                    .list();
            if (restList.size() > 0)
                account.setRestSum(restList.get(0).getRestSum());

            hql = " from Directory where dirID = :account_type and dirGroup = :dir_group and isActive = 1 ";
            List<Directory> dirList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("account_type", account.getAccountType())
                    .setParameter("dir_group", Directory.ACCOUNTS)
                    .list();
            if (dirList.size() > 0)
                account.setAccountTypeLabel(dirList.get(0).getDirType());
        }

        Accounts result = accList.get(0);

        logger.info("getAccountByNumber(" + accountNumber + ") records found = " + accList.size());

        return result;
    }

    @Override
    public Double getAccountRest(Accounts account) {
        String hql = " from AccountRest ar where ar.account = :account "
                + " and ar.restID = (select max(z.restID) from AccountRest z where z.account = ar.account) ";

        List<AccountRest> restlist = sessionFactory.getCurrentSession().createQuery(hql).setParameter("account", account).list();

        Double result = restlist.get(0).getRestSum().doubleValue();

        logger.info("getAccountRest(Accounts) records found = " + restlist.size());

        return result;
    }

    @Override
    public Boolean newRest(AccountRest accountRest) {
        Session session = sessionFactory.getCurrentSession();
        Integer restID;

        try {
            restID = (Integer) session.save(accountRest);
            logger.info("newRest(AccountRest) successfully added rest_id=" + restID);
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public Boolean addAccount(Accounts account) {
        Session session = sessionFactory.getCurrentSession();
        Integer accountID;

        try {
            accountID = (Integer) session.save(account);
            logger.info("addAccount(Accounts) successfully added account_id=" + accountID);
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public Boolean updateAccount(Accounts account) {
        Session session = sessionFactory.getCurrentSession();

        try {
            Accounts acc = session.get(Accounts.class, account.getAccountID());
            acc.setDateModified(account.getDateModified());
            acc.setUserID(account.getUserID());
            acc.setAccountType(account.getAccountType());
            acc.setComment(account.getComment());
            session.update(acc);

            logger.info("updateAccount(Accounts) successfully updated account_id=" + account.getAccountID());
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public Boolean changeStatus(Integer accountID, Boolean status) {
        Session session = sessionFactory.getCurrentSession();
        Integer state = status?0:1;

        try {
            Accounts acc = session.get(Accounts.class, accountID);
            acc.setIsSuspended(state);
            session.update(acc);

            logger.info("changeStatus(" + accountID + ", " + status + ") successfully updated account_id=" + accountID);
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public Boolean closeAccount(Integer accountID) {
        Session session = sessionFactory.getCurrentSession();

        try {
            Accounts acc = session.get(Accounts.class, accountID);
            acc.setDateClosed(LocalDate.now());

            logger.info("closeAccount(" + accountID + ") successfully updated account_id=" + accountID);
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
