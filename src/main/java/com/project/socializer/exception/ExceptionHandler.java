package com.project.socializer.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ExceptionHandler {

    public ServletResponse createJsonError(int status, String error, String exception, String location,HttpServletRequest request,HttpServletResponse response) throws IOException {
        ExceptionResponse exceptionResponse = new ExceptionResponse(status,error,exception,request.getRequestURI(),location);
        response.setStatus(status);
        response.setHeader("Location", location);
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), exceptionResponse);
        return response;
    }
}
