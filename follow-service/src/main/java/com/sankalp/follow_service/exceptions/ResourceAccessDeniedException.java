package com.sankalp.follow_service.exceptions;

public class ResourceAccessDeniedException extends RuntimeException{

    public ResourceAccessDeniedException(String message){
        super(message);
    }

}
