package com.kaishengit.crm.exception;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException(String message){
        super(message);
    }
    public AuthenticationException(Throwable th){
        super(th);
    }
    public AuthenticationException(String message,Throwable th){
        super(message,th);
    }
}
