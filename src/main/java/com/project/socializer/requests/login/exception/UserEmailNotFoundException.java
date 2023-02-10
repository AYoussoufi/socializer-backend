package com.project.socializer.requests.login.exception;

public class UserEmailNotFoundException extends RuntimeException{
    public UserEmailNotFoundException(String message){
        super(message);
    }
}
