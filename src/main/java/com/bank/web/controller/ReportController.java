package com.bank.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {
    public static final Logger logger = Logger.getLogger(ReportController.class);

    /*@Autowired
    public ReportController() {}*/

    @RequestMapping(path = "/reports", method = RequestMethod.GET)
    @Secured({"ROLE_ACCOUNTANT", "ROLE_OPERATOR"})
    public ModelAndView getReports(ModelMap map) {

        map.put("pageName", "Reports");
        map.put("leftMenu", "reports");

        return new ModelAndView("reports");
    }
}
