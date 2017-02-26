package com.bank.web.controller;

import com.bank.web.model.entity.*;
import com.bank.web.model.repository.CustomerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CustomerController {
    public static final Logger logger = Logger.getLogger(CustomerController.class);

    private CustomerRepository customerRepository;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping(path = "/customers", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
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
    //@Secured({"ROLE_USER"})
    public ModelAndView getCustomerDetails(@PathVariable Integer customerID, ModelMap map) {
        List<Accounts> accounts = customerRepository.getAccounts(customerID);
        List<CustomerAddress> addresses = customerRepository.getAddresses(customerID);
        List<CustomerContacts> contacts = customerRepository.getContacts(customerID);
        List<CustomerPapers> papers = customerRepository.getPapers(customerID);

        CustomerInfo customer = customerRepository.customerDetails(customerID);

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
    //@Secured({"ROLE_USER"})
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
    //@Secured({"ROLE_USER"})
    public String newCustomer(@RequestParam Map<String, String> params) {
        CustomerInfo customer = new CustomerInfo();

        try {
            customer.setCustomerID(Integer.valueOf(params.get("customerID")));
            customer.setFirstName(params.get("firstName"));
            customer.setLastName(params.get("lastName"));
            customer.setMiddleName(params.get("middleName"));
            customer.setBirthDate(formatter.parse(params.get("birthDate")));
            customer.setDateModified(new java.util.Date());
            customer.setIsActive(1);
        /* Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null)
                username = authentication.getName(); */
            customer.setUserID("temp_user");
            customer.setDateCreated(new java.util.Date());

            if (Integer.valueOf(params.get("customerID")) >= 0) {
                customerRepository.updateCustomer(customer);
            } else {
                customerRepository.addCustomer(customer);
            }
        } catch (Exception e) {
            logger.error("Exception: ", e);
        }

        return "1";
    }

    @RequestMapping(path = "/customers/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    //@Secured({"ROLE_USER"})
    public String changeCustomerState(@RequestParam Map<String, String> params) {
        try {

            if (params.get("status").equals("1")) {
                customerRepository.changeStatus(Integer.valueOf(params.get("customerID")), false);
            } else {
                customerRepository.changeStatus(Integer.valueOf(params.get("customerID")), true);
            }

        } catch (Exception e) {
            logger.error("Exception: ", e);
        }

        return "1";
    }

}
