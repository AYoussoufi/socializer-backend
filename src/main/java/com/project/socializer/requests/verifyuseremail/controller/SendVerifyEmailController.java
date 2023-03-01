package com.project.socializer.requests.verifyuseremail.controller;

import com.project.socializer.requests.verifyuseremail.service.SendVerifyEmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SendVerifyEmailController {

    @Autowired
    SendVerifyEmailService sendVerifyEmailService;

    @PostMapping("/api/v1/public/send/mail/verification")
    public void saveAndSendMail(@RequestBody Map<String,String> requestBody, HttpServletResponse response) throws MessagingException {
        String email = requestBody.get("email");
        sendVerifyEmailService.saveUser(email,response);
    }
}
