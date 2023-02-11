package com.project.socializer.requests.login.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.socializer.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtService jwtService;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    PrintWriter printWriter;
    @Mock
    Authentication authUser;
    @Mock
    ObjectMapper objectMapper;
    LoginService loginService;

    @BeforeEach
    void setUp() {
        this.loginService = new LoginService(authenticationManager,jwtService,objectMapper);
    }

    @Test
    void islogInUserSuccess() throws Exception{
        //Elements
        String email = "test@test.com";
        String password = "test1234";
        Map<String,String> expectedTokens = new HashMap<>();
        String expectedCreatedAccessToken = "accessToken";
        String expectedCreatedRefreshToken = "refreshToken";
        expectedTokens.put("accessToken",expectedCreatedAccessToken);
        expectedTokens.put("refreshToken",expectedCreatedRefreshToken);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email,password);

        //WHEN
        when(authenticationManager.authenticate(authToken)).thenReturn(authUser);
        when(jwtService.createAccessJwtToken(email)).thenReturn(expectedTokens.get("accessToken"));
        when(jwtService.createRefreshJwtToken(email)).thenReturn(expectedTokens.get("refreshToken"));
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        //EXECUTE
        this.loginService.logInUser(email,password,httpServletResponse);

        //VERIFY
        verify(authenticationManager).authenticate(authToken);
        verify(httpServletResponse).getWriter();
        verify(jwtService).createAccessJwtToken(email);
        verify(jwtService).createRefreshJwtToken(email);
        verify(objectMapper).writeValue(printWriter,expectedTokens);
    }

    @Test()
    void islogInUserFailed() throws Exception{
        //Elements
        String email = "invalidEmail";
        String password = "invalidPassword";

        doThrow(new BadCredentialsException("Invalid Credentials"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(BadCredentialsException.class,()->{loginService.logInUser(email,password,httpServletResponse);});

    }
}