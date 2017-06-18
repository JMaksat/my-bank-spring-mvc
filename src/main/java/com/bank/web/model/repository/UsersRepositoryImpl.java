package com.bank.web.model.repository;

import org.apache.log4j.Logger;
import com.bank.web.model.entity.Users;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository("usersRepository")
@Transactional
public class UsersRepositoryImpl implements UsersRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(UsersRepositoryImpl.class);

    public UsersRepositoryImpl() {}

    public UsersRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Users> getUsers() {
        String hql = " from Users where isActive = 1 ";

        List<Users> result = sessionFactory.getCurrentSession()
                .createQuery(hql)
                .list();

        for (Users user : result) {
            String sql = " select string_agg(ur.user_role, ',') from bank.user_roles ur where ur.username = :username ";
            List<String> roleList = sessionFactory.getCurrentSession()
                    .createNativeQuery(sql)
                    .setParameter("username", user.getUserName())
                    .list();

            user.setRoles(roleList.get(0));
        }

        logger.info("getUsers() records found = " + result.size());

        return result;
    }

    @Override
    public Users findByLogin(String login) {
        Users result = null;

        List<Users> userList = sessionFactory.getCurrentSession()
                .createQuery(" from Users  where userName = :login and isActive = 1 ")
                .setParameter("login", login).list();

        if (userList.size() > 0) {
            result = userList.get(0);
            logger.info("findByLogin(" + login + ") users found = " + userList.size());
        } else {
            logger.info("findByLogin(" + login + ") user not found");
        }

        return result;
    }

    /*
    * New user:
    * String password = "12345";
    * BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    * String hashedPassword = passwordEncoder.encode(password);
    * */
}
