package com.project.socializer.requests.refresh_jwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.socializer.security.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class RefreshJwtControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtService jwtService;

    @Autowired
    ObjectMapper objectMapper;

    Map<String,String> tokens;

    @BeforeEach
    void setUp() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.tokens = jwtService.createAccessRefreshJwtToken("test");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(2)
    public void GetANewJwtTokensFromRefreshTokens() throws Exception {
        this.tokens.put("accessToken","Bearer invalid");
        mockMvc.perform(get("/api/v1/public/auth/refresh/jwt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization",tokens.get("accessToken"))
                        .header("RefreshToken","Bearer "+tokens.get("refreshToken")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").hasJsonPath())
                .andExpect(jsonPath("$.accessToken").value(matchesPattern(".{428,}")))
                .andExpect(jsonPath("$.refreshToken").hasJsonPath())
                .andExpect(jsonPath("$.refreshToken").value(matchesPattern(".{428,}")))
                .andReturn();
    }

    @Test
    @Order(1)
    public void AccessTokenStillValidYouWontGetANewJwtToken() throws Exception {
        mockMvc.perform(get("/api/v1/public/auth/refresh/jwt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer "+tokens.get("accessToken"))
                        .header("RefreshToken","Bearer "+tokens.get("refreshToken")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.exception").value("JwtException"))
                .andReturn();
    }
}