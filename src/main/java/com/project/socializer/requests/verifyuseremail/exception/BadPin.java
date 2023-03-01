package com.project.socializer.requests.verifyuseremail.exception;

public class BadPin extends RuntimeException {
    public BadPin(String message){
        super(message);
    }

}
