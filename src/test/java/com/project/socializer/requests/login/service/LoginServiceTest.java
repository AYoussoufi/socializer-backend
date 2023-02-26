package com.project.socializer.requests.login.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.socializer.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
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
    PrintWriter printWriter;
    @Mock
    Authentication authUser;
    @Mock
    ObjectMapper objectMapper;
    LoginService loginService;
    MockHttpServletResponse mockResponse;
    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

    @BeforeEach
    void setUp() {
        this.loginService = new LoginService(authenticationManager,jwtService,objectMapper);
        this.mockResponse = new MockHttpServletResponse();
        this.usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("Jhon.Doe@Gmail.com","PasswordTest");

    }

    @Test
    void islogInUserSuccess() throws Exception{
        //PREPARE
        Map<String , String> tokens = new HashMap<>();
        tokens.put("accessToken","Bearer AccessToken");
        tokens.put("refreshToken","Bearer RefreshToken");

        //WHEN
        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(authUser);
        when(jwtService.createAccessRefreshJwtToken("Jhon.Doe@Gmail.com")).thenReturn(tokens);

        //EXECUTE
        this.loginService.logInUser("Jhon.Doe@Gmail.com","PasswordTest",mockResponse);

        //VERIFY
        verify(authenticationManager).authenticate(usernamePasswordAuthenticationToken);
        verify(jwtService).createAccessRefreshJwtToken("Jhon.Doe@Gmail.com");

        //ASSERT
        assertDoesNotThrow(()->{
            objectMapper.writeValue(mockResponse.getWriter(),tokens);
        });
        assertDoesNotThrow(()->{
            this.loginService.logInUser("Jhon.Doe@Gmail.com","PasswordTest",mockResponse);
        });
    }

    @Test()
    void islogInUserFailed() throws Exception{
        //Elements
        String email = "invalidEmail";
        String password = "invalidPassword";

        //PREPARE
        doThrow(BadCredentialsException.class)
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        //ASSERT
        assertThrows(BadCredentialsException.class,()->{loginService.logInUser(email,password,any(HttpServletResponse.class));});
    }
}