package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerAddress;
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

@Repository("addressRepository")
@Transactional
public class AddressRepositoryImpl implements AddressRepository {

    @Autowired
    private RowMapperService rowMapperService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger logger = Logger.getLogger(AddressRepositoryImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSuorce) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSuorce);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSuorce).withTableName("bank.customer_address");
    }

    @Override
    public List<CustomerAddress> getAddress(Integer addressID) {
        Map<String, Object> param = new HashMap<>();

        String sql = " select address_id" +
                "     , value " +
                "     , date_created " +
                "     , date_modified " +
                "     , is_active " +
                "     , user_id " +
                "     , (select dir_type from bank.directory " +
                "         where dir_id = address_type and dir_group = :dir_group and is_active = 1) address_type " +
                "     , customer_id " +
                "from bank.customer_address where address_id = :address_id ";
        param.put("address_id", addressID);
        param.put("dir_group", Directory.ADDRESS);
        List<CustomerAddress> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(CustomerAddress.class));
        logger.info(" Obtain address details using addressID = " + addressID);

        return result;
    }

    @Override
    public void addAddress(CustomerAddress address) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " insert into bank.customer_address (value, date_created, is_active, user_id, address_type, customer_id) " +
                " values (:value, :date_created, :is_active, :user_id, :address_type, :customer_id) ";

        fields.put("value", address.getValue());
        fields.put("date_created", address.getDateCreated());
        fields.put("is_active", address.getIsActive());
        fields.put("user_id", address.getUserID());
        fields.put("address_type", Integer.valueOf(address.getAddressType()));
        fields.put("customer_id", address.getCustomerID());
        fields.put("dir_group", Directory.ADDRESS);

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.customer_address was inserted " + rowNumbers + " rows");
        }
    }

    @Override
    public void updateAddress(CustomerAddress address) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " update bank.customer_address set value = :value, address_type = :address_type, date_modified = :date_modified, user_id = :user_id where address_id = :address_id ";

        fields.put("address_id", address.getAddressID());
        fields.put("value", address.getValue());
        fields.put("address_type", Integer.valueOf(address.getAddressType()));
        fields.put("date_modified", address.getDateModified());
        fields.put("user_id", address.getUserID());

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.customer_info " + address.getCustomerID() + " was update " + rowNumbers + " rows");
        }
    }

    @Override
    public void changeStatus(Integer addressID, Boolean status) {
        Map<String, Object> fields = new HashMap<>();
        Integer state = status?1:0;

        String sql = " update bank.customer_address set is_active = :state where address_id = :address_id ";

        fields.put("address_id", addressID);
        fields.put("state", state);

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.customer_address " + addressID + " was update is_active " + rowNumbers + " rows");
        }
    }
}
