package com.project.socializer.requests.verifyuseremail.exception;

public class TooMuchAttempt extends RuntimeException {
    public TooMuchAttempt(String message){
        super(message);
    }

}
