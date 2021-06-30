package com.yapily.tech.asses.exception;

public class ServiceException extends Exception {
    public ServiceException(int statusCode){
        super(String.valueOf(statusCode));
    }
}
