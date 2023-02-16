package com.project.socializer.security.jwt;

import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class JwtConfigTest {

    JwtConfig jwtConfig;

    @BeforeEach
    public void setUp(){
        this.jwtConfig = new JwtConfig();
    }

    @Test
    void getPrivateSecretKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PrivateKey privateKey = this.jwtConfig.getPrivateSecretKey();
        assertDoesNotThrow(()->{this.jwtConfig.getPrivateSecretKey();});
        assertEquals("RSA",privateKey.getAlgorithm());
        assertNotNull(privateKey);
    }

    @Test
    void getPublicSecretKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PublicKey publicKey = this.jwtConfig.getPublicSecretKey();
        assertDoesNotThrow(()->{this.jwtConfig.getPublicSecretKey();});
        assertEquals("RSA",publicKey.getAlgorithm());
        assertNotNull(publicKey);

    }

    @Test
    void getExpirationTime() {

    }
}