package com.project.socializer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ExceptionResponse {
    private int status;
    private String error;
    private String date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
    private String exception;
    private String path;

    public ExceptionResponse(int status, String error, String exception, String path) {
        this.status = status;
        this.error = error;
        this.exception = exception;
        this.path = path;
    }
}
