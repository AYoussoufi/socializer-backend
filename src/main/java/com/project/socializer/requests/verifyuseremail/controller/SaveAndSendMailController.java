package com.project.socializer.requests.verifyuseremail.controller;

import com.project.socializer.requests.verifyuseremail.service.SaveAndSendEmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SaveAndSendMailController {

    @Autowired
    SaveAndSendEmailService saveAndSendEmailService;

    @PostMapping("/api/v1/public/send/mail/verification")
    public void saveAndSendMail(@RequestBody Map<String,String> requestBody) throws MessagingException {
        String email = requestBody.get("email");
        saveAndSendEmailService.saveUser(email);
        saveAndSendEmailService.sendMail(email);
    }
}
