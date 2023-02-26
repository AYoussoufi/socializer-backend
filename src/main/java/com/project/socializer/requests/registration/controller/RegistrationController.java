package com.project.socializer.requests.registration.controller;

import com.project.socializer.requests.registration.requestBody.SignUpRequest;
import com.project.socializer.requests.registration.services.RegistrationService;
import com.project.socializer.util.responsewriter.ResponseBody;
import com.project.socializer.util.responsewriter.ResponseWriter;
import com.project.socializer.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    RegistrationService registrationService;
    UserRepository userRepository;
    ResponseWriter responseWriter;

    @Autowired
    public RegistrationController(RegistrationService registrationService, UserRepository userRepository, ResponseWriter responseWriter) {
        this.registrationService = registrationService;
        this.userRepository = userRepository;
        this.responseWriter = responseWriter;
    }

    @PostMapping(value = "/api/v1/public/auth/signup")
    public void signUp(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) throws Exception {
        if (bindingResult.hasErrors()){
            throw new ValidationException("received input are invalid");
        }
        registrationService.saveUser(signUpRequest);
        responseWriter.writeJsonResponse(response,new ResponseBody(200,"User successfully added",request.getRequestURI()));
    }
}
