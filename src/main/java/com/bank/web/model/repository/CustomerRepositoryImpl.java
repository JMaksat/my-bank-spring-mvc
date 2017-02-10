package com.bank.web.model.repository;

import com.bank.web.model.entity.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.roma.impl.service.RowMapperService;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository("customerRepository")
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    private RowMapperService rowMapperService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger logger = Logger.getLogger(CustomerRepositoryImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSuorce) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSuorce);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSuorce).withTableName("bank.customer_info");
    }

    @Override
    public List<CustomerInfo> customersList() {
        return null;
    }

    @Override
    public void changeStatus(CustomerInfo customerInfo, Boolean status) {

    }

    @Override
    public List<Accounts> getAccounts(CustomerInfo customerInfo) {
        return null;
    }

    @Override
    public List<CustomerAddress> getAddresses(CustomerInfo customerInfo) {
        return null;
    }

    @Override
    public List<CustomerContacts> getContacts(CustomerInfo customerInfo) {
        return null;
    }

    @Override
    public List<CustomerPapers> getPapers(CustomerInfo customerInfo) {
        return null;
    }

    @Override
    public void addCustomer(CustomerInfo customerInfo) {

    }

    @Override
    public void updateCustomer(CustomerInfo customerInfo) {

    }
}
