package com.bank.web.controller;

import com.bank.web.model.entity.CustomerInfo;
import com.bank.web.model.entity.CustomerPapers;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.repository.CustomerRepository;
import com.bank.web.model.repository.DirectoryRepository;
import com.bank.web.model.repository.PaperRepository;
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
public class PaperController {
    public static final Logger logger = Logger.getLogger(PaperController.class);

    private PaperRepository paperRepository;
    private DirectoryRepository directoryRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public PaperController(PaperRepository paperRepository,
                           DirectoryRepository directoryRepository,
                           CustomerRepository customerRepository) {
        this.paperRepository = paperRepository;
        this.directoryRepository = directoryRepository;
        this.customerRepository = customerRepository;
    }

    @RequestMapping(path = "/paper/{paperID}", method = RequestMethod.GET)
    @Secured({"ROLE_OPERATOR"})
    public ModelAndView getPaperData(@PathVariable Integer paperID, ModelMap map) {
        CustomerPapers paper = paperRepository.getPaper(paperID);

        if (paper != null) {
            CustomerInfo customer = customerRepository.customerDetails(paper.getCustomerID());

            map.put("paper", paper);
            map.put("customer", customer);
            map.put("pageName", "Paper");
            map.put("leftMenu", "customers");
        }

        return new ModelAndView("paper");
    }

    @RequestMapping(value = "/paper/edit/{customerID}/{paperID}", method = RequestMethod.GET)
    @Secured({"ROLE_OPERATOR"})
    public ModelAndView updatePaper(@PathVariable("customerID") Integer customerID, @PathVariable("paperID") Integer paperID, ModelMap map) {
        List<Directory> types = directoryRepository.getPaperTypes();

        if (paperID >= 0) {
            CustomerPapers paper = paperRepository.getPaper(paperID);

            map.put("paper", paper);
            map.put("pageName", "Update paper entry");
        } else {
            map.put("pageName", "New paper");
        }

        map.put("types", types);
        map.put("leftMenu", "customers");
        map.put("paperID", paperID);
        map.put("customerID", customerID);

        return new ModelAndView("paperEntry");
    }

    @RequestMapping(path = "/paper/save", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_OPERATOR"})
    public String newPaper(@RequestParam Map<String, String> params) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerPapers paper = new CustomerPapers();

        paper.setPaperID(Integer.valueOf(params.get("id")));
        paper.setValue(params.get("value"));
        paper.setDateCreated(new java.util.Date());
        paper.setDateModified(new java.util.Date());
        paper.setIsActive(1);
        paper.setUserID(authentication.getName());
        paper.setPaperType(params.get("type"));
        paper.setCustomerID(Integer.valueOf(params.get("customerID")));

        if (Integer.valueOf(params.get("id")) >= 0) {
            paperRepository.updatePaper(paper);
        } else {
            paperRepository.addPaper(paper);
        }

        return "1";
    }

    @RequestMapping(path = "/paper/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_OPERATOR"})
    public String changePaperState(@RequestParam Map<String, String> params) {

        if (params.get("status").equals("1")) {
            paperRepository.changeStatus(Integer.valueOf(params.get("paperID")), false);
        } else {
            paperRepository.changeStatus(Integer.valueOf(params.get("paperID")), true);
        }

        return "1";
    }
}
