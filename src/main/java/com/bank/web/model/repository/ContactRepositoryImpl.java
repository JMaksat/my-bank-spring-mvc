package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerContacts;
import com.bank.web.model.entity.Directory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.roma.impl.service.RowMapperService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public CustomerContacts getContact(Integer contactID) {
        Map<String, Object> param = new HashMap<>();

        String sql = " select contact_id" +
                "     , value " +
                "     , date_created " +
                "     , date_modified " +
                "     , is_active " +
                "     , user_id " +
                "     , (select dir_type from bank.directory " +
                "         where dir_id = contact_type and dir_group = :dir_group and is_active = 1) contact_type " +
                "     , customer_id " +
                " from bank.customer_contacts where contact_id = :contact_id ";
        param.put("contact_id", contactID);
        param.put("dir_group", Directory.CONTACTS);
        CustomerContacts result = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapperService.getRowMapper(CustomerContacts.class));
        logger.info(" Obtain contact details using contactID = " + contactID);

        return result;
    }

    @Override
    public void addContact(CustomerContacts contact) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " insert into bank.customer_contacts (value, date_created, is_active, user_id, contact_type, customer_id) " +
                " values (:value, :date_created, :is_active, :user_id, :contact_type, :customer_id) ";

        fields.put("value", contact.getValue());
        fields.put("date_created", contact.getDateCreated());
        fields.put("is_active", contact.getIsActive());
        fields.put("user_id", contact.getUserID());
        fields.put("contact_type", Integer.valueOf(contact.getContactType()));
        fields.put("customer_id", contact.getCustomerID());

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.customer_contacts was inserted " + rowNumbers + " rows");
        }
    }

    @Override
    public void updateContact(CustomerContacts contact) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " update bank.customer_contacts set value = :value, contact_type = :contact_type, date_modified = :date_modified, user_id = :user_id where contact_id = :contact_id ";

        fields.put("contact_id", contact.getContactID());
        fields.put("value", contact.getValue());
        fields.put("contact_type", Integer.valueOf(contact.getContactType()));
        fields.put("date_modified", contact.getDateModified());
        fields.put("user_id", contact.getUserID());

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.customer_contacts " + contact.getContactID() + " was update " + rowNumbers + " rows");
        }
    }

    @Override
    public void changeStatus(Integer contactID, Boolean status) {
        Map<String, Object> fields = new HashMap<>();
        Integer state = status?1:0;

        String sql = " update bank.customer_contacts set is_active = :state where contact_id = :contact_id ";

        fields.put("contact_id", contactID);
        fields.put("state", state);

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.customer_contacts " + contactID + " was update is_active " + rowNumbers + " rows");
        }
    }
}
