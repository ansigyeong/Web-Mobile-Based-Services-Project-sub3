package com.web.blog.controller.exception;


public class UserAlreadyExistException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public UserAlreadyExistException(){}
    public UserAlreadyExistException(String message){
        super(message);
    }
    
}