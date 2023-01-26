package com.project.socializer.security.config;

import com.project.socializer.security.filter.VerifyJwtTokenFilter;
import com.project.socializer.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    private final JwtService jwtService;
    @Autowired
    public JwtConfigurer(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public void configure(HttpSecurity http){
        VerifyJwtTokenFilter customFilter = new VerifyJwtTokenFilter(this.jwtService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}