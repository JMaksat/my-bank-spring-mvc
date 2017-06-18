package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerPapers;
import com.bank.web.model.entity.Directory;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Repository("paperRepository")
@Transactional
public class PaperRepositoryImpl implements PaperRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(PaperRepositoryImpl.class);

    public PaperRepositoryImpl(){}

    public PaperRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CustomerPapers getPaper(Integer paperID) {
        CustomerPapers result;
        String hql;

        hql = " from CustomerPapers where paperID = :paper_id ";
        List<CustomerPapers> pprList = sessionFactory.getCurrentSession().createQuery(hql)
                .setParameter("paper_id", paperID).list();
        result = pprList.get(0);

        hql = " from Directory where dirID = :paper_type and dirGroup = :dir_group and isActive = 1 ";
        Query qry = sessionFactory.getCurrentSession().createQuery(hql);
        qry.setParameter("paper_type", result.getPaperType());
        qry.setParameter("dir_group", Directory.PAPERS);
        List<Directory> dirList = qry.list();
        Directory dir = dirList.get(0);

        result.setPaperTypeLabel(dir.getDirType());
        logger.info("getPaper(" + paperID + ") records found = " + pprList.size());

        return result;
    }

    @Override
    public Boolean addPaper(CustomerPapers paper) {
        Session session = sessionFactory.getCurrentSession();
        Integer paperID;

        try {
            paperID = (Integer) session.save(paper);
            logger.info("addPaper(CustomerPaper) successfully added paper_id=" + paperID);
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public Boolean updatePaper(CustomerPapers paper) {
        Session session = sessionFactory.getCurrentSession();

        try {
            CustomerPapers cp = session.get(CustomerPapers.class, paper.getPaperID());
            cp.setValue(paper.getValue());
            cp.setDateModified(paper.getDateModified());
            cp.setUserID(paper.getUserID());
            cp.setPaperType(paper.getPaperType());
            session.update(cp);

            logger.info("updatePaper(CustomerPaper) successfully updated paper_id=" + paper.getPaperID());
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    @Override
    public Boolean changeStatus(Integer paperID, Boolean status) {
        Session session = sessionFactory.getCurrentSession();
        Integer state = status?0:1;

        try {
            CustomerPapers cp = session.get(CustomerPapers.class, paperID);
            cp.setIsActive(state);
            session.update(cp);

            logger.info("changeStatus(" + paperID + ", " + status + " ) successfully updated paper_id=" + paperID);
        } catch (HibernateException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}