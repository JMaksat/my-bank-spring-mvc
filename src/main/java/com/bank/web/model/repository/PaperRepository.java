package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerPapers;


public interface PaperRepository {

    CustomerPapers getPaper(Integer paperID);

    Boolean addPaper(CustomerPapers paper);

    Boolean updatePaper(CustomerPapers paper);

    Boolean changeStatus(Integer paperID, Boolean status);
}
