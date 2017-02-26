package com.bank.web.model.repository;

import com.bank.web.model.entity.BankParameters;
import com.bank.web.model.entity.Directory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.roma.impl.service.RowMapperService;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("parametersRepository")
public class ParametersRepositoryImpl implements ParametersRepository {

    @Autowired
    private RowMapperService rowMapperService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final Logger logger = Logger.getLogger(ParametersRepositoryImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSuorce) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSuorce);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSuorce).withTableName("bank.bank_parameters");
    }

    @Override
    public BankParameters getParamTransAccount() {
        Map<String, Object> param = new HashMap<>();

        String sql = " select parameter_id " +
                " , parent_id " +
                " , parameter_name " +
                " , value " +
                " , date_created " +
                " , date_modified " +
                " , active_from " +
                " , active_to " +
                " , user_id " +
                " from bank.bank_parameters " +
                " where parameter_name = :parameter_name ";
        param.put("parameter_name", "TRANSFER_ACCOUNT");
        BankParameters result = namedParameterJdbcTemplate.queryForObject(sql, param, rowMapperService.getRowMapper(BankParameters.class));
        logger.info(" Obtain value for parameter TRANSFER_ACCOUNT");

        return result;    }
}
