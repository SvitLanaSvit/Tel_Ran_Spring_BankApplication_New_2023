package com.example.bankapplication.service.exception;

public class NegativeDataException extends RuntimeException{
    public NegativeDataException(String message){
        super(message);
    }
}
