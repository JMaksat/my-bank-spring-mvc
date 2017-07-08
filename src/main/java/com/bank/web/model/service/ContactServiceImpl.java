package com.bank.web.model.service;

import com.bank.web.model.entity.CustomerContacts;
import com.bank.web.model.entity.CustomerInfo;
import com.bank.web.model.repository.ContactRepository;
import com.bank.web.model.repository.CustomerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service("contactService")
public class ContactServiceImpl implements ContactService {
    public static final Logger logger = Logger.getLogger(ContactServiceImpl.class);

    ContactRepository contactRepository;
    CustomerRepository customerRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository,
                              CustomerRepository customerRepository) {
        this.contactRepository = contactRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Map<String, Object> getContactData(Integer contactID) {
        Map<String, Object> objMap = new HashMap<>();
        CustomerContacts contact = contactRepository.getContact(contactID);
        CustomerInfo customer = customerRepository.customerDetails(contact.getCustomer().getCustomerID());

        objMap.put("contact", contact);
        objMap.put("customer", customer);

        return objMap;
    }
}
