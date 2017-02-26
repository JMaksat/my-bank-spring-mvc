package com.bank.web.model.repository;

import com.bank.web.model.entity.CustomerAddress;

import java.util.List;

public interface AddressRepository {

    CustomerAddress getAddress(Integer addressID);

    void addAddress(CustomerAddress address);

    void updateAddress(CustomerAddress address);

    void changeStatus(Integer addressID, Boolean status);
}
