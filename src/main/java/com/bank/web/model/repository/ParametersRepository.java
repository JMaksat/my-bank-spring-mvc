package com.bank.web.model.repository;

import com.bank.web.model.entity.BankParameters;

import java.util.List;

public interface ParametersRepository {

    List<BankParameters> getParameters();

    BankParameters getParamTransAccount();
}
