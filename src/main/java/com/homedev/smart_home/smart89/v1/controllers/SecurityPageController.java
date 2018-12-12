package com.homedev.smart_home.smart89.v1.controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@EnableAutoConfiguration
public class SecurityPageController {

    @RequestMapping(value = "/security", method = RequestMethod.GET)
    public String getSecurityPage(Model model) {

        return "security";
    }
}
