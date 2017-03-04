package com.bank.web.controller;

import com.bank.web.model.entity.CustomerAddress;
import com.bank.web.model.entity.CustomerInfo;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.repository.AddressRepository;
import com.bank.web.model.repository.CustomerRepository;
import com.bank.web.model.repository.DirectoryRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class AddressController {
    public static final Logger logger = Logger.getLogger(AddressController.class);

    private AddressRepository addressRepository;
    private DirectoryRepository directoryRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public AddressController(AddressRepository addressRepository,
                             DirectoryRepository directoryRepository,
                             CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.directoryRepository = directoryRepository;
        this.customerRepository = customerRepository;
    }

    @RequestMapping(path = "/address/{addressID}", method = RequestMethod.GET)
    @Secured({"ROLE_OPERATOR"})
    public ModelAndView getAddressData(@PathVariable Integer addressID, ModelMap map) {
        CustomerAddress address = addressRepository.getAddress(addressID);

        if (address != null) {
            CustomerInfo customer = customerRepository.customerDetails(address.getCustomerID());

            map.put("address", address);
            map.put("customer", customer);
            map.put("pageName", "Address");
            map.put("leftMenu", "customers");
        }

        return new ModelAndView("address");
    }

    @RequestMapping(value = "/address/edit/{customerID}/{addressID}", method = RequestMethod.GET)
    @Secured({"ROLE_OPERATOR"})
    public ModelAndView updateAddress(@PathVariable("customerID") Integer customerID, @PathVariable("addressID") Integer addressID, ModelMap map) {
        List<Directory> types = directoryRepository.getAddressTypes();

        if (addressID >= 0) {
            CustomerAddress address = addressRepository.getAddress(addressID);

            map.put("address", address);
            map.put("pageName", "Update address entry");
        } else {
            map.put("pageName", "New address");
        }

        map.put("types", types);
        map.put("leftMenu", "customers");
        map.put("addressID", addressID);
        map.put("customerID", customerID);

        return new ModelAndView("addressEntry");
    }

    @RequestMapping(path = "/address/save", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_OPERATOR"})
    public String newAddress(@RequestParam Map<String, String> params) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerAddress address = new CustomerAddress();

        address.setAddressID(Integer.valueOf(params.get("id")));
        address.setValue(params.get("value"));
        address.setDateCreated(new java.util.Date());
        address.setDateModified(new java.util.Date());
        address.setIsActive(1);
        address.setUserID(authentication.getName());
        address.setAddressType(params.get("type"));
        address.setCustomerID(Integer.valueOf(params.get("customerID")));

        if (Integer.valueOf(params.get("id")) >= 0) {
            addressRepository.updateAddress(address);
        } else {
            addressRepository.addAddress(address);
        }

        return "1";
    }

    @RequestMapping(path = "/address/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_OPERATOR"})
    public String changeAddressState(@RequestParam Map<String, String> params) {

        if (params.get("status").equals("1")) {
            addressRepository.changeStatus(Integer.valueOf(params.get("addressID")), false);
        } else {
            addressRepository.changeStatus(Integer.valueOf(params.get("addressID")), true);
        }

        return "1";
    }

}
