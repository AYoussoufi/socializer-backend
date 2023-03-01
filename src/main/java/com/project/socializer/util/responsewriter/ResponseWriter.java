package com.project.socializer.util.responsewriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResponseWriter {
    private final ObjectMapper objectMapper;

    @Autowired
    ResponseWriter(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public void writeJsonResponse(HttpServletResponse response,ResponseBody responseBody)  {
        try{
            response.setContentType("application/json");
            objectMapper.writeValue(response.getWriter(),responseBody.getResponseBodyMap());
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
