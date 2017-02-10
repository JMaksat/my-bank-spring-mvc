package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerContacts;

public interface ContactRepository {

    void addContact(CustomerContacts contact);

    void updateContact(CustomerContacts contact);

    void deleteContact(CustomerContacts contact);
}
