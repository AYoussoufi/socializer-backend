package com.project.socializer.requests.verifyuseremail.controller;

import com.project.socializer.requests.verifyuseremail.service.VerifyUserEmailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VerifyUserEmailController {
    @Autowired
    private VerifyUserEmailService verifyUserEmailService;


    @PostMapping("/api/v1/public/verifying-email")
    public void verifyUserEmail(@RequestBody Map<String,String> requestBody, HttpServletResponse response){
        Integer pin = Integer.parseInt(requestBody.get("pin"));
        String email = requestBody.get("email");
        verifyUserEmailService.verifyUserEmail(pin,email,response);

    }
}
