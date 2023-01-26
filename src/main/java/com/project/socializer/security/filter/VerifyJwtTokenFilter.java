package com.project.socializer.security.filter;

import com.project.socializer.requests.error.ErrorHandler;
import com.project.socializer.requests.login.service.LoginService;
import com.project.socializer.security.jwt.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
@NoArgsConstructor
@Slf4j
public class VerifyJwtTokenFilter extends GenericFilter {

    JwtService jwtService;
    LoginService loginService;
    ErrorHandler errorHandler = new ErrorHandler();


    @Autowired
    public VerifyJwtTokenFilter(JwtService jwtService,LoginService loginService) {
        this.jwtService = jwtService;
        this.loginService = loginService;
    }

    @Autowired
    public VerifyJwtTokenFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if(req.getRequestURI().equals("/api/v1/auth/signup")
        || req.getRequestURI().equals("/api/v1/auth/login")
        ){
        }else{
            try{
                //1.I GET MY DESIRED TOKENS
                Map<String,String> tokens = jwtService.getTokenFromHeader((HttpServletRequest) servletRequest);
                if(tokens != null){
                    //2.CONTAIN THE DESIRED TOKENS IN VARIABLES
                    String accessToken = tokens.get("accessToken");
                    String refreshToken = tokens.get("refreshToken");
                    //3.A: IF THE TOKEN IS VALID WE WILL AUTHENTICATE THE USER
                    if(jwtService.verifyToken(accessToken)){
                        //A.CLAIM THE USERNAME
                        String username = jwtService.getUsernameFromToken(accessToken);
                        //B.GET THE ROLES
                        Collection<? extends GrantedAuthority> roles = loginService.loadUserByUsername(username).getAuthorities();
                        //C.AUTHENTICATE
                        Authentication auth = new UsernamePasswordAuthenticationToken(username,null,roles);
                        //D.GET THE PRINCIPAL
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                    //3.B IF THE ACCESS TOKEN IS INVALID AND THE REFRESH TOKEN IS STILL WELL WE WILL SEND UNAUTHORIZED REQUEST AND TELL THE CLIENT SIDE TO REFRESH THE TOKEN
                    else if(jwtService.verifyToken(refreshToken)){
                        errorHandler.createJsonError(HttpServletResponse.SC_UNAUTHORIZED,"Access Token expired or invalid","/refresh",servletResponse);
                    }
                    //4.C IF BOTH TOKEN ARE INVALID WE WILL SEND UNAUTHORIZED REQUEST AND TELL THE CLIENT SIDE TO AUTHENTICATE AGAIN
                    else{
                        errorHandler.createJsonError(HttpServletResponse.SC_UNAUTHORIZED,"Refresh Token and Access Token expired or invalid","/login",servletResponse);
                    }
                }else{
                    throw new JwtException("Unauthorized Request no token has been provided please make sure to send a jwt");
                }

            }catch(JwtException e){
                errorHandler.createJsonError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage(),"/login",servletResponse);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}