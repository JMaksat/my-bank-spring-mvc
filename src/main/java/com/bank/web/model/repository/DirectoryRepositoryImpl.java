package com.bank.web.model.repository;

import com.bank.web.model.entity.Directory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

@Repository("directoryRepository")
@Transactional
public class DirectoryRepositoryImpl implements DirectoryRepository {

    @Autowired
    private RowMapperService rowMapperService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger logger = Logger.getLogger(DirectoryRepositoryImpl.class);

    private String sql = " select dir_id, dir_group, dir_type, description, date_created, date_modified, is_active, user_id " +
            " from bank.directory where dir_group = :dir_group and is_active = 1 ";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("bank.directory");
    }

    @Override
    public List<Directory> directoryList() {
        Map<String, Object> param = new HashMap<>();

        String sql = " select dir_id " +
                " , dir_group " +
                " , dir_type " +
                " , description " +
                " , date_created " +
                " , date_modified " +
                " , is_active " +
                " , user_id " +
                " from bank.directory " +
                " order by dir_id ";

        param.put("dir_group", Directory.OPERATIONS);
        ((JdbcTemplate)(namedParameterJdbcTemplate.getJdbcOperations())).setFetchSize(500);
        List<Directory> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(Directory.class));
        logger.info(" Obtain all data from bank.transactions ");

        return result;
    }

    @Override
    @Cacheable("account")
    public List<Directory> getAccountTypes() {
        Map<String, Object> param = new HashMap<>();

        param.put("dir_group", Directory.ACCOUNTS);
        List<Directory> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(Directory.class));
        logger.info(" Obtain directory data of type = " + Directory.ACCOUNTS);

        return result;
    }

    @Override
    @Cacheable("operation")
    public List<Directory> getTransactionTypes() {
        Map<String, Object> param = new HashMap<>();

        param.put("dir_group", Directory.OPERATIONS);
        List<Directory> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(Directory.class));
        logger.info(" Obtain directory data of type = " + Directory.OPERATIONS);

        return result;
    }

    @Override
    @Cacheable("address")
    public List<Directory> getAddressTypes() {
        Map<String, Object> param = new HashMap<>();

        param.put("dir_group", Directory.ADDRESS);
        List<Directory> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(Directory.class));
        logger.info(" Obtain directory data of type = " + Directory.ADDRESS);

        return result;
    }

    @Override
    @Cacheable("contact")
    public List<Directory> getContactTypes() {
        Map<String, Object> param = new HashMap<>();

        param.put("dir_group", Directory.CONTACTS);
        List<Directory> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(Directory.class));
        logger.info(" Obtain directory data of type = " + Directory.CONTACTS);

        return result;
    }

    @Override
    @Cacheable("paper")
    public List<Directory> getPaperTypes() {
        Map<String, Object> param = new HashMap<>();

        param.put("dir_group", Directory.PAPERS);
        List<Directory> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(Directory.class));
        logger.info(" Obtain directory data of type = " + Directory.PAPERS);

        return result;
    }

    @Override
    @CacheEvict(value = {"account", "address", "operation", "contact", "paper"}, allEntries = true)
    public void addEntry(Directory directory) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " insert into bank.directory (dir_group, dir_type, description, date_created, is_active, user_id) " +
                " values (:dir_group, :dir_type, :description, :date_created, :is_active, :user_id) ";

        fields.put("dir_group", directory.getDirGroup());
        fields.put("dir_type", directory.getDirType());
        fields.put("description", directory.getDescription());
        fields.put("date_created", directory.getDateCreated());
        fields.put("is_active", directory.getIsActive());
        fields.put("user_id", directory.getUserID());
        // Check for DuplicateKeyException
        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.directory was inserted " + rowNumbers + " rows");
        }
    }

    @Override
    @CacheEvict(value = {"account", "address", "operation", "contact", "paper"}, allEntries = true)
    public void updateEntry(Directory directory) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " update bank.directory set dir_group = :dir_group, dir_type = :dir_type, description = :description " +
                " , date_modified = :date_modified, user_id = :user_id where dir_id = :dir_id ";

        fields.put("dir_id", directory.getDirID());
        fields.put("dir_group", directory.getDirGroup());
        fields.put("dir_type", directory.getDirType());
        fields.put("description", directory.getDescription());
        fields.put("date_modified", directory.getDateModified());
        fields.put("user_id", directory.getUserID());
        // Check for DuplicateKeyException
        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.directory was inserted " + rowNumbers + " rows");
        }
    }

    @Override
    @CacheEvict(value = {"account", "address", "operation", "contact", "paper"}, allEntries = true)
    public void changeStatus(Integer dirID, Boolean status) {
        Map<String, Object> fields = new HashMap<>();
        Integer state = status?1:0;

        String sql = " update bank.directory set is_active = :state where dir_id = :dir_id ";

        fields.put("dir_id", dirID);
        fields.put("state", state);

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.directory " + dirID + " was update is_active " + rowNumbers + " rows");
        }
    }
}
