package com.project.socializer.requests.verifyuseremail.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyUserEmailController {

    @PostMapping("/api/v1/public/verifying-email")
    public void verifyUserEmail(){

    }
}
