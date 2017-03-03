package com.bank.web.model.repository;

import com.bank.web.model.entity.*;
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

@Repository("customerRepository")
@Transactional
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    private RowMapperService rowMapperService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger logger = Logger.getLogger(CustomerRepositoryImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("bank.customer_info");
    }

    @Override
    public List<CustomerInfo> customersList(Boolean isActive) {
        Map<String, CustomerInfo> param = new HashMap<>();

        String isActiveClause;

        if (isActive == null) {
            isActiveClause = " 1=1 ";
        } else if (isActive) {
            isActiveClause = " is_active = 1 ";
        } else {
            isActiveClause = " is_active = 0 ";
        }

        String sql = " select customer_id, first_name, last_name, middle_name, " +
                " birth_date, date_modified, is_active, user_id, date_created " +
                " from bank.customer_info " +
                " where " + isActiveClause +
                " order by customer_id ";

        ((JdbcTemplate)(namedParameterJdbcTemplate.getJdbcOperations())).setFetchSize(500);
        List<CustomerInfo> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(CustomerInfo.class));
        logger.info(" Obtain data from bank.customer_info with condition (" + result.size() + ") ");

        return result;
    }

    @Override
    public CustomerInfo customerDetails(Integer customerID) {
        Map<String, Integer> param = new HashMap<>();

        String sql = " select customer_id, first_name, last_name, middle_name, " +
                " birth_date, date_modified, is_active, user_id, date_created " +
                " from bank.customer_info where customer_id = :customerID ";
        param.put("customerID", customerID);
        CustomerInfo result = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapperService.getRowMapper(CustomerInfo.class));
        logger.info(" Obtain customer details using cistomerID = " + customerID);

        return result;
    }

    @Override
    public void changeStatus(Integer customerID, Boolean status) {
        Map<String, Object> fields = new HashMap<>();
        Integer state = status?1:0;

        String sql = " update bank.customer_info set is_active = :state where customer_id = :customerID ";

        fields.put("customerID", customerID);
        fields.put("state", state);

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.customer_info " + customerID + " was update is_active " + rowNumbers + " rows");
        }
    }

    @Override
    public List<Accounts> getAccounts(Integer customerID) {
        Map<String, Object> param = new HashMap<>();

        String sql = " select account_id " +
                "     , account_number " +
                "     , account_owner " +
                "     , date_opened " +
                "     , date_closed " +
                "     , date_created " +
                "     , date_modified " +
                "     , user_id " +
                "     , (select dir_type from bank.directory " +
                "         where dir_id = account_type and dir_group = :dir_group and is_active = 1) account_type " +
                "     , is_suspended " +
                "     , comment  " +
                " , (select z.rest_sum " +
                "      from bank.account_rest z " +
                "     where z.rest_id = (select max(r.rest_id) " +
                "                          from bank.account_rest r " +
                "                         where r.account_id = z.account_id) " +
                "       and z.account_id = t.account_id) as rest_sum " +
                "  from bank.accounts t " +
                " where account_owner = :customerID " +
                " order by account_id ";
        param.put("customerID", customerID);
        param.put("dir_group", Directory.ACCOUNTS);
        List<Accounts> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(Accounts.class));
        logger.info(" Obtain accounts list for cistomerID = " + customerID);

        return result;
    }

    @Override
    public List<CustomerAddress> getAddresses(Integer customerID) {
        Map<String, Object> param = new HashMap<>();

        String sql = " select address_id " +
                "     , value " +
                "     , date_created " +
                "     , date_modified " +
                "     , is_active " +
                "     , user_id " +
                "     , (select dir_type from bank.directory " +
                "         where dir_id = address_type and dir_group = :dir_group and is_active = 1) address_type " +
                "     , customer_id " +
                "  from bank.customer_address " +
                " where customer_id = :customerID " +
                " order by address_id ";
        param.put("customerID", customerID);
        param.put("dir_group", Directory.ADDRESS);
        List<CustomerAddress> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(CustomerAddress.class));
        logger.info(" Obtain addresses list for cistomerID = " + customerID);

        return result;
    }

    @Override
    public List<CustomerContacts> getContacts(Integer customerID) {
        Map<String, Object> param = new HashMap<>();

        String sql = " select contact_id " +
                "     , value " +
                "     , date_created " +
                "     , date_modified " +
                "     , is_active " +
                "     , user_id " +
                "     , (select dir_type from bank.directory " +
                "         where dir_id = contact_type and dir_group = :dir_group and is_active = 1) contact_type " +
                "     , customer_id " +
                "  from bank.customer_contacts " +
                " where customer_id = :customerID " +
                " order by contact_id ";
        param.put("customerID", customerID);
        param.put("dir_group", Directory.CONTACTS);
        List<CustomerContacts> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(CustomerContacts.class));
        logger.info(" Obtain contacts list for cistomerID = " + customerID);

        return result;
    }

    @Override
    public List<CustomerPapers> getPapers(Integer customerID) {
        Map<String, Object> param = new HashMap<>();

        String sql = " select paper_id " +
                "     , value " +
                "     , date_created " +
                "     , date_modified " +
                "     , is_active " +
                "     , user_id " +
                "     , (select dir_type from bank.directory " +
                "         where dir_id = paper_type and dir_group = :dir_group and is_active = 1) paper_type " +
                "     , customer_id " +
                "  from bank.customer_papers " +
                " where customer_id = :customerID " +
                " order by paper_id ";
        param.put("customerID", customerID);
        param.put("dir_group", Directory.PAPERS);
        List<CustomerPapers> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(CustomerPapers.class));
        logger.info(" Obtain contacts list for cistomerID = " + customerID);

        return result;
    }

    @Override
    public void addCustomer(CustomerInfo customerInfo) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " insert into bank.customer_info (first_name, last_name, middle_name, birth_date, is_active, user_id, date_created) " +
                " values (:first_name, :last_name, :middle_name, :birth_date, :is_active, :user_id, :date_created) ";

        fields.put("first_name", customerInfo.getFirstName());
        fields.put("last_name", customerInfo.getLastName());
        fields.put("middle_name", customerInfo.getMiddleName());
        fields.put("birth_date", customerInfo.getBirthDate());
        fields.put("is_active", customerInfo.getIsActive());
        fields.put("user_id", customerInfo.getUserID());
        fields.put("date_created", customerInfo.getDateCreated());

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.customer_info was inserted " + rowNumbers + " rows");
        }
    }

    @Override
    public void updateCustomer(CustomerInfo customerInfo) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " update bank.customer_info set first_name = :first_name, last_name = :last_name, middle_name = :middle_name, " +
                " birth_date = :birth_date, date_modified = :date_modified, user_id = :user_id where customer_id = :customer_id ";

        fields.put("customer_id", customerInfo.getCustomerID());
        fields.put("first_name", customerInfo.getFirstName());
        fields.put("last_name", customerInfo.getLastName());
        fields.put("middle_name", customerInfo.getMiddleName());
        fields.put("birth_date", customerInfo.getBirthDate());
        fields.put("date_modified", customerInfo.getDateModified());
        fields.put("user_id", customerInfo.getUserID());

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.customer_info " + customerInfo.getCustomerID() + " was update " + rowNumbers + " rows");
        }
    }
}
