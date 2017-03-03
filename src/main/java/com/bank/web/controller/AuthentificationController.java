package com.bank.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthentificationController {


    @RequestMapping(path = "/main", method = RequestMethod.GET)
    public ModelAndView getHomePage(ModelMap map) {

        map.put("pageName", "MyBank");

        return new ModelAndView("main");
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView getDefault() {
        return new ModelAndView("redirect:/main");
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Map<String, String> params) {
        String auth = params.get("auth");
        Map<String, Object> model = new HashMap<>();
        if (auth != null) {
            model.put("auth", auth);
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(path="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
