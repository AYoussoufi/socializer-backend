package com.project.socializer.requests.login.controller;

import com.project.socializer.requests.login.requestBody.LoginRequest;
import com.project.socializer.requests.login.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    LoginService loginService;
    @Autowired
    LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @PostMapping("/api/v1/public/auth/login")
    public void logIn(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult,HttpServletResponse response){
        if (bindingResult.hasErrors()){
            throw new ValidationException("received input are invalid");
        }
        loginService.logInUser(loginRequest.getEmail(),loginRequest.getPassword(),response);
    }
}
