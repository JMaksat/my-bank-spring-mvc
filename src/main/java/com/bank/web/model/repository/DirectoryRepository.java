package com.bank.web.model.repository;

import com.bank.web.model.entity.Directory;

import java.util.List;

public interface DirectoryRepository {

    List<Directory> getAccountTypes();

    List<Directory> getTransactionTypes();

    List<Directory> getAddressTypes();

    List<Directory> getContactTypes();

    List<Directory> getPaperTypes();

    void addEntry(Directory directory);
}
