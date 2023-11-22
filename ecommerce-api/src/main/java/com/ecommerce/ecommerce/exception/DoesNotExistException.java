package com.ecommerce.ecommerce.exception;

public class DoesNotExistException extends RuntimeException {
    public DoesNotExistException(String message) {
        super(message);
    }

    public DoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
