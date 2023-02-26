package com.project.socializer.requests.registration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.socializer.requests.registration.requestBody.SignUpRequest;
import com.project.socializer.user.entity.Roles;
import com.project.socializer.user.entity.UserEntity;
import com.project.socializer.user.repository.RolesRepository;
import com.project.socializer.user.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Mock
    BindingResult bindingResult;

    @Autowired
    RegistrationController registrationController;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
            UserRepository userRepository;
    @Autowired
    RolesRepository rolesRepository;
    SignUpRequest signUpRequest = new SignUpRequest("Doe","Jhon","Doe","johndoe@example.com","password","2000-01-01");




    @After("signUpThrowUserAlreadyExist")
    void tearDown() {
        userRepository.deleteAll();
        rolesRepository.deleteAll();
    }

    @Before("signUpThrowUserAlreadyExist")
    void targetedSetup() {
        rolesRepository.save(new Roles("USER"));
        userRepository.save(new UserEntity("Doe","Jhon","Doe","johndoe@example.com","password","2000-01-01",rolesRepository.getByRoleName("USER").get()));

    }

    @Test
    void signUp() throws Exception {
        mockMvc.perform(
                        post("/api/v1/public/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signUpRequest))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("User successfully added"))
                .andExpect(jsonPath("$.date").hasJsonPath())
                .andExpect(jsonPath("$.path").value("/api/v1/public/auth/signup"))
                .andReturn();
    }

    @Test
    void signUpThrowUserAlreadyExist() throws Exception {
        mockMvc.perform(
                        post("/api/v1/public/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signUpRequest))
                )
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("This user is already exist, did you forget your password ?"))
                .andExpect(jsonPath("$.date").hasJsonPath())
                .andExpect(jsonPath("$.exception").value("UserExistException"))
                .andExpect(jsonPath("$.path").value("/api/v1/public/auth/signup"))
                .andReturn();
    }

    @Test
    void signUpRequestIsNotValid() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThrows(ValidationException.class,()->{registrationController.signUp(signUpRequest,bindingResult,new MockHttpServletResponse(),new MockHttpServletRequest());});
    }


}