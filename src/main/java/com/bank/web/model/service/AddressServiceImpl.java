package com.bank.web.model.service;

import com.bank.web.model.entity.CustomerAddress;
import com.bank.web.model.entity.CustomerInfo;
import com.bank.web.model.repository.AddressRepository;
import com.bank.web.model.repository.CustomerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service("addressService")
public class AddressServiceImpl implements AddressService {
    public static final Logger logger = Logger.getLogger(AddressServiceImpl.class);

    AddressRepository addressRepository;
    CustomerRepository customerRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository,
                              CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Map<String, Object> getAddressData(Integer addressID) {
        Map<String, Object> objMap = new HashMap<>();
        CustomerAddress address = addressRepository.getAddress(addressID);
        CustomerInfo customer = customerRepository.customerDetails(address.getCustomer().getCustomerID());

        objMap.put("address", address);
        objMap.put("customer", customer);

        return objMap;
    }
}
