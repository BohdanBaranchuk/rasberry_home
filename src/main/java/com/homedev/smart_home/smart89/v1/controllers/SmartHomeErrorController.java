package com.homedev.smart_home.smart89.v1.controllers;

import org.apache.catalina.servlet4preview.RequestDispatcher;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class SmartHomeErrorController implements ErrorController {

    private static final Logger log = LoggerFactory.getLogger(
            SmartHomeErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        log.error("Error occurred when user request page");

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {

            Integer statusCode = Integer.valueOf(status.toString());
            log.error("Error with statusCode: {}", statusCode);
        }

        return "error";
    }

    public String getErrorPath() {
        return "/error";
    }
}
