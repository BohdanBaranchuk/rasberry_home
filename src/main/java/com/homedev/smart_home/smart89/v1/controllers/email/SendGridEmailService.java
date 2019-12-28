package com.homedev.smart_home.smart89.v1.controllers.email;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class SendGridEmailService implements EmailService {

    private SendGrid sendGridClient;

    @Autowired
    public SendGridEmailService(SendGrid sendGridClient) {
        this.sendGridClient = sendGridClient;
    }

    public void sendText(
            String from,
            String to,
            String subject,
            String body) {

        Response response = sendEmail(from, to, subject, new Content("text/plain", body));

        System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
                + response.getHeaders());
    }

    public void sendHTML(
            String from,
            String to,
            String subject,
            String body) {

        Response response = sendEmail(from, to, subject, new Content("text/html", body)); // TODO: rewrite this normally

/*        System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
                + response.getHeaders());*/
    }

    private Response sendEmail(
            String from,
            String to,
            String subject,
            Content content) {

        Mail mail = new Mail(new Email(from), subject, new Email(to), content);

        mail.setReplyTo(new Email(from));

        Request request = new Request();
        Response response = null;

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            response = sendGridClient.api(request);

        } catch (IOException ex) {

            System.out.println(ex.getMessage()); // TODO: use log or throw exception
        }

        return response;
    }
}
