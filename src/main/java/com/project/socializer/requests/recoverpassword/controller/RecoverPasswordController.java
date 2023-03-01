package com.project.socializer.requests.recoverpassword.controller;

import com.project.socializer.requests.recoverpassword.service.RecoverPasswordService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RecoverPasswordController {

    @Autowired
    RecoverPasswordService recoverPasswordService;

    @PostMapping("/api/v1/public/recover-password")
    public void recoverPasswordResponse(@RequestBody Map<String,String> requestBody, HttpServletResponse response){
        String email = requestBody.get("email");
        recoverPasswordService.recoverPassword(email,response);
    }
}
