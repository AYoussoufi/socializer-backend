package com.project.socializer.requests.login.controller;

import com.project.socializer.requests.login.exception.UserEmailNotFoundException;
import com.project.socializer.requests.login.request.LoginRequest;
import com.project.socializer.requests.login.service.LoginService;
import com.project.socializer.user.entity.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Binding;
import java.security.Principal;
import java.util.NoSuchElementException;

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
