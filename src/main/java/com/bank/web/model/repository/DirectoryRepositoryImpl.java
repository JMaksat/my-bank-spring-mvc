package com.bank.web.model.repository;

import com.bank.web.model.entity.Directory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.roma.impl.service.RowMapperService;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository("directoryRepository")
public class DirectoryRepositoryImpl implements DirectoryRepository {

    @Autowired
    private RowMapperService rowMapperService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger logger = Logger.getLogger(DirectoryRepositoryImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSuorce) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSuorce);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSuorce).withTableName("bank.directory");
    }

    @Override
    public List<Directory> getAccountTypes() {
        //Directory.ACCOUNTS
        return null;
    }

    @Override
    public List<Directory> getTransactionTypes() {
        //Directory.OPERATIONS
        return null;
    }

    @Override
    public List<Directory> getAddressTypes() {
        //Directory.ADDRESS
        return null;
    }

    @Override
    public List<Directory> getContactTypes() {
        //Directory.CONTACTS
        return null;
    }

    @Override
    public List<Directory> getPaperTypes() {
        //Directory.PAPERS
        return null;
    }
}
