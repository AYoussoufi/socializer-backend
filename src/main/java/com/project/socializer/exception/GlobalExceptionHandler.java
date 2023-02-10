package com.project.socializer.exception;

import com.project.socializer.requests.login.exception.UserEmailNotFoundException;
import com.project.socializer.requests.registration.exception.UserExistException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends  ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            UserExistException.class,
            ValidationException.class,
            UserEmailNotFoundException.class,
            JwtException.class,
            BadCredentialsException.class
    })
    public ResponseEntity<ExceptionResponse> handleException(Exception e, HttpServletRequest request, HttpServletResponse response){
        response.setStatus(400);
        ExceptionResponse exception = new ExceptionResponse(400,e.getMessage(),e.getClass().getSimpleName(),request.getRequestURI());
        return new ResponseEntity<>(exception, HttpStatus.valueOf(400));
    }
}