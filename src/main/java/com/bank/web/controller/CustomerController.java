package com.bank.web.controller;

import com.bank.web.model.entity.*;
import com.bank.web.model.repository.CustomerRepository;
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
import java.util.*;

@Controller
public class CustomerController {
    public static final Logger logger = Logger.getLogger(CustomerController.class);

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping(path = "/customers", method = RequestMethod.GET)
    @Secured({"ROLE_ACCOUNTANT", "ROLE_OPERATOR"})
    public ModelAndView getCustomerData(ModelMap map) {
        List<CustomerInfo> customers = customerRepository.customersList(null);

        if (customers != null) {
            map.put("customers", customers);
            map.put("pageName", "Customers list");
            map.put("leftMenu", "customers");
        }

        return new ModelAndView("customers");
    }

    @RequestMapping(path = "/customerDetails/{customerID}", method = RequestMethod.GET)
    @Secured({"ROLE_ACCOUNTANT", "ROLE_OPERATOR"})
    public ModelAndView getCustomerDetails(@PathVariable Integer customerID, ModelMap map) {
        CustomerInfo customer = customerRepository.customerDetails(customerID);
        List<Accounts> accounts = customerRepository.getAccounts(customer);
        List<CustomerAddress> addresses = customerRepository.getAddresses(customer);
        List<CustomerContacts> contacts = customerRepository.getContacts(customer);
        List<CustomerPapers> papers = customerRepository.getPapers(customer);

        map.put("customer", customer);
        map.put("accounts", accounts);
        map.put("addresses", addresses);
        map.put("contacts", contacts);
        map.put("papers", papers);
        map.put("pageName", "Customer details");
        map.put("leftMenu", "customers");

        return new ModelAndView("customerDetails");
    }

    @RequestMapping(path = "/customers/edit/{customerID}", method = RequestMethod.GET)
    @Secured({"ROLE_ACCOUNTANT", "ROLE_OPERATOR"})
    public ModelAndView updateCustomer(@PathVariable Integer customerID, ModelMap map) {

        if (customerID >= 0) {
            CustomerInfo customer = customerRepository.customerDetails(customerID);

            map.put("customer", customer);
            map.put("pageName", "Update customer entry");
        } else {
            map.put("pageName", "New customer");
        }

        map.put("leftMenu", "customers");
        map.put("customerID", customerID);

        return new ModelAndView("customerEntry");
    }

    @RequestMapping(path = "/customers/save", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_ACCOUNTANT", "ROLE_OPERATOR"})
    public String newCustomer(@RequestParam Map<String, String> params) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerInfo customer = new CustomerInfo();
        String retVal = "0";

        if (params.get("customerID") != null &&
                params.get("firstName") != null &&
                params.get("firstName") != null &&
                params.get("birthDate") != null) {

                customer.setCustomerID(Integer.valueOf(params.get("customerID")));
                customer.setFirstName(params.get("firstName"));
                customer.setLastName(params.get("lastName"));
                customer.setMiddleName(params.get("middleName"));
                customer.setBirthDate(LocalDate.parse(params.get("birthDate")));
                customer.setDateModified(LocalDate.now());
                customer.setIsActive(1);
                customer.setUserID(authentication.getName());
                customer.setDateCreated(LocalDate.now());

            if (Integer.valueOf(params.get("customerID")) >= 0) {
                retVal = customerRepository.updateCustomer(customer) ? "1" : "0";
            } else {
                retVal = customerRepository.addCustomer(customer) ? "1" : "0";
            }
        }

        return retVal;
    }

    @RequestMapping(path = "/customers/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_ACCOUNTANT", "ROLE_OPERATOR"})
    public String changeCustomerState(@RequestParam Map<String, String> params) {
        String retVal = "0";

        if (params.get("customerID") != null) {
            retVal = customerRepository.changeStatus(Integer.valueOf(params.get("customerID")),
                    params.get("status").equals("1")) ? "1" : "0";
        }

        return retVal;
    }

}
