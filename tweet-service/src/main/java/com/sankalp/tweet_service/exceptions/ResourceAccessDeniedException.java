package com.sankalp.tweet_service.exceptions;

public class ResourceAccessDeniedException extends RuntimeException{

    public ResourceAccessDeniedException(String message){
        super(message);
    }

}
