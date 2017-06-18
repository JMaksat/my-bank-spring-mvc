package com.bank.web.model.repository;

import com.bank.web.model.entity.*;
import java.util.List;

public interface CustomerRepository {

    List<CustomerInfo> customersList(Boolean isActive);

    CustomerInfo customerDetails(Integer customerID);

    Boolean changeStatus (Integer customerID, Boolean status);

    List<Accounts> getAccounts(CustomerInfo customer);

    List<CustomerAddress> getAddresses(CustomerInfo customerID);

    List<CustomerContacts> getContacts(CustomerInfo customerID);

    List<CustomerPapers> getPapers(CustomerInfo customerID);

    Boolean addCustomer(CustomerInfo customerInfo);

    Boolean updateCustomer(CustomerInfo customerInfo);
}
