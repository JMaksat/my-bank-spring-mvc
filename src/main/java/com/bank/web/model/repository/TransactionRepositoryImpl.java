package com.bank.web.model.repository;

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

@Repository("transactionRepositoryImpl")
@Transactional
public class TransactionRepositoryImpl implements TransactionRepository {

    @Autowired
    private RowMapperService rowMapperService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger logger = Logger.getLogger(TransactionRepositoryImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSuorce) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSuorce);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSuorce).withTableName("bank.transactions");
    }


    @Override
    public List<Transactions> transactionsList() {
        Map<String, Object> param = new HashMap<>();

        String sql = " select transaction_id " +
                " , (select dir_type from bank.directory " +
                "     where dir_id = operation_type and dir_group = :dir_group and is_active = 1) operation_type " +
                " , is_reversed " +
                " , transaction_sum " +
                " , transaction_date " +
                " , to_char(transaction_time, 'hh24:mi:ss') transaction_time " +
                " , user_id " +
                " , (select account_number from bank.accounts where account_id = account_debit) account_debit " +
                " , (select account_number from bank.accounts where account_id = account_credit) account_credit " +
                " from bank.transactions " +
                " order by transaction_id ";

        param.put("dir_group", Directory.OPERATIONS);
        ((JdbcTemplate)(namedParameterJdbcTemplate.getJdbcOperations())).setFetchSize(500);
        List<Transactions> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(Transactions.class));
        logger.info(" Obtain all data from bank.transactions ");

        return result;
    }

    @Override
    public List<Transactions> getTransactions(Integer accountID) {
        Map<String, Object> param = new HashMap<>();

        String sql = " select transaction_id " +
                " , (select dir_type from bank.directory " +
                "     where dir_id = operation_type and dir_group = :dir_group and is_active = 1) operation_type " +
                " , is_reversed " +
                " , transaction_sum " +
                " , transaction_date " +
                " , to_char(transaction_time, 'hh24:mi:ss') transaction_time " +
                " , user_id " +
                " , (select account_number from bank.accounts where account_id = account_debit) account_debit " +
                " , (select account_number from bank.accounts where account_id = account_credit) account_credit " +
                " from bank.transactions " +
                " where :account_id in (account_debit, account_credit) " +
                " order by transaction_id ";

        param.put("dir_group", Directory.OPERATIONS);
        param.put("account_id", accountID);
        List<Transactions> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(Transactions.class));
        logger.info(" Obtain transactions list for accountID = " + accountID);

        return result;
    }

    @Override
    public Transactions getTransaction(Integer transactionID) {
        return null;
    }

    @Override
    public Integer getNewTransactionSeq() {
        Map<String, Object> param = new HashMap<>();

        String sql = " select nextval('bank.transactions_seq') ";
        Integer result = namedParameterJdbcTemplate.queryForObject(sql, param, Integer.class);
        logger.info(" Next sequence for bank.transactions generated ");

        return result;
    }

    @Override
    public void addTransaction(Transactions transaction) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " insert into bank.transactions (transaction_id, operation_type, is_reversed, transaction_sum, transaction_date, transaction_time, user_id, account_debit, account_credit) " +
                "        values (:transaction_id, :operation_type, :is_reversed, :transaction_sum, :transaction_date, now(), :user_id, :account_debit, :account_credit)  ";

        fields.put("transaction_id", transaction.getTransactionID());
        fields.put("operation_type", Integer.valueOf(transaction.getOperationType()));
        fields.put("is_reversed", transaction.getIsReversed());
        fields.put("transaction_sum", transaction.getTransactionSum());
        fields.put("transaction_date", transaction.getTransactionDate());
        fields.put("user_id", transaction.getUserID());
        fields.put("account_debit", Integer.valueOf(transaction.getAccountDebit()));
        fields.put("account_credit", Integer.valueOf(transaction.getAccountCredit()));

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.transactions was inserted " + rowNumbers + " rows");
        }
    }
}
