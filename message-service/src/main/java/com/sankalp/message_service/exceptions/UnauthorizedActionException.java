package com.sankalp.message_service.exceptions;

public class UnauthorizedActionException extends RuntimeException{

    public UnauthorizedActionException(String message){
        super(message);
    }

}
