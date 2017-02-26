package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerContacts;

import java.util.List;

public interface ContactRepository {

    CustomerContacts getContact(Integer contactID);

    void addContact(CustomerContacts contact);

    void updateContact(CustomerContacts contact);

    void changeStatus(Integer contactID, Boolean status);
}
