package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerContacts;


public interface ContactRepository {

    CustomerContacts getContact(Integer contactID);

    Boolean addContact(CustomerContacts contact);

    Boolean updateContact(CustomerContacts contact);

    Boolean changeStatus(Integer contactID, Boolean status);
}
