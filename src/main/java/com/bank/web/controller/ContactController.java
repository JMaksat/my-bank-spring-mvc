package com.bank.web.controller;

import com.bank.web.model.entity.CustomerContacts;
import com.bank.web.model.entity.CustomerInfo;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.repository.ContactRepository;
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
public class ContactController {
    public static final Logger logger = Logger.getLogger(ContactController.class);

    private ContactRepository contactRepository;
    private DirectoryRepository directoryRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public ContactController(ContactRepository contactRepository,
                             DirectoryRepository directoryRepository,
                             CustomerRepository customerRepository) {
        this.contactRepository = contactRepository;
        this.directoryRepository = directoryRepository;
        this.customerRepository = customerRepository;
    }

    @RequestMapping(path = "/contact/{contactID}", method = RequestMethod.GET)
    @Secured({"ROLE_OPERATOR"})
    public ModelAndView getContactData(@PathVariable Integer contactID, ModelMap map) {
        CustomerContacts contact = contactRepository.getContact(contactID);

        if (contact != null) {
            CustomerInfo customer = customerRepository.customerDetails(contact.getCustomerID());

            map.put("contact", contact);
            map.put("customer", customer);
            map.put("pageName", "Contact");
            map.put("leftMenu", "customers");
        }

        return new ModelAndView("contact");
    }

    @RequestMapping(value = "/contact/edit/{customerID}/{contactID}", method = RequestMethod.GET)
    @Secured({"ROLE_OPERATOR"})
    public ModelAndView updateContact(@PathVariable("customerID") Integer customerID, @PathVariable("contactID") Integer contactID, ModelMap map) {
        List<Directory> types = directoryRepository.getContactTypes();

        if (contactID >= 0) {
            CustomerContacts contact = contactRepository.getContact(contactID);

            map.put("contact", contact);
            map.put("pageName", "Update contact entry");
        } else {
            map.put("pageName", "New contact");
        }

        map.put("types", types);
        map.put("leftMenu", "customers");
        map.put("contactID", contactID);
        map.put("customerID", customerID);

        return new ModelAndView("contactEntry");
    }

    @RequestMapping(path = "/contact/save", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_OPERATOR"})
    public String newContact(@RequestParam Map<String, String> params) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerContacts contact = new CustomerContacts();

        contact.setContactID(Integer.valueOf(params.get("id")));
        contact.setValue(params.get("value"));
        contact.setDateCreated(new java.util.Date());
        contact.setDateModified(new java.util.Date());
        contact.setIsActive(1);
        contact.setUserID(authentication.getName());
        contact.setContactType(params.get("type"));
        contact.setCustomerID(Integer.valueOf(params.get("customerID")));

        if (Integer.valueOf(params.get("id")) >= 0) {
            contactRepository.updateContact(contact);
        } else {
            contactRepository.addContact(contact);
        }

        return "1";
    }

    @RequestMapping(path = "/contact/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_OPERATOR"})
    public String changeContactState(@RequestParam Map<String, String> params) {

        if (params.get("status").equals("1")) {
            contactRepository.changeStatus(Integer.valueOf(params.get("contactID")), false);
        } else {
            contactRepository.changeStatus(Integer.valueOf(params.get("contactID")), true);
        }

        return "1";
    }
}
