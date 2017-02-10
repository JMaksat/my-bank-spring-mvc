package com.bank.web.model.repository;

import org.apache.log4j.Logger;
import com.bank.web.model.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.roma.impl.service.RowMapperService;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository("usersRepository")
public class UsersRepositoryImpl implements UsersRepository {

    @Autowired
    private RowMapperService rowMapperService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger logger = Logger.getLogger(UsersRepositoryImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSuorce) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSuorce);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSuorce).withTableName("bank.users");
    }

    @Override
    public Users findByLogin(String login) {
        String sql = " select username, password, is_active from bank.users where username = :login ";
        Map<String, Object> params = Collections.<String, Object>singletonMap("login", login);
        Users user = null;
        try {
            user = namedParameterJdbcTemplate.queryForObject(sql, params, rowMapperService.getRowMapper(Users.class));
            logger.debug("Found Users entity with login '" + login + "'");
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Users entity with login '" + login + "' not found");
        }
        return user;
    }
}
