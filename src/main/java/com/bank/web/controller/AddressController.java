package com.bank.web.controller;

import com.bank.web.model.entity.CustomerAddress;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.repository.AddressRepository;
import com.bank.web.model.repository.DirectoryRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
public class AddressController {
    public static final Logger logger = Logger.getLogger(AddressController.class);

    private AddressRepository addressRepository;
    private DirectoryRepository directoryRepository;

    @Autowired
    public AddressController(AddressRepository addressRepository, DirectoryRepository directoryRepository) {
        this.addressRepository = addressRepository;
        this.directoryRepository = directoryRepository;
    }

    @RequestMapping(path = "/address/{addressID}", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
    public ModelAndView getAddressData(@PathVariable Integer addressID, ModelMap map) {
        CustomerAddress address = addressRepository.getAddress(addressID);

        if (address != null) {
            map.put("address", address);
            map.put("pageName", "Address");
            map.put("leftMenu", "customers");
        }

        return new ModelAndView("address");
    }

    @RequestMapping(value = "/address/edit/{customerID}/{addressID}", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
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
    //@Secured({"ROLE_USER"})
    public String newAddress(@RequestParam Map<String, String> params) {
        CustomerAddress address = new CustomerAddress();

        try {
            address.setAddressID(Integer.valueOf(params.get("id")));
            address.setValue(params.get("value"));
            address.setDateCreated(new java.util.Date());
            address.setDateModified(new java.util.Date());
            address.setIsActive(1);
        /* Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null)
                username = authentication.getName(); */
            address.setUserID("temp_user");
            address.setAddressType(params.get("type"));
            address.setCustomerID(Integer.valueOf(params.get("customerID")));

            if (Integer.valueOf(params.get("id")) >= 0) {
                addressRepository.updateAddress(address);
            } else {
                addressRepository.addAddress(address);
            }
        } catch (Exception e) {
            logger.error("Exception: ", e);
        }

        return "1";
    }

    @RequestMapping(path = "/address/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    //@Secured({"ROLE_USER"})
    public String changeAddressState(@RequestParam Map<String, String> params) {
        try {

            if (params.get("status").equals("1")) {
                addressRepository.changeStatus(Integer.valueOf(params.get("addressID")), false);
            } else {
                addressRepository.changeStatus(Integer.valueOf(params.get("addressID")), true);
            }

        } catch (Exception e) {
            logger.error("Exception: ", e);
        }

        return "1";
    }

}
