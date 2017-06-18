package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerAddress;
import com.bank.web.model.entity.Directory;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Repository("addressRepository")
@Transactional
public class AddressRepositoryImpl implements AddressRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(AddressRepositoryImpl.class);

    public AddressRepositoryImpl(){}

    public AddressRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CustomerAddress getAddress(Integer addressID) {
        CustomerAddress result;
        String hql;

        hql = " from CustomerAddress where addressID = :address_id ";
        List<CustomerAddress> adrList = sessionFactory.getCurrentSession().createQuery(hql)
                .setParameter("address_id", addressID).list();
        result = adrList.get(0);

        hql = " from Directory where dirID = :address_type and dirGroup = :dir_group and isActive = 1 ";
        Query qry = sessionFactory.getCurrentSession().createQuery(hql);
        qry.setParameter("address_type", result.getAddressType());
        qry.setParameter("dir_group", Directory.ADDRESS);
        List<Directory> dirList = qry.list();
        Directory dir = dirList.get(0);

        result.setAddressTypeLabel(dir.getDirType());
        logger.info("getAddress(" + addressID + ") records found = " + adrList.size());

        return result;
}

    @Override
    public Boolean addAddress(CustomerAddress address) {
        Session session = sessionFactory.getCurrentSession();
        Integer addressID;

        try {
            addressID = (Integer) session.save(address);
            logger.info("addAddress(CustomerAddress) successfully added address_id=" + addressID);
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public Boolean updateAddress(CustomerAddress address) {
        Session session = sessionFactory.getCurrentSession();

        try {
            CustomerAddress ca = session.get(CustomerAddress.class, address.getAddressID());
            ca.setValue(address.getValue());
            ca.setDateModified(address.getDateModified());
            ca.setUserID(address.getUserID());
            ca.setAddressType(address.getAddressType());
            session.update(ca);

            logger.info("addAddress(CustomerAddress) successfully updated address_id="+ address.getAddressID());
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public Boolean changeStatus(Integer addressID, Boolean status) {
        Session session = sessionFactory.getCurrentSession();
        Integer state = status?0:1;

        try {
            CustomerAddress ca = session.get(CustomerAddress.class, addressID);
            ca.setIsActive(state);
            session.update(ca);

            logger.info("changeStatus(" + addressID + ", " + status + " ) successfully updated address_id=" + addressID);
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
