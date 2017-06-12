package com.bank.web.model.repository;

import com.bank.web.model.entity.UserRoles;
import com.bank.web.model.entity.Users;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("userRolesRepository")
@Transactional
public class UserRolesRepositoryImpl implements UserRolesRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(UserRolesRepositoryImpl.class);

    public UserRolesRepositoryImpl() {}

    public UserRolesRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserRoles> findByRole(Users users) {
        List<UserRoles> result = sessionFactory.getCurrentSession()
                .createQuery(" from UserRoles where userName = :users ")
                .setParameter("users", users).list();

        if (result.size() > 0)
            logger.info("findByRole(" + users.getUserName() + ") roles found = " + result.size());
        else
            logger.info("findByRole(" + users.getUserName() + ") no role found ");

        return result;
    }
}
