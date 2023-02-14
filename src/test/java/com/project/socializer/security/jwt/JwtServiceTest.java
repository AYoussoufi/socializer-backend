package com.project.socializer.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    JwtService jwtService;

    @Mock
    JwtConfig jwtConfig;

    @BeforeEach
    void SetUp(){
        this.jwtService = new JwtService(jwtConfig);
    }

    @Test
    void createAccessJwtToken() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String username = "testName";
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 30 * 60 * 1000);
        PrivateKey privateKey = jwtConfig.getPrivateSecretKey();
        when(jwtConfig.getPrivateSecretKey()).thenReturn(privateKey);
        String jwtToken = this.jwtService.createAccessJwtToken(username);
        Claims claims = Jwts.parser().setSigningKey(jwtConfig.getPrivateSecretKey()).parseClaimsJws(jwtToken).getBody();

        assertEquals(username, claims.getSubject());
        assertEquals(now, claims.getIssuedAt());
        assertEquals(expiration, claims.getExpiration());

    }

    @Test
    void createRefreshJwtToken() {
    }

    @Test
    void getUsernameFromToken() {
    }

    @Test
    void verifyToken() {
    }

    @Test
    void getTokenFromHeader() {
    }
}