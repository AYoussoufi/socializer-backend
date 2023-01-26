package com.project.socializer.requests.registration.controller;

import com.project.socializer.requests.registration.request.SignUpRequest;
import com.project.socializer.requests.registration.services.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;

    @PostMapping(value = "/api/v1/auth/signup")
    public String signUp(@Valid @RequestBody SignUpRequest signUpRequest){
         registrationService.saveUser(signUpRequest);
         return "success";
    }
}
