package com.bank.web.model.repository;

import com.bank.web.model.entity.BankParameters;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository("parametersRepository")
@Transactional
public class ParametersRepositoryImpl implements ParametersRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(ParametersRepositoryImpl.class);

    public ParametersRepositoryImpl() {}

    public ParametersRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<BankParameters> getParameters() {
        List<BankParameters> result;

        result = sessionFactory.getCurrentSession()
                .createQuery(" from BankParameters where :now between activeFrom and activeTo ")
                .setParameter("now", LocalDate.now()).list();

        logger.info("getParameters() records found = " + result.size());

        return result;
    }

    @Override
    public BankParameters getParamTransAccount() {
        BankParameters result;

        List<BankParameters> param = sessionFactory.getCurrentSession()
                .createQuery(" from BankParameters where parameterName = :parameter_name ")
                .setParameter("parameter_name", "TRANSFER_ACCOUNT").list();

        result = param.get(0);
        logger.info("getParamTransAccount() records found = " + param.size());

        return result;
    }
}
