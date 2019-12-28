package com.homedev.smart_home.smart89.v1.controllers.email;

public interface EmailService {

    void sendText(String from, String to, String subject, String body);

    void sendHTML(String from, String to, String subject, String body);
}
