package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerPapers;

import java.util.List;

public interface PaperRepository {

    List<CustomerPapers> getPaper(Integer paperID);

    void addPaper(CustomerPapers paper);

    void updatePaper(CustomerPapers paper);

    void changeStatus(Integer paperID, Boolean status);
}
