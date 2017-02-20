package com.bank.web.controller;

import com.bank.web.model.entity.CustomerContacts;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.repository.ContactRepository;
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
public class ContactController {
    public static final Logger logger = Logger.getLogger(ContactController.class);

    private ContactRepository contactRepository;
    private DirectoryRepository directoryRepository;

    @Autowired
    public ContactController(ContactRepository contactRepository, DirectoryRepository directoryRepository) {
        this.contactRepository = contactRepository;
        this.directoryRepository = directoryRepository;
    }

    @RequestMapping(path = "/contact/{contactID}", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
    public ModelAndView getContactData(@PathVariable Integer contactID, ModelMap map) {
        List<CustomerContacts> contacts = contactRepository.getContact(contactID);

        CustomerContacts contact = (contacts != null && contacts.size() >0) ? contacts.iterator().next() : null;

        if (contact != null) {
            map.put("contact", contact);
            map.put("pageName", "Contact");
            map.put("leftMenu", "customers");
        }

        return new ModelAndView("contact");
    }

    @RequestMapping(value = "/contact/edit/{customerID}/{contactID}", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
    public ModelAndView updateContact(@PathVariable("customerID") Integer customerID, @PathVariable("contactID") Integer contactID, ModelMap map) {
        List<Directory> types = directoryRepository.getContactTypes();

        if (contactID >= 0) {
            List<CustomerContacts> contacts = contactRepository.getContact(contactID);
            CustomerContacts contact = (contacts != null && contacts.size() > 0) ? contacts.iterator().next() : null;

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
    //@Secured({"ROLE_USER"})
    public String newContact(@RequestParam Map<String, String> params) {
        CustomerContacts contact = new CustomerContacts();

        try {
            contact.setContactID(Integer.valueOf(params.get("id")));
            contact.setValue(params.get("value"));
            contact.setDateCreated(new java.util.Date());
            contact.setDateModified(new java.util.Date());
            contact.setIsActive(1);
        /* Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null)
                username = authentication.getName(); */
            contact.setUserID("temp_user");
            contact.setContactType(params.get("type"));
            contact.setCustomerID(Integer.valueOf(params.get("customerID")));

            if (Integer.valueOf(params.get("id")) >= 0) {
                contactRepository.updateContact(contact);
            } else {
                contactRepository.addContact(contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "1";
    }

    @RequestMapping(path = "/contact/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    //@Secured({"ROLE_USER"})
    public String changeContactState(@RequestParam Map<String, String> params) {
        try {

            if (params.get("status").equals("1")) {
                contactRepository.changeStatus(Integer.valueOf(params.get("contactID")), false);
            } else {
                contactRepository.changeStatus(Integer.valueOf(params.get("contactID")), true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "1";
    }
}
