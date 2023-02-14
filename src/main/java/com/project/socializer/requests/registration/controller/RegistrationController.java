package com.project.socializer.requests.registration.controller;

import com.project.socializer.requests.login.service.LoginService;
import com.project.socializer.requests.registration.exception.UserExistException;
import com.project.socializer.requests.registration.request.SignUpRequest;
import com.project.socializer.requests.registration.services.RegistrationService;
import com.project.socializer.user.entity.UserEntity;
import com.project.socializer.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@RestController
public class RegistrationController {

    RegistrationService registrationService;
    UserRepository userRepository;

    @Autowired
    public RegistrationController(RegistrationService registrationService, UserRepository userRepository) {
        this.registrationService = registrationService;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/api/v1/public/auth/signup")
    public String signUp(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult, HttpServletResponse response) throws UserExistException {
        if (bindingResult.hasErrors()){
            throw new ValidationException("received input are invalid");
        }
        try{
            registrationService.saveUser(signUpRequest);
        }catch (DataIntegrityViolationException e){
           try{
               userRepository.findByEmail(signUpRequest.getEmail()).get();
           }catch (NoSuchElementException ex){
               throw new DataIntegrityViolationException(e.getMessage());
           }
            throw new UserExistException("User already exist, did you forget your password ?");
        }
         return "success";
    }
}
