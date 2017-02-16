package com.bank.web.model.repository;

import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.Transactions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.roma.impl.service.RowMapperService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository("accountRepository")
@Transactional
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private RowMapperService rowMapperService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger logger = Logger.getLogger(AccountRepositoryImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSuorce) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSuorce);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSuorce).withTableName("bank.accounts");
    }

    @Override
    public List<Accounts> accountsList() {
        return null;
    }

    @Override
    public void changeAccountStatus(Accounts account) {

    }

    @Override
    public void suspendAccount(Accounts account) {

    }

    @Override
    public void releaseAccount(Accounts account) {

    }

    @Override
    public void decreaseRest(Accounts account) {

    }

    @Override
    public void increaseRest(Accounts account) {

    }

    @Override
    public void getAccountRest(Accounts account) {

    }

    @Override
    public List<Transactions> getTransactions(Accounts account) {
        return null;
    }

    @Override
    public void addAccount(Accounts account) {

    }

    @Override
    public void addTransaction(Transactions transaction) {

    }

    @Override
    public void updateAccount(Accounts account) {

    }
}
