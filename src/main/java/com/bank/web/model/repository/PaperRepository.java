package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerPapers;

public interface PaperRepository {

    void addPaper(CustomerPapers paper);

    void updatePaper(CustomerPapers paper);

    void deletePaper(CustomerPapers paper);
}
