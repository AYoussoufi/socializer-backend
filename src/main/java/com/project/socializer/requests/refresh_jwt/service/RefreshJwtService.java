package com.project.socializer.requests.refresh_jwt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.socializer.exception.ExceptionHandler;
import com.project.socializer.security.jwt.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

@Service
@Slf4j
public class RefreshJwtService {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Autowired
    public RefreshJwtService(JwtService jwtService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    public void getNewAccessTokenThroughRefreshToken(HttpServletRequest request,HttpServletResponse response) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Map<String,String> tokens = jwtService.getTokenFromHeader(request);
        String refreshJwtToken = tokens.get("refreshToken");
        String accessJwtToken = tokens.get("accessToken");
        if(jwtService.verifyToken(refreshJwtToken) && !jwtService.verifyToken(accessJwtToken)){
                String username = jwtService.getUsernameFromToken(refreshJwtToken);
                Map<String,String> newTokens = jwtService.createAccessRefreshJwtToken(username);
                objectMapper.writeValue(response.getWriter(),newTokens);
        }else if(jwtService.verifyToken(accessJwtToken)) {
            throw new JwtException("AccessToken still valid please this endpoint is used to refresh the access token when invalid");
        }else{
            response.setHeader("Location","/login");
            throw new JwtException("AccessToken and Refresh Token are invalid");
        }
    }
}
