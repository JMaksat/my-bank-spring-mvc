package com.bank.web.controller;

import com.bank.web.model.entity.Users;
import com.bank.web.model.repository.UsersRepository;
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
public class UserController {
    public static final Logger logger = Logger.getLogger(UserController.class);

    private UsersRepository usersRepository;

    @Autowired
    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    @Secured({"ROLE_ADMIN"})
    public ModelAndView getUsers(ModelMap map) {
        List<Users> folks = usersRepository.getUsers();

        if (folks != null) {
            map.put("folks", folks);
            map.put("pageName", "Users");
            map.put("leftMenu", "users");
        }

        return new ModelAndView("users");
    }
}
