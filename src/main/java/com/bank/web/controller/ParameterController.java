package com.bank.web.controller;

import com.bank.web.model.entity.BankParameters;
import com.bank.web.model.repository.ParametersRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ParameterController {
    public static final Logger logger = Logger.getLogger(ParameterController.class);

    private ParametersRepository parametersRepository;

    @Autowired
    public ParameterController(ParametersRepository parametersRepository) {
        this.parametersRepository = parametersRepository;
    }

    @RequestMapping(path = "/parameters", method = RequestMethod.GET)
    @Secured({"ROLE_ADMIN"})
    public ModelAndView getDirectory(ModelMap map) {
        List<BankParameters> params = parametersRepository.getParameters();

        if (params != null) {
            map.put("params", params);
            map.put("pageName", "Bank parameters");
            map.put("leftMenu", "parameters");
        }

        return new ModelAndView("parameters");
    }
}
