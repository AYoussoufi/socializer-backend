package com.project.socializer.requests.registration.exception;

public class UserExistException extends Exception{

    public UserExistException(String message){
        super(message);
    }
}
