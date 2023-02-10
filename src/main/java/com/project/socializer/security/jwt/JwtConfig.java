package com.project.socializer.security.jwt;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
@Getter
@Slf4j
public class JwtConfig {
    @Value("${security.jwt.expiration-time}")
    private  Long expirationTime;

    public PrivateKey getPrivateSecretKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String privateKeyPEM = new String(Files.readAllBytes(Paths.get("C:/Users/Don/IdeaProjects/socializer-backend/src/main/resources/decrypted_private_key.pem")), StandardCharsets.UTF_8);
        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "");
        privateKeyPEM = privateKeyPEM.replace("-----END PRIVATE KEY-----", "");
        privateKeyPEM = privateKeyPEM.replaceAll("\\s", "");
        byte[] encoded = Base64.decode(privateKeyPEM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

        public PublicKey getPublicSecretKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
            String publicKeyPEM = new String(Files.readAllBytes(Paths.get("C:/Users/Don/IdeaProjects/socializer-backend/src/main/resources/public_key.pem")), StandardCharsets.UTF_8);
            publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "");
            publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
            publicKeyPEM = publicKeyPEM.replaceAll("\\s", "");
            byte[] encoded = Base64.decode(publicKeyPEM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(keySpec);
        }
}
