package com.project.socializer.requests.login.controller;

import com.project.socializer.requests.login.request.LoginRequest;
import com.project.socializer.requests.login.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping("/api/v1/auth/login")
    public void logIn(@Valid @RequestBody LoginRequest loginRequest){
      loginService.loadUserByUsername(loginRequest.getEmail());
    }
}
