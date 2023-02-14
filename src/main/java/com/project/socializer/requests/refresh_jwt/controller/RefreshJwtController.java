package com.project.socializer.requests.refresh_jwt.controller;

import com.project.socializer.requests.refresh_jwt.service.RefreshJwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
public class RefreshJwtController {

    RefreshJwtService refreshJwtService;

    @Autowired
    public RefreshJwtController(RefreshJwtService refreshJwtService) {
        this.refreshJwtService = refreshJwtService;
    }

    @RequestMapping("/api/v1/public/auth/refresh/jwt")
    public void refreshJwtToken(HttpServletResponse response, HttpServletRequest request) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        refreshJwtService.getNewAccessTokenThroughRefreshToken(request,response);
    }
}
