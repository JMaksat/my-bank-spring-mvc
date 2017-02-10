package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerAddress;

public interface AddressRepository {

    void addAddress(CustomerAddress address);

    void updateAddress(CustomerAddress address);

    void deleteAddress(CustomerAddress address);
}
