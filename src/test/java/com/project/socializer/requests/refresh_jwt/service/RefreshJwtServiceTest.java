package com.project.socializer.requests.refresh_jwt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.socializer.security.jwt.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshJwtServiceTest {


    RefreshJwtService refreshJwtService;

    @Mock
    JwtService jwtService;

    @Mock
    ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        refreshJwtService = new RefreshJwtService(jwtService,objectMapper);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getNewAccessTokenThroughRefreshToken() {
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        Map<String,String> tokens = new HashMap<>();
        tokens.put("accessToken","invalidRefreshToken");
        tokens.put("refreshToken","validRefreshToken");

        when(jwtService.getTokenFromHeader(request)).thenReturn(tokens);
        when(jwtService.verifyToken(eq("invalidRefreshToken"))).thenReturn(false);
        when(jwtService.verifyToken(eq("validRefreshToken"))).thenReturn(true);

        assertDoesNotThrow(()->{refreshJwtService.getNewAccessTokenThroughRefreshToken(request,response);});
        assertEquals(response.getStatus(),200);
        assertEquals(response.getContentType(),"application/json");

    }

    @Test
    void gettingRefreshTokenThrowsExceptionWhenAccessTokenValid(){
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        Map<String,String> tokens = new HashMap<>();
        tokens.put("accessToken","validRefreshToken");
        tokens.put("refreshToken","validRefreshToken");

        when(jwtService.getTokenFromHeader(request)).thenReturn(tokens);

        when(jwtService.verifyToken(eq("validRefreshToken"))).thenReturn(true);

        assertThrows(JwtException.class,()->{
            refreshJwtService.getNewAccessTokenThroughRefreshToken(request,response);
        });
    }

    @Test
    void gettingRefreshTokenThrowsExceptionWhenNoneTokenIsValid() {

        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        Map<String,String> tokens = new HashMap<>();
        tokens.put("accessToken","invalidRefreshToken");
        tokens.put("refreshToken","invalidRefreshToken");

        when(jwtService.getTokenFromHeader(request)).thenReturn(tokens);

        when(jwtService.verifyToken(eq("invalidRefreshToken"))).thenReturn(false);

        assertThrows(JwtException.class,()->{
            refreshJwtService.getNewAccessTokenThroughRefreshToken(request,response);
        });

    }
}