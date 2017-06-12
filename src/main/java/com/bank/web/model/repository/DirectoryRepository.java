package com.bank.web.model.repository;

import com.bank.web.model.entity.Directory;

import java.util.List;
import java.util.function.BooleanSupplier;

public interface DirectoryRepository {

    List<Directory> directoryList();

    List<Directory> getAccountTypes();

    List<Directory> getTransactionTypes();

    List<Directory> getAddressTypes();

    List<Directory> getContactTypes();

    List<Directory> getPaperTypes();

    Boolean checkUnique(Integer dirID);

    Boolean addEntry(Directory directory);

    Boolean updateEntry(Directory directory);

    Boolean changeStatus(Integer dirID, Boolean status);
}
