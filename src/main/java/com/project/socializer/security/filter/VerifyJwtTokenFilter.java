package com.project.socializer.security.filter;

import com.project.socializer.exception.ExceptionHandler;
import com.project.socializer.requests.login.service.LoginService;
import com.project.socializer.security.jwt.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.Map;
@NoArgsConstructor
@Slf4j
public class VerifyJwtTokenFilter extends GenericFilter {

    JwtService jwtService;
    UserDetailsService userDetailService;
    ExceptionHandler exceptionHandler = new ExceptionHandler();


    @Autowired
    public VerifyJwtTokenFilter(JwtService jwtService,UserDetailsService userDetailService) {
        this.jwtService = jwtService;
        this.userDetailService = userDetailService;
    }

    @Autowired
    public VerifyJwtTokenFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, JwtException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        if(req.getRequestURI().equals("/api/v1/auth/signup")
        || req.getRequestURI().equals("/api/v1/auth/login")
        ){
        }else{
                //1.I GET MY DESIRED TOKENS
                Map<String,String> tokens = jwtService.getTokenFromHeader((HttpServletRequest) servletRequest);
                if(tokens != null){
                    //2.CONTAIN THE DESIRED TOKENS IN VARIABLES
                    String accessToken = tokens.get("accessToken");
                    String refreshToken = tokens.get("refreshToken");
                    //3.A: IF THE TOKEN IS VALID WE WILL AUTHENTICATE THE USER
                    if(jwtService.verifyToken(accessToken)){
                        //A.CLAIM THE USERNAME
                        String username;
                        try {
                            username = jwtService.getUsernameFromToken(accessToken);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidKeySpecException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            username = jwtService.getUsernameFromToken(accessToken);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidKeySpecException e) {
                            throw new RuntimeException(e);
                        }
                        //B.GET THE ROLES
                        Collection<? extends GrantedAuthority> roles = userDetailService.loadUserByUsername(username).getAuthorities();
                        //C.AUTHENTICATE
                        Authentication auth = new UsernamePasswordAuthenticationToken(username,null,roles);
                        //D.GET THE PRINCIPAL
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                    //3.B IF THE ACCESS TOKEN IS INVALID AND THE REFRESH TOKEN IS STILL WELL WE WILL SEND UNAUTHORIZED REQUEST AND TELL THE CLIENT SIDE TO REFRESH THE TOKEN
                    else if(jwtService.verifyToken(refreshToken)){
                        exceptionHandler.createJsonError(400,"Access Token is expired or invalid",JwtException.class.getSimpleName(),"/api/v1/auth/refresh",req,res);
                    }
                    //4.C IF BOTH TOKEN ARE INVALID WE WILL SEND UNAUTHORIZED REQUEST AND TELL THE CLIENT SIDE TO AUTHENTICATE AGAIN
                    else{
                        exceptionHandler.createJsonError(400,"Access Token and Refresh Token are expired or invalid please relogin.",JwtException.class.getSimpleName(),"/api/v1/auth/login",req,res);
                    }
                }else{
                    exceptionHandler.createJsonError(401,"No Access Token or Refresh Token, please try to login to get your tokens.",JwtException.class.getSimpleName(),"/api/v1/auth/login",req,res);
        }
    }
        filterChain.doFilter(servletRequest,servletResponse);
}
}