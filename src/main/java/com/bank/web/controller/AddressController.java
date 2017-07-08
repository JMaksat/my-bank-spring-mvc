package com.bank.web.controller;

import com.bank.web.model.entity.CustomerAddress;
import com.bank.web.model.entity.CustomerInfo;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.repository.AddressRepository;
import com.bank.web.model.repository.CustomerRepository;
import com.bank.web.model.repository.DirectoryRepository;
import com.bank.web.model.service.AddressService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class AddressController {
    public static final Logger logger = Logger.getLogger(AddressController.class);

    private AddressRepository addressRepository;
    private AddressService addressService;
    private DirectoryRepository directoryRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public AddressController(AddressRepository addressRepository,
                             AddressService addressService,
                             DirectoryRepository directoryRepository,
                             CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.addressService = addressService;
        this.directoryRepository = directoryRepository;
        this.customerRepository = customerRepository;
    }

    @RequestMapping(path = "/address/{addressID}", method = RequestMethod.GET)
    @Secured({"ROLE_OPERATOR"})
    public ModelAndView getAddressData(@PathVariable Integer addressID, ModelMap map) {
        Map<String, Object> objMap = addressService.getAddressData(addressID);

        if (objMap.get("customer") != null) {
            map.put("address", objMap.get("address"));
            map.put("customer", objMap.get("customer"));
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

        if (params.get("id") != null &&
                params.get("value") != null &&
                params.get("type") != null &&
                params.get("customerID") != null) {

            address.setAddressID(Integer.valueOf(params.get("id")));
            address.setValue(params.get("value"));
            address.setDateCreated(LocalDate.now());
            address.setDateModified(LocalDate.now());
            address.setIsActive(1);
            address.setUserID(authentication.getName());
            address.setAddressType(Integer.valueOf(params.get("type")));
            address.setCustomer(customerRepository.customerDetails(Integer.valueOf(params.get("customerID"))));

            if (Integer.valueOf(params.get("id")) >= 0) {
                addressRepository.updateAddress(address);
            } else {
                addressRepository.addAddress(address);
            }

            return "1";
        }

        return "0";
    }

    @RequestMapping(path = "/address/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_OPERATOR"})
    public String changeAddressState(@RequestParam Map<String, String> params) {

        if (params.get("addressID") != null) {
            addressRepository.changeStatus(Integer.valueOf(params.get("addressID")),
                    params.get("status").equals("1"));
            return "1";
        }

        return "0";
    }

}
