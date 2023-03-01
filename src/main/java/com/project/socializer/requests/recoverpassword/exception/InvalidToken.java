package com.project.socializer.requests.recoverpassword.exception;

public class InvalidToken extends RuntimeException{
    public InvalidToken(String message) {
        super(message);
    }
}
