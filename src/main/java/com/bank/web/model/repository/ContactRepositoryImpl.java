package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerContacts;
import com.bank.web.model.entity.Directory;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Repository("contactRepository")
@Transactional
public class ContactRepositoryImpl implements ContactRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(ContactRepositoryImpl.class);

    public ContactRepositoryImpl(){}

    public ContactRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CustomerContacts getContact(Integer contactID) {
        CustomerContacts result;
        String hql;

        hql = " from CustomerContacts where contactID = :contact_id ";
        List<CustomerContacts> cntList = sessionFactory.getCurrentSession().createQuery(hql)
                .setParameter("contact_id", contactID).list();
        result = cntList.get(0);

        hql = " from Directory where dirID = :contact_type and dirGroup = :dir_group and isActive = 1 ";
        Query qry = sessionFactory.getCurrentSession().createQuery(hql);
        qry.setParameter("contact_type", result.getContactType());
        qry.setParameter("dir_group", Directory.CONTACTS);
        List<Directory> dirList = qry.list();
        Directory dir = dirList.get(0);

        result.setContactTypeLabel(dir.getDirType());
        logger.info("getContact(" + contactID + ") records found = " + cntList.size());

        return result;
    }

    @Override
    public Boolean addContact(CustomerContacts contact) {
        Session session = sessionFactory.getCurrentSession();
        Integer contactID;

        try {
            contactID = (Integer) session.save(contact);
            logger.info("addContact(CustomerContact) successfully added contact_id=" + contactID);
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean updateContact(CustomerContacts contact) {
        Session session = sessionFactory.getCurrentSession();

        try {
            CustomerContacts cc = session.get(CustomerContacts.class, contact.getContactID());
            cc.setValue(contact.getValue());
            cc.setDateModified(contact.getDateModified());
            cc.setUserID(contact.getUserID());
            cc.setContactType(contact.getContactType());
            session.update(cc);

            logger.info("updateContact(CustomerContact) successfully updated contact_id=" + contact.getContactID());
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public Boolean changeStatus(Integer contactID, Boolean status) {
        Session session = sessionFactory.getCurrentSession();
        Integer state = status?0:1;

        try {
            CustomerContacts cc = session.get(CustomerContacts.class, contactID);
            cc.setIsActive(state);
            session.update(cc);

            logger.info("changeStatus(" + contactID + ", " + status + " ) successfully updated contact_id=" + contactID);
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}