package com.example.sport_ecommerce.domain.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Product not found.");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
