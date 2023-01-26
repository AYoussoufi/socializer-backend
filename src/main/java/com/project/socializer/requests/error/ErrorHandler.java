package com.project.socializer.requests.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ErrorHandler {

    public ServletResponse createJsonError(int errorStatus,String errorMessage,String locationHeader, ServletResponse servletResponse) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(errorStatus,errorMessage);
        ((HttpServletResponse) servletResponse).setStatus(errorStatus);
        ((HttpServletResponse) servletResponse).setHeader("Location", locationHeader);
        servletResponse.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(servletResponse.getWriter(), errorResponse);
        return servletResponse;
    }
}
