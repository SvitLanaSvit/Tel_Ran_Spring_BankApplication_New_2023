package com.example.bankapplication.service.exception;

public class NegativeBalanceThrowException extends RuntimeException {
    public NegativeBalanceThrowException(String message){
        super(message);
    }
}
