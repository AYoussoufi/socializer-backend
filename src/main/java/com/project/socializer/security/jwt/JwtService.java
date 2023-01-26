package com.project.socializer.security.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class JwtService {


    private final JwtConfig jwtConfig;

    @Autowired
    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }


    public String createAccessJwtToken(String username){
        Date nowDate = new Date();
        Date ExpiryDate = new Date(nowDate.getTime() + jwtConfig.getExpirationTime());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(ExpiryDate)
                .signWith(SignatureAlgorithm.ES256, jwtConfig.getSecretKey())
                .compact();
    }

    public String createRefreshJwtToken(String username){
        Date nowDate = new Date();
        Date ExpiryDate = new Date(nowDate.getTime() + (Long.parseLong(jwtConfig.getExpirationTime())* 2));

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(ExpiryDate)
                .signWith(SignatureAlgorithm.ES256, jwtConfig.getSecretKey())
                .compact();
    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
       return claims.getSubject();
    }

    public boolean verifyToken(String token){
            log.warn(token);
            Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(token);
            return true;
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
