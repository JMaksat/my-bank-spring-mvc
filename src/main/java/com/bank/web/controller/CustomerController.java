package com.bank.web.controller;

import com.bank.web.model.entity.*;
import com.bank.web.model.repository.CustomerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController {
    public static final Logger logger = Logger.getLogger(CustomerController.class);

    private CustomerRepository customerRepository;

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
        List<CustomerInfo> customer = customerRepository.customerDetails(customerID);
        List<Accounts> accounts = customerRepository.getAccounts(customerID);
        List<CustomerAddress> addresses = customerRepository.getAddresses(customerID);
        List<CustomerContacts> contacts = customerRepository.getContacts(customerID);
        List<CustomerPapers> papers = customerRepository.getPapers(customerID);

        CustomerInfo details = (customer != null && customer.size() > 0) ? customer.iterator().next() : null;

        map.put("customer", details);
        map.put("accounts", accounts);
        map.put("addresses", addresses);
        map.put("contacts", contacts);
        map.put("papers", papers);
        map.put("pageName", "Customer details");
        map.put("leftMenu", "customers");

        return new ModelAndView("customerDetails");
    }

    @RequestMapping(path = "/customers/new/{customerID}", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
    public ModelAndView newCustomer(@PathVariable Integer customerID, ModelMap map) {

        map.put("pageName", "New customer");
        map.put("leftMenu", "customers");
        map.put("templateType", "insert");

        return new ModelAndView("customerEntry");
    }

    @RequestMapping(path = "/customers/update/{customerID}", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
    public ModelAndView updateCustomer(@PathVariable Integer customerID, ModelMap map) {

        map.put("pageName", "Update customer entry");
        map.put("leftMenu", "customers");
        map.put("templateType", "update");

        return new ModelAndView("customerEntry");
    }
}
