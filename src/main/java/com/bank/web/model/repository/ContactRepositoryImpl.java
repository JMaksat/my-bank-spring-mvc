package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerContacts;
import com.bank.web.model.entity.Directory;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public void addContact(CustomerContacts contact) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        Integer contactID = null;

        try {
            transaction.begin();

            contactID = (Integer) session.save(contact);

            transaction.commit();
            logger.info("addContact(CustomerContact) successfully added contact_id=" + contactID);
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void updateContact(CustomerContacts contact) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction.begin();

            CustomerContacts cc = session.get(CustomerContacts.class, contact.getContactID());
            cc.setValue(contact.getValue());
            cc.setDateModified(contact.getDateModified());
            cc.setUserID(contact.getUserID());
            cc.setContactType(contact.getContactType());
            session.update(cc);

            transaction.commit();
            logger.info("updateContact(CustomerContact) successfully updated contact_id=" + contact.getContactID());
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void changeStatus(Integer contactID, Boolean status) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        Integer state = status?0:1;

        try {
            transaction.begin();

            CustomerContacts cc = session.get(CustomerContacts.class, contactID);
            cc.setIsActive(state);
            session.update(cc);

            transaction.commit();
            logger.info("changeStatus(" + contactID + ", " + status + " ) successfully updated contact_id=" + contactID);
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error(e.getMessage(), e);
        }
    }
}