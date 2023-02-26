    package com.project.socializer.exception;

    import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
    import org.springframework.http.MediaType;
    import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

class ExceptionHandlerTest {

    private ExceptionResponse exceptionResponse;

    private MockHttpServletResponse mockHttpServletResponse;

    private MockHttpServletRequest mockHttpServletRequest;

    JsonExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletResponse = new MockHttpServletResponse();
        exceptionResponse = new ExceptionResponse(400,"testing purpose","Exception","/test");
        exceptionHandler = new JsonExceptionHandler();
    }

    @Test
    void createJsonError() throws IOException {

        exceptionHandler.createJsonError(exceptionResponse.getStatus(),exceptionResponse.getError(),exceptionResponse.getException(),"/location",mockHttpServletRequest,mockHttpServletResponse);

        assertEquals(mockHttpServletResponse.getStatus(),exceptionResponse.getStatus());
        assertEquals(mockHttpServletResponse.getHeader("Location"),"/location");
        assertEquals(mockHttpServletResponse.getContentType(),"application/json");
        assertNotNull(mockHttpServletResponse.getContentAsString());

    }
}