package com.bank.web.model.repository;

import com.bank.web.model.entity.*;
import java.util.List;
import java.util.Map;

public interface CustomerRepository {

    List<CustomerInfo> customersList(Boolean isActive);

    void changeStatus (CustomerInfo customerInfo, Boolean status);

    List<Accounts> getAccounts(CustomerInfo customerInfo);

    List<CustomerAddress> getAddresses(CustomerInfo customerInfo);

    List<CustomerContacts> getContacts(CustomerInfo customerInfo);

    List<CustomerPapers> getPapers(CustomerInfo customerInfo);

    void addCustomer(CustomerInfo customerInfo);

    void updateCustomer(CustomerInfo customerInfo);
}
