package com.bank.web.model.repository;


import com.bank.web.model.entity.UserRoles;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.roma.impl.service.RowMapperService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

@Repository("userRolesRepository")
@Transactional
public class UserRolesRepositoryImpl implements UserRolesRepository {

    @Autowired
    private RowMapperService rowMapperService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger logger = Logger.getLogger(UserRolesRepositoryImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSuorce) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSuorce);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSuorce).withTableName("bank.user_roles");
    }

    @Override
    public UserRoles findByRole(String login) {
        String sql = " select user_role_id, username, role from bank.user_roles where username = :login ";
        Map<String, Object> params = Collections.<String, Object>singletonMap("login", login);
        UserRoles user = null;
        try {
            user = namedParameterJdbcTemplate.queryForObject(sql, params, rowMapperService.getRowMapper(UserRoles.class));
            logger.debug("Found UserRoles entity with login '" + login + "'");
        } catch (EmptyResultDataAccessException e) {
            logger.debug("UserRoles entity with login '" + login + "' not found");
        }
        return user;
    }
}
