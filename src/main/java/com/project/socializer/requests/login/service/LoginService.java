package com.project.socializer.requests.login.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.socializer.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoginService {

    AuthenticationManager authenticationManager;
    JwtService jwtService;
    ObjectMapper objectMapper;
    @Autowired
    LoginService(AuthenticationManager authenticationManager, JwtService jwtService,ObjectMapper objectMapper){
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;

    }

    public void logInUser(String username, String password, HttpServletResponse response){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try{
            Authentication authUser = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            String accessToken = jwtService.createAccessJwtToken(username);
            String refreshToken = jwtService.createRefreshJwtToken(username);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken",accessToken);
            tokens.put("refreshToken",refreshToken);
            this.objectMapper.writeValue(response.getWriter(),tokens);
        }catch (Exception e){
            throw new BadCredentialsException(e.getMessage());
        }
    }
}
