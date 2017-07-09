package com.bank.web.controller;

import com.bank.web.model.entity.CustomerPapers;
import com.bank.web.model.entity.Directory;
import com.bank.web.model.repository.CustomerRepository;
import com.bank.web.model.repository.DirectoryRepository;
import com.bank.web.model.repository.PaperRepository;
import com.bank.web.model.service.PaperService;
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
public class PaperController {
    public static final Logger logger = Logger.getLogger(PaperController.class);

    private PaperRepository paperRepository;
    private PaperService paperService;
    private DirectoryRepository directoryRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public PaperController(PaperRepository paperRepository,
                           PaperService paperService,
                           DirectoryRepository directoryRepository,
                           CustomerRepository customerRepository) {
        this.paperRepository = paperRepository;
        this.paperService = paperService;
        this.directoryRepository = directoryRepository;
        this.customerRepository = customerRepository;
    }

    @RequestMapping(path = "/paper/{paperID}", method = RequestMethod.GET)
    @Secured({"ROLE_OPERATOR"})
    public ModelAndView getPaperData(@PathVariable Integer paperID, ModelMap map) {
        Map<String, Object> objMap = paperService.getPaperData(paperID);

        if (objMap.get("customer") != null) {
            map.put("paper", objMap.get("paper"));
            map.put("customer", objMap.get("customer"));
            map.put("pageName", "Papers");
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
            map.put("pageName", "Update papers entry");
        } else {
            map.put("pageName", "New papers");
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
        String retVal = "0";

        if (params.get("id") != null &&
                params.get("value") != null &&
                params.get("type") != null &&
                params.get("customerID") != null) {

            paper.setPaperID(Integer.valueOf(params.get("id")));
            paper.setValue(params.get("value"));
            paper.setDateCreated(LocalDate.now());
            paper.setDateModified(LocalDate.now());
            paper.setIsActive(1);
            paper.setUserID(authentication.getName());
            paper.setPaperType(Integer.valueOf(params.get("type")));
            paper.setCustomer(customerRepository.customerDetails(Integer.valueOf(params.get("customerID"))));

            if (Integer.valueOf(params.get("id")) >= 0) {
                retVal = paperRepository.updatePaper(paper) ? "1" : "0";
            } else {
                retVal = paperRepository.addPaper(paper) ? "1" : "0";
            }
        }

        return retVal;
    }

    @RequestMapping(path = "/paper/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured({"ROLE_OPERATOR"})
    public String changePaperState(@RequestParam Map<String, String> params) {
        String retVal = "0";

        if (params.get("paperID") != null) {
            retVal = paperRepository.changeStatus(Integer.valueOf(params.get("paperID")),
                    params.get("status").equals("1")) ? "1" : "0";
        }

        return retVal;
    }
}
