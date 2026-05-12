package com.example.springbootboilerplate.app.exception.common;

public class ResourceInUseException extends RuntimeException {
    public ResourceInUseException(String message) {
        super(message);
    }
}
