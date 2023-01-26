package com.project.socializer.security.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JwtConfig {

    @Value("${security.jwt.secret-key}")
    private  String secretKey;
    @Value("${security.jwt.expiration-time}")
    private  String expirationTime;

}
