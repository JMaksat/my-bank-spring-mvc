package com.bank.web.model.repository;

import com.bank.web.model.entity.*;
import java.util.List;
import java.util.Map;

public interface CustomerRepository {

    List<CustomerInfo> customersList(Boolean isActive);

    List<CustomerInfo> customerDetails(Integer customerID);

    void changeStatus (Integer customerID, Boolean status);

    List<Accounts> getAccounts(Integer customerID);

    List<CustomerAddress> getAddresses(Integer customerID);

    List<CustomerContacts> getContacts(Integer customerID);

    List<CustomerPapers> getPapers(Integer customerID);

    void addCustomer(CustomerInfo customerInfo);

    void updateCustomer(CustomerInfo customerInfo);
}
