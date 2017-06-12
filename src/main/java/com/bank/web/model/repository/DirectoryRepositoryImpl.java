package com.bank.web.model.repository;

import com.bank.web.model.entity.Directory;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.criteria.internal.predicate.BooleanAssertionPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("directoryRepository")
@Transactional
public class DirectoryRepositoryImpl implements DirectoryRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(DirectoryRepositoryImpl.class);

    public DirectoryRepositoryImpl() {}

    public DirectoryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Directory> directoryList() {
        List<Directory> result;

        result = sessionFactory.getCurrentSession().createQuery(" from Directory order by dirID ").list();

        logger.info("directoryList() records found = " + result.size());

        return result;
    }

    @Override
    @Cacheable("account")
    public List<Directory> getAccountTypes() {
        List<Directory> result;

        result = sessionFactory.getCurrentSession()
                .createQuery(" from Directory where dirGroup = :dir_group and isActive = 1 ")
                .setParameter("dir_group", Directory.ACCOUNTS).list();

        logger.info("getAccountTypes() records found = " + result.size());

        return result;
    }

    @Override
    @Cacheable("operation")
    public List<Directory> getTransactionTypes() {
        List<Directory> result;

        result = sessionFactory.getCurrentSession()
                .createQuery(" from Directory where dirGroup = :dir_group and isActive = 1 ")
                .setParameter("dir_group", Directory.OPERATIONS).list();

        logger.info("getTransactionTypes() records found = " + result.size());

        return result;
    }

    @Override
    @Cacheable("address")
    public List<Directory> getAddressTypes() {
        List<Directory> result;

        result = sessionFactory.getCurrentSession()
                .createQuery(" from Directory where dirGroup = :dir_group and isActive = 1 ")
                .setParameter("dir_group", Directory.ADDRESS).list();

        logger.info("getAddressTypes() records found = " + result.size());

        return result;
    }

    @Override
    @Cacheable("contact")
    public List<Directory> getContactTypes() {
        List<Directory> result;

        result = sessionFactory.getCurrentSession()
                .createQuery(" from Directory where dirGroup = :dir_group and isActive = 1 ")
                .setParameter("dir_group", Directory.CONTACTS).list();

        logger.info("getContactTypes() records found = " + result.size());

        return result;
    }

    @Override
    @Cacheable("paper")
    public List<Directory> getPaperTypes() {
        List<Directory> result;

        result = sessionFactory.getCurrentSession()
                .createQuery(" from Directory where dirGroup = :dir_group and isActive = 1 ")
                .setParameter("dir_group", Directory.PAPERS).list();

        logger.info("getPaperTypes() records found = " + result.size());

        return result;
    }

    @Override
    public Boolean checkUnique(Integer dirID) {
        Boolean result;

        List<Directory> dirList = sessionFactory.getCurrentSession()
                .createQuery(" from Directory where dirID = :dir_id ")
                .setParameter("dir_id", dirID).list();

        result = dirList.size() > 0 ? false : true;
        logger.info("checkUnique(" + dirID + ") result = " + result);

        return result;
    }

    @Override
    @CacheEvict(value = {"account", "address", "operation", "contact", "paper"}, allEntries = true)
    public Boolean addEntry(Directory directory) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        Boolean result = true;
        Integer dirID = null;

        try {
            transaction.begin();

            dirID = (Integer) session.save(directory);

            transaction.commit();
            logger.info("addEntry(Directory) successfully added dir_id=" + dirID);
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error(e.getMessage(), e);
            result = false;
        }

        return result;
    }

    @Override
    @CacheEvict(value = {"account", "address", "operation", "contact", "paper"}, allEntries = true)
    public Boolean updateEntry(Directory directory) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        Boolean result = true;

        try {
            transaction.begin();

            Directory dir = session.get(Directory.class, directory.getDirID());
            dir.setDirGroup(directory.getDirGroup());
            dir.setDirType(directory.getDirType());
            dir.setDescription(directory.getDescription());
            dir.setDateModified(directory.getDateModified());
            dir.setUserID(directory.getUserID());
            session.update(dir);

            transaction.commit();
            logger.info("updateEntry(Directory) successfully updated dir_id=" + directory.getDirID());
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error(e.getMessage(), e);
            result = false;
        }

        return result;
    }

    @Override
    @CacheEvict(value = {"account", "address", "operation", "contact", "paper"}, allEntries = true)
    public Boolean changeStatus(Integer dirID, Boolean status) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        Boolean result = true;
        Integer state = status?0:1;

        try {
            transaction.begin();

            Directory dir = session.get(Directory.class, dirID);
            dir.setIsActive(state);
            session.update(dir);

            transaction.commit();
            logger.info("changeStatus(" + dirID + ", " + status + ") successfully updated dir_id=" + dirID);
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            logger.error(e.getMessage(), e);
            result = false;
        }

        return result;
    }
}
