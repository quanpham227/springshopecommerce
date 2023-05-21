package com.springshopecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductImageNotFoundException extends RuntimeException {

    public ProductImageNotFoundException(String s) {
        super("Product must have at least one image");
    }

}
