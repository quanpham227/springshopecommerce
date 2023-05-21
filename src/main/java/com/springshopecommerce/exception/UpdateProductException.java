package com.springshopecommerce.exception;

public class UpdateProductException extends RuntimeException{

    public UpdateProductException(String message) {
        super(message);
    }

    public UpdateProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
