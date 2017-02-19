package com.bank.web.model.repository;

import com.bank.web.model.entity.Accounts;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.entity.Transactions;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.roma.impl.service.RowMapperService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Accounts> accountsList(Boolean suspended) {
        Map<String, Accounts> param = new HashMap<>();

        String suspendedClause;

        if (suspended == null) {
            suspendedClause = " 1=1 ";
        } else if (suspended) {
            suspendedClause = " is_suspended = 1 ";
        } else {
            suspendedClause = " is_suspended = 0 ";
        }

        String sql = " select account_id, account_number, account_owner, date_opened, date_closed, date_created" +
                " , date_modified, user_id, account_type, is_suspended, comment " +
                " from bank.accounts " +
                " where " + suspendedClause +
                " order by account_id ";

        ((JdbcTemplate)(namedParameterJdbcTemplate.getJdbcOperations())).setFetchSize(500);
        List<Accounts> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(Accounts.class));
        logger.info(" Obtain data from bank.accounts with condition (" + result.size() + ") ");

        return result;
    }

    @Override
    public List<Accounts> getAccount(Integer accountID) {
        Map<String, Object> param = new HashMap<>();

        String sql = " select account_id" +
                " , account_number" +
                " , account_owner" +
                " , date_opened" +
                " , date_closed" +
                " , date_created" +
                " , date_modified" +
                " , user_id" +
                " , (select dir_type from bank.directory " +
                "     where dir_id = account_type and dir_group = :dir_group and is_active = 1) account_type" +
                " , is_suspended" +
                " , comment " +
                " from bank.accounts where account_id = :account_id ";
        param.put("account_id", accountID);
        param.put("dir_group", Directory.ACCOUNTS);
        List<Accounts> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(Accounts.class));
        logger.info(" Obtain account details using accountID = " + accountID);

        return result;
    }

    @Override
    public void changeStatus(Integer accountID, Boolean status) {
        Map<String, Object> fields = new HashMap<>();
        Integer state = status?1:0;

        String sql = " update bank.accounts set is_suspended = :state where account_id = :account_id ";

        fields.put("account_id", accountID);
        fields.put("state", state);

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.accounts " + accountID + " was update is_active " + rowNumbers + " rows");
        }
    }

    @Override
    public void decreaseRest(Integer accountID, Double amount) {

    }

    @Override
    public void increaseRest(Integer accountID, Double amount) {

    }

    @Override
    public void getAccountRest(Integer accountID) {

    }

    @Override
    public List<Transactions> getTransactions(Integer accountID) {
        return null;
    }

    @Override
    public void addAccount(Accounts account) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " insert into bank.accounts (account_number, account_owner, date_opened, date_closed, date_created, user_id, account_type, comment) " +
                " values (:account_number, :account_owner, :date_opened, :date_closed, :date_created, :user_id, :account_type, :comment) ";

        fields.put("account_number", account.getAccountNumber());
        fields.put("account_owner", account.getAccountOwner());
        fields.put("date_opened", account.getDateOpened());
        fields.put("date_closed", account.getDateClosed());
        fields.put("date_created", account.getDateCreated());
        fields.put("user_id", account.getUserID());
        fields.put("account_type", account.getAccountType());
        fields.put("comment", account.getComment());

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.accounts was inserted " + rowNumbers + " rows");
        }
    }

    @Override
    public void addTransaction(Transactions transaction) {

    }

    @Override
    public void updateAccount(Accounts account) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " update bank.accounts set account_number = :account_number, account_owner = :account_owner" +
                " , date_opened = :date_opened, date_closed = :date_closed, date_modified = :date_modified" +
                " , user_id = :user_id, account_type = :account_type, is_suspended = :is_suspended, comment = :comment " +
                " where account_id = :account_id ";

        fields.put("account_number", account.getAccountNumber());
        fields.put("account_owner", account.getAccountOwner());
        fields.put("date_opened", account.getDateOpened());
        fields.put("date_closed", account.getDateClosed());
        fields.put("date_modified", account.getDateModified());
        fields.put("user_id", account.getUserID());
        fields.put("account_type", account.getAccountType());
        fields.put("is_suspended", account.getIsSuspended());
        fields.put("comment", account.getComment());
        fields.put("account_id", account.getAccountID());

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.accounts " + account.getAccountID() + " was update " + rowNumbers + " rows");
        }
    }
}
