package com.project.socializer.requests.registration.exception;

public class UserExistException extends RuntimeException{

    public UserExistException(String message){
        super(message);
    }
}
