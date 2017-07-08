package com.bank.web.model.service;

import com.bank.web.model.entity.CustomerInfo;
import com.bank.web.model.entity.CustomerPapers;
import com.bank.web.model.repository.CustomerRepository;
import com.bank.web.model.repository.PaperRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service("paperService")
public class PaperServiceImpl implements PaperService {
    public static final Logger logger = Logger.getLogger(PaperServiceImpl.class);

    PaperRepository paperRepository;
    CustomerRepository customerRepository;

    @Autowired
    public PaperServiceImpl(PaperRepository paperRepository,
                              CustomerRepository customerRepository) {
        this.paperRepository = paperRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Map<String, Object> getPaperData(Integer paperID) {
        Map<String, Object> objMap = new HashMap<>();
        CustomerPapers paper = paperRepository.getPaper(paperID);
        CustomerInfo customer = customerRepository.customerDetails(paper.getCustomer().getCustomerID());

        objMap.put("paper", paper);
        objMap.put("customer", customer);

        return objMap;
    }
}
