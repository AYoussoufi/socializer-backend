package com.project.socializer.security.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Service
public class JwtService {


    private final JwtConfig jwtConfig;

    @Autowired
    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }



    public Map<String, String> createAccessRefreshJwtToken(String username) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 30 * 60 * 1000))
                .signWith(SignatureAlgorithm.RS256, jwtConfig.getPrivateSecretKey())
                .compact();
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.RS256, jwtConfig.getPrivateSecretKey())
                .compact();
        Map<String , String> tokens = new HashMap<>();
        tokens.put("accessToken",accessToken);
        tokens.put("refreshToken",refreshToken);
        return tokens;
    }

    public String getUsernameFromToken(String token) {
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getPublicSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean verifyToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtConfig.getPublicSecretKey()).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public  Map<String,String> getTokenFromHeader(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization");
        String refreshToken = request.getHeader("RefreshToken");
        boolean VerifyTokens = (accessToken != null && refreshToken != null) && (!accessToken.equals("") && !refreshToken.equals(""));
        if(VerifyTokens && accessToken.startsWith("Bearer ") && refreshToken.startsWith("Bearer ")){
            Map<String,String> tokens = new HashMap<>();
            tokens.put("accessToken",accessToken.substring(7));
            tokens.put("refreshToken",refreshToken.substring(7));
            return tokens;
        }
        return null;
    }
}
