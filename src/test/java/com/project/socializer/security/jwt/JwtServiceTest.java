package com.project.socializer.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;
    private String jwtToken;
    private String invalidJwtToken;
    private final String username = "John.Doe@gmail.com";
    private final Date dateNow = new Date();

    @BeforeEach
    void SetUp() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        JwtConfig jwtConfig = new JwtConfig();
        this.jwtService = new JwtService(jwtConfig);
        this.jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(dateNow)
                .setExpiration(new Date(dateNow.getTime() + 30 * 60 * 1000))
                .signWith(SignatureAlgorithm.RS256, jwtConfig.getPrivateSecretKey())
                .compact();
        this.invalidJwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(dateNow)
                .setExpiration(new Date())
                .signWith(SignatureAlgorithm.RS256, jwtConfig.getPrivateSecretKey())
                .compact();
    }

    @Test
    void createAccessRefreshJwtToken() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        assertDoesNotThrow(()->{jwtService.createAccessRefreshJwtToken(this.username);});
        assertNotNull(jwtService.createAccessRefreshJwtToken(this.username));
        assertEquals(2, jwtService.createAccessRefreshJwtToken(this.username).size());
        assertTrue(jwtService.createAccessRefreshJwtToken(this.username).containsKey("accessToken"));
        assertTrue(jwtService.createAccessRefreshJwtToken(this.username).containsKey("refreshToken"));
        assertEquals(447,jwtService.createAccessRefreshJwtToken(this.username).get("accessToken").length());
        assertEquals(447,jwtService.createAccessRefreshJwtToken(this.username).get("refreshToken").length());
        assertNotNull(jwtService.createAccessRefreshJwtToken(this.username).get("accessToken"));
        assertNotNull(jwtService.createAccessRefreshJwtToken(this.username).get("refreshToken"));
        assertNotEquals(jwtService.createAccessRefreshJwtToken(this.username).get("accessToken"),jwtService.createAccessRefreshJwtToken(this.username).get("refreshToken"));
    }

    @Test
    void verifyTokenValid() {
        assertTrue(this.jwtService.verifyToken(this.jwtToken));
    }

    @Test
    void verifyNotTokenValid() {
        assertFalse(this.jwtService.verifyToken(this.invalidJwtToken));
    }

    @Test
    void getTokenFromHeader() {
        MockHttpServletRequest request =  new MockHttpServletRequest();
        request.addHeader("Authorization","Bearer accessToken-JWT");
        request.addHeader("RefreshToken","Bearer refreshToken-JWT");

        Map<String , String> tokensFromHeader = jwtService.getTokenFromHeader(request);

        assertEquals("accessToken-JWT",tokensFromHeader.get("accessToken"));
        assertEquals("refreshToken-JWT",tokensFromHeader.get("refreshToken"));
    }
}