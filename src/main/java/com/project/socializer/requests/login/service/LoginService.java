package com.project.socializer.requests.login.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.socializer.requests.login.exception.UserEmailNotFoundException;
import com.project.socializer.security.jwt.JwtService;
import com.project.socializer.user.repository.UserRepository;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OptionalDataException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class LoginService {

    AuthenticationManager authenticationManager;
    JwtService jwtService;
    @Autowired
    LoginService(AuthenticationManager authenticationManager, JwtService jwtService){
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public void logInUser(String username, String password, HttpServletResponse response){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try{
            Authentication authUser = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            Collection<? extends GrantedAuthority> roles = authUser.getAuthorities();
            String accessToken = jwtService.createAccessJwtToken(username);
            String refreshToken = jwtService.createRefreshJwtToken(username);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken",accessToken);
            tokens.put("refreshToken",refreshToken);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(),tokens);
        }catch (Exception e){
            throw new BadCredentialsException(e.getMessage());
        }
    }
}
