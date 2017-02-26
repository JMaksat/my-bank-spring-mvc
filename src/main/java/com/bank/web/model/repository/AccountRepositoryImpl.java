package com.bank.web.model.repository;

import com.bank.web.model.entity.AccountRest;
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
    public List<Accounts> accountsList(Boolean suspended, Boolean closed) {
        Map<String, Object> param = new HashMap<>();

        String suspendedClause;
        String closedClause;

        if (suspended == null) {
            suspendedClause = "";
        } else if (suspended) {
            suspendedClause = " and is_suspended = 1 ";
        } else {
            suspendedClause = " and is_suspended = 0 ";
        }

        if (closed == null) {
            closedClause = "";
        } else if (closed) {
            closedClause = " and date_closed is not null ";
        } else {
            closedClause = " and date_closed is null ";
        }

        String sql = " select account_id " +
                " , account_number " +
                " , account_owner " +
                " , date_opened " +
                " , date_closed " +
                " , date_created " +
                " , date_modified " +
                " , user_id " +
                " , (select dir_type from bank.directory " +
                "     where dir_id = account_type and dir_group = :dir_group and is_active = 1) account_type " +
                " , is_suspended " +
                " , comment " +
                " , (select z.rest_sum " +
                "      from bank.account_rest z " +
                "     where z.rest_id = (select max(r.rest_id) " +
                "                          from bank.account_rest r " +
                "                         where r.account_id = z.account_id) " +
                "       and z.account_id = t.account_id) as rest_sum " +
                " from bank.accounts t " +
                " where 1=1 " +
                suspendedClause +
                closedClause +
                " order by account_id ";

        param.put("dir_group", Directory.ACCOUNTS);
        ((JdbcTemplate)(namedParameterJdbcTemplate.getJdbcOperations())).setFetchSize(500);
        List<Accounts> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(Accounts.class));
        logger.info(" Obtain data from bank.accounts with condition (" + result.size() + ") ");

        return result;
    }

    @Override
    public Accounts getAccountById(Integer accountID) {
        Map<String, Object> param = new HashMap<>();

        String sql = " select account_id " +
                " , account_number " +
                " , account_owner " +
                " , date_opened " +
                " , date_closed " +
                " , date_created " +
                " , date_modified " +
                " , user_id " +
                " , (select dir_type from bank.directory " +
                "     where dir_id = account_type and dir_group = :dir_group and is_active = 1) account_type " +
                " , is_suspended " +
                " , comment " +
                " , (select z.rest_sum " +
                "      from bank.account_rest z " +
                "     where z.rest_id = (select max(r.rest_id) " +
                "                          from bank.account_rest r " +
                "                         where r.account_id = z.account_id) " +
                "       and z.account_id = t.account_id) as rest_sum " +
                " from bank.accounts t " +
                " where account_id = :account_id ";
        param.put("account_id", accountID);
        param.put("dir_group", Directory.ACCOUNTS);
        Accounts result = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapperService.getRowMapper(Accounts.class));
        logger.info(" Obtain account details using accountID = " + accountID);

        return result;
    }

    @Override
    public Accounts getAccountByNumber(String accountNumber) {
        Map<String, Object> param = new HashMap<>();

        String sql = " select account_id " +
                " , account_number " +
                " , account_owner " +
                " , date_opened " +
                " , date_closed " +
                " , date_created " +
                " , date_modified " +
                " , user_id " +
                " , (select dir_type from bank.directory " +
                "     where dir_id = account_type and dir_group = :dir_group and is_active = 1) account_type " +
                " , is_suspended " +
                " , comment " +
                " , (select z.rest_sum " +
                "      from bank.account_rest z " +
                "     where z.rest_id = (select max(r.rest_id) " +
                "                          from bank.account_rest r " +
                "                         where r.account_id = z.account_id) " +
                "       and z.account_id = t.account_id) as rest_sum " +
                " from bank.accounts t " +
                " where account_number = :account_number ";
        param.put("account_number", accountNumber);
        param.put("dir_group", Directory.ACCOUNTS);
        Accounts result = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapperService.getRowMapper(Accounts.class));
        logger.info(" Obtain account details using accountNumber = " + accountNumber);

        return result;
    }

    @Override
    public Double getAccountRest(Integer accountID) {
        Map<String, Object> param = new HashMap<>();

        String sql = " select t.rest_sum " +
                "  from bank.account_rest t " +
                " where t.account_id = :account_id " +
                "   and t.rest_id = (select max(z.rest_id) " +
                "                      from bank.account_rest z " +
                "                     where z.account_id = t.account_id) ";
        param.put("account_id", accountID);
        Double result = namedParameterJdbcTemplate.queryForObject(sql, param, Double.class);
        logger.info(" Obtain account rest using accountID = " + accountID);

        return result;
    }

    @Override
    public Integer getNewAccountSeq() {
        Map<String, Object> param = new HashMap<>();

        String sql = " select nextval('bank.accounts_seq') ";
        Integer result = namedParameterJdbcTemplate.queryForObject(sql, param, Integer.class);
        logger.info(" Next sequence for bank.accounts generated ");

        return result;
    }

    @Override
    public Integer getNewRestSeq() {
        Map<String, Object> param = new HashMap<>();

        String sql = " select nextval('bank.account_rest_seq') ";
        Integer result = namedParameterJdbcTemplate.queryForObject(sql, param, Integer.class);
        logger.info(" Next sequence for bank.account_rest generated ");

        return result;
    }

    @Override
    public void newRest(AccountRest accountRest) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " insert into bank.account_rest (rest_id, account_id, rest_sum, transaction_id, rest_date, rest_time) " +
                "   values (:rest_id, :account_id, :rest_sum, :transaction_id, :rest_date, now()) ";

        fields.put("rest_id", accountRest.getRestID());
        fields.put("account_id", accountRest.getAccountID());
        fields.put("rest_sum", accountRest.getRestSum());
        fields.put("transaction_id", accountRest.getTransactionID());
        fields.put("rest_date", accountRest.getRestDate());

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.account_rest was inserted " + rowNumbers + " rows");
        }
    }

    @Override
    public void addAccount(Accounts account) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " insert into bank.accounts (account_id, account_number, account_owner, date_opened, date_closed, date_created, user_id, account_type, is_suspended, comment) " +
                " values (:account_id, :account_number, :account_owner, :date_opened, :date_closed, :date_created, :user_id, :account_type, :is_suspended, :comment) ";

        fields.put("account_id", account.getAccountID());
        fields.put("account_number", account.getAccountNumber());
        fields.put("account_owner", account.getAccountOwner());
        fields.put("date_opened", account.getDateOpened());
        fields.put("date_closed", account.getDateClosed());
        fields.put("date_created", account.getDateCreated());
        fields.put("user_id", account.getUserID());
        fields.put("account_type", Integer.valueOf(account.getAccountType()));
        fields.put("is_suspended", account.getIsSuspended());
        fields.put("comment", account.getComment());

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.accounts was inserted " + rowNumbers + " rows");
        }
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
        fields.put("account_type", Integer.valueOf(account.getAccountType()));
        fields.put("is_suspended", account.getIsSuspended());
        fields.put("comment", account.getComment());
        fields.put("account_id", account.getAccountID());

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.accounts " + account.getAccountID() + " was update " + rowNumbers + " rows");
        }
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
    public void closeAccount(Integer accountID) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " update bank.accounts set date_closed = now() where account_id = :account_id ";

        fields.put("account_id", accountID);

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.accounts " + accountID + " was update date_closed " + rowNumbers + " rows");
        }
    }
}
