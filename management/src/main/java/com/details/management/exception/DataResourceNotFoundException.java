package com.details.management.exception;

public class DataResourceNotFoundException extends Exception{
    public DataResourceNotFoundException(String message) {
        super(message);
    }

    public DataResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
