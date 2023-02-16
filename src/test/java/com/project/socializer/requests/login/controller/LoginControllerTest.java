package com.project.socializer.requests.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.socializer.requests.login.request.LoginRequest;
import com.project.socializer.requests.login.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.assertj.core.api.AbstractStringAssert;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    private LoginController loginController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private ObjectMapper objectMapper;
    private LoginRequest loginRequest;
    @Mock
    private LoginService loginService;
    @BeforeEach
    void setUp(){
        this.loginController = new LoginController(loginService);
        this.loginRequest = new LoginRequest("jhon.doe@gmail.com","passwordTest");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.objectMapper = new ObjectMapper();
    }
    /*@Test
    void testLogInOkRequestController() throws Exception {
                //PREPARE
        HashMap<String , String> tokens = new HashMap<>();
        tokens.put("accessToken","Bearer AccessToken");
        tokens.put("refreshToken","Bearer RefreshToken");

        //WHEN
        doNothing().when(loginService).logInUser(loginRequest.getEmail(),loginRequest.getPassword(),any(HttpServletResponse.class));

        //EXECUTE
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

mockMvc.perform(post("/api/v1/public/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());

        //VERIFY
        verify(loginService).logInUser(loginRequest.getEmail(),loginRequest.getPassword(),any(HttpServletResponse.class));
        assertThat(mockHttpServletResponse.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(tokens));        //ASSERTS
    }

    @Test
    void testLogInBadRequestController() {

    }*/
}