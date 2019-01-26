package com.bank.web.model.repository;

import com.bank.web.model.entity.*;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Repository("customerRepository")
@Transactional
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(CustomerRepositoryImpl.class);

    public CustomerRepositoryImpl() {}

    public CustomerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<CustomerInfo> customersList(Boolean isActive) {
        List<CustomerInfo> result;
        StringBuilder hql = new StringBuilder(" from CustomerInfo where ");

        if (isActive == null) {
            hql.append(" 1=1 ");
        } else if (isActive) {
            hql.append(" is_active = 1 ");
        } else {
            hql.append(" is_active = 0 ");
        }

        hql.append(" order by customerID ");
        result = sessionFactory.getCurrentSession().createQuery(hql.toString()).list();

        logger.info("customersList(" + isActive + ") records found = " + result.size());

        return result;
    }

    @Override
    public CustomerInfo customerDetails(Integer customerID) {

        String hql = " from CustomerInfo where customerID = :customer_id ";
        List<CustomerInfo> custList = sessionFactory.getCurrentSession().createQuery(hql)
                .setParameter("customer_id", customerID).list();
        CustomerInfo result = custList.get(0);

        logger.info("customerDetails(" + customerID + ") records found = " + custList.size());

        return result;
    }

    @Override
    public Boolean changeStatus(Integer customerID, Boolean status) {
        Session session = sessionFactory.getCurrentSession();
        Integer state = status?0:1;

        try {
            CustomerInfo ci = session.get(CustomerInfo.class, customerID);
            ci.setIsActive(state);
            session.update(ci);

            logger.info("changeStatus(" + customerID + ", " + status + " ) successfully updated customer_id" + customerID);
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public List<Accounts> getAccounts(CustomerInfo customer) {
        String hql;

        hql = " from Accounts where accountOwner = :accountOwner order by accountID ";

        List<Accounts> result = sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("accountOwner", customer).list();

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

        logger.info("getAccounts(" + customer.getCustomerID() + ") records found = " + result.size());

        return result;
    }

    @Override
    public List<CustomerAddress> getAddresses(CustomerInfo customer) {
        String hql;

        hql = " from CustomerAddress where customer = :customer order by addressID ";

        List<CustomerAddress> result = sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("customer", customer).list();

        for (CustomerAddress address : result) {
            hql = " from Directory where dirID = :address_type and dirGroup = :dir_group and isActive = 1 ";
            List<Directory> dirList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("address_type", address.getAddressType())
                    .setParameter("dir_group", Directory.ADDRESS)
                    .list();
            if (dirList.size() > 0)
                address.setAddressTypeLabel(dirList.get(0).getDirType());
        }

        logger.info("getAddresses(" + customer.getCustomerID() + ") records found = " + result.size());

        return result;
    }

    @Override
    public List<CustomerContacts> getContacts(CustomerInfo customer) {
        String hql;

        hql = " from CustomerContacts where customer = :customer order by contactID ";

        List<CustomerContacts> result = sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("customer", customer).list();

        for (CustomerContacts contact : result) {
            hql = " from Directory where dirID = :contact_type and dirGroup = :dir_group and isActive = 1 ";
            List<Directory> dirList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("contact_type", contact.getContactType())
                    .setParameter("dir_group", Directory.CONTACTS)
                    .list();
            if (dirList.size() > 0)
                contact.setContactTypeLabel(dirList.get(0).getDirType());
        }

        logger.info("getContacts(" + customer.getCustomerID() + ") records found = " + result.size());

        return result;
    }

    @Override
    public List<CustomerPapers> getPapers(CustomerInfo customer) {
        String hql;

        hql = " from CustomerPapers where customer = :customer order by paperID ";

        List<CustomerPapers> result = sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("customer", customer).list();

        for (CustomerPapers paper : result) {
            hql = " from Directory where dirID = :paper_type and dirGroup = :dir_group and isActive = 1 ";
            List<Directory> dirList = sessionFactory.getCurrentSession()
                    .createQuery(hql)
                    .setParameter("paper_type", paper.getPaperType())
                    .setParameter("dir_group", Directory.PAPERS)
                    .list();
            if (dirList.size() > 0)
                paper.setPaperTypeLabel(dirList.get(0).getDirType());
        }

        logger.info("getPapers(" + customer.getCustomerID() + ") records found = " + result.size());

        return result;
    }

    @Override
    public Boolean addCustomer(CustomerInfo customerInfo) {
        Session session = sessionFactory.getCurrentSession();
        Integer customerID;

        try {
            customerID = (Integer) session.save(customerInfo);
            logger.info("addCustomer(CustomerInfo) successfully added customer_id=" + customerID);
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public Boolean updateCustomer(CustomerInfo customerInfo) {
        Session session = sessionFactory.getCurrentSession();

        try {
            CustomerInfo ci = session.get(CustomerInfo.class, customerInfo.getCustomerID());
            ci.setFirstName(customerInfo.getFirstName());
            ci.setLastName(customerInfo.getLastName());
            ci.setMiddleName(customerInfo.getMiddleName());
            ci.setBirthDate(customerInfo.getBirthDate());
            ci.setDateModified(customerInfo.getDateModified());
            ci.setUserID(customerInfo.getUserID());
            session.update(ci);

            logger.info("updateCustomer(CustomerInfo) successfully updated customer_id=" + customerInfo.getCustomerID());
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
