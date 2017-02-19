package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerPapers;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.roma.impl.service.RowMapperService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository("paperRepository")
@Transactional
public class PaperRepositoryImpl implements PaperRepository {

    @Autowired
    private RowMapperService rowMapperService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger logger = Logger.getLogger(PaperRepositoryImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSuorce) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSuorce);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSuorce).withTableName("bank.customer_papers");
    }

    @Override
    public List<CustomerPapers> getPaper(Integer paperID) {
        return null;
    }

    @Override
    public void addPaper(CustomerPapers paper) {

    }

    @Override
    public void updatePaper(CustomerPapers paper) {

    }

    @Override
    public void changeStatus(Integer paperID, Boolean status) {

    }
}
