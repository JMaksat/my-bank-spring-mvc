package com.bank.web.controller;

import com.bank.web.model.entity.Directory;
import com.bank.web.model.entity.Transactions;
import com.bank.web.model.repository.DirectoryRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class DirectoryController {
    public static final Logger logger = Logger.getLogger(DirectoryController.class);

    private DirectoryRepository directoryRepository;

    @Autowired
    public DirectoryController(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @RequestMapping(path = "/directory", method = RequestMethod.GET)
    //@Secured({"ROLE_USER"})
    public ModelAndView getDirectory(ModelMap map) {
        List<Directory> dictionary = directoryRepository.directoryList();

        if (dictionary != null) {
            map.put("dictionary", dictionary);
            map.put("pageName", "Directory");
            map.put("leftMenu", "directory");
        }

        return new ModelAndView("directory");
    }
}
