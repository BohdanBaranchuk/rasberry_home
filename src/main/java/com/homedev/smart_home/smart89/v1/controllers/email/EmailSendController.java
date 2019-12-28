package com.homedev.smart_home.smart89.v1.controllers.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmailSendController {

    @Autowired
    EmailService sendGridEmailService;

    @RequestMapping("/email")
    public String email() throws Exception {

        sendGridEmailService.sendHTML(
                "baranchuk.b@gmail.com",
                "baranchuk.b@gmail.com",
                "Hello World",
                "Hello, <strong>how are you doing?</strong>");

        return "air";
    }
}
