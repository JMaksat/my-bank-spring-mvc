package com.bank.web.controller;

import com.bank.web.model.entity.CustomerContacts;
import com.bank.web.model.entity.CustomerInfo;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.repository.ContactRepository;
import com.bank.web.model.repository.CustomerRepository;
import com.bank.web.model.repository.DirectoryRepository;
import com.bank.web.model.service.ContactService;
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
public class ContactController {
    public static final Logger logger = Logger.getLogger(ContactController.class);

    private ContactRepository contactRepository;
    private ContactService contactService;
    private DirectoryRepository directoryRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public ContactController(ContactRepository contactRepository,
                             ContactService contactService,
                             DirectoryRepository directoryRepository,
                             CustomerRepository customerRepository) {
        this.contactRepository = contactRepository;
        this.contactService = contactService;
        this.directoryRepository = directoryRepository;
        this.customerRepository = customerRepository;
    }

    @RequestMapping(path = "/contact/{contactID}", method = RequestMethod.GET)
    @Secured({"ROLE_OPERATOR"})
    public ModelAndView getContactData(@PathVariable Integer contactID, ModelMap map) {
        Map<String, Object> objMap = contactService.getContactData(contactID);

        if (objMap.get("customer") != null) {
            map.put("contact", objMap.get("contact"));
            map.put("customer", objMap.get("customer"));
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

        if (params.get("id") != null &&
                params.get("value") != null &&
                params.get("type") != null &&
                params.get("customerID") != null) {

            contact.setContactID(Integer.valueOf(params.get("id")));
            contact.setValue(params.get("value"));
            contact.setDateCreated(LocalDate.now());
            contact.setDateModified(LocalDate.now());
            contact.setIsActive(1);
            contact.setUserID(authentication.getName());
            contact.setContactType(Integer.valueOf(params.get("type")));
            contact.setCustomer(customerRepository.customerDetails(Integer.valueOf(params.get("customerID"))));

            if (Integer.valueOf(params.get("id")) >= 0) {
                contactRepository.updateContact(contact);
            } else {
                contactRepository.addContact(contact);
            }

            return "1";
        }

        return "0";
    }

    @RequestMapping(path = "/contact/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_OPERATOR"})
    public String changeContactState(@RequestParam Map<String, String> params) {

        if (params.get("contactID") != null) {
            contactRepository.changeStatus(Integer.valueOf(params.get("contactID")),
                    params.get("status").equals("1"));

            return "1";
        }

        return "0";
    }
}
