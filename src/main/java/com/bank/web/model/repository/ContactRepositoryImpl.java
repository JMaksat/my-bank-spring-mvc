package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerContacts;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.roma.impl.service.RowMapperService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository("contactRepository")
@Transactional
public class ContactRepositoryImpl implements ContactRepository {

    @Autowired
    private RowMapperService rowMapperService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger logger = Logger.getLogger(ContactRepositoryImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSuorce) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSuorce);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSuorce).withTableName("bank.customer_contacts");
    }

    @Override
    public List<CustomerContacts> getContact(Integer contactID) {
        return null;
    }

    @Override
    public void addContact(CustomerContacts contact) {

    }

    @Override
    public void updateContact(CustomerContacts contact) {

    }

    @Override
    public void changeStatus(Integer contactID, Boolean status) {

    }
}
