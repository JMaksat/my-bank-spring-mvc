package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerPapers;
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
        Map<String, Object> param = new HashMap<>();

        String sql = " select paper_id" +
                "     , value " +
                "     , date_created " +
                "     , date_modified " +
                "     , is_active " +
                "     , user_id " +
                "     , (select dir_type from bank.directory " +
                "         where dir_id = paper_type and dir_group = :dir_group and is_active = 1) paper_type " +
                "     , customer_id " +
                " from bank.customer_papers where paper_id = :paper_id ";
        param.put("paper_id", paperID);
        param.put("dir_group", Directory.PAPERS);
        List<CustomerPapers> result = namedParameterJdbcTemplate.query(sql, param, rowMapperService.getRowMapper(CustomerPapers.class));
        logger.info(" Obtain paper details using paperID = " + paperID);

        return result;
    }

    @Override
    public void addPaper(CustomerPapers paper) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " insert into bank.customer_papers (value, date_created, is_active, user_id, paper_type, customer_id) " +
                " values (:value, :date_created, :is_active, :user_id, :paper_type, :customer_id) ";

        fields.put("value", paper.getValue());
        fields.put("date_created", paper.getDateCreated());
        fields.put("is_active", paper.getIsActive());
        fields.put("user_id", paper.getUserID());
        fields.put("paper_type", Integer.valueOf(paper.getPaperType()));
        fields.put("customer_id", paper.getCustomerID());

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.customer_papers was inserted " + rowNumbers + " rows");
        }
    }

    @Override
    public void updatePaper(CustomerPapers paper) {
        Map<String, Object> fields = new HashMap<>();

        String sql = " update bank.customer_papers set value = :value, paper_type = :paper_type, date_modified = :date_modified, user_id = :user_id where paper_id = :paper_id ";

        fields.put("paper_id", paper.getPaperID());
        fields.put("value", paper.getValue());
        fields.put("paper_type", Integer.valueOf(paper.getPaperType()));
        fields.put("date_modified", paper.getDateModified());
        fields.put("user_id", paper.getUserID());

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.customer_papers " + paper.getPaperID() + " was update " + rowNumbers + " rows");
        }
    }

    @Override
    public void changeStatus(Integer paperID, Boolean status) {
        Map<String, Object> fields = new HashMap<>();
        Integer state = status?1:0;

        String sql = " update bank.customer_papers set is_active = :state where paper_id = :paper_id ";

        fields.put("paper_id", paperID);
        fields.put("state", state);

        int rowNumbers = namedParameterJdbcTemplate.update(sql, fields);

        if (rowNumbers != 1) {
            logger.warn("Warning! For bank.customer_papers " + paperID + " was update is_active " + rowNumbers + " rows");
        }
    }
}
