package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerAddress;


public interface AddressRepository {

    CustomerAddress getAddress(Integer addressID);

    Boolean addAddress(CustomerAddress address);

    Boolean updateAddress(CustomerAddress address);

    Boolean changeStatus(Integer addressID, Boolean status);
}
