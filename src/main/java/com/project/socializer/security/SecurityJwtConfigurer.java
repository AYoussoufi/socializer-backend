package com.project.socializer.security;

import com.project.socializer.security.filter.VerifyJwtTokenFilter;
import com.project.socializer.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SecurityJwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    private final JwtService jwtService;
    private final VerifyJwtTokenFilter verifyJwtTokenFilter;
    @Autowired
    public SecurityJwtConfigurer(JwtService jwtService, VerifyJwtTokenFilter verifyJwtTokenFilter) {
        this.jwtService = jwtService;
        this.verifyJwtTokenFilter = verifyJwtTokenFilter;
    }


    @Override
    public void configure(HttpSecurity http){
        http.addFilterBefore(verifyJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}