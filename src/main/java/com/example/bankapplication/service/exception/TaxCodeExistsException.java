package com.example.bankapplication.service.exception;

public class TaxCodeExistsException extends RuntimeException {
    public TaxCodeExistsException(String message) {
        super(message);
    }
}
