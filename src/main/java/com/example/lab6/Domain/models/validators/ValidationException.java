package com.example.lab6.Domain.models.validators;

public class ValidationException extends RuntimeException{
    public ValidationException(){

    }
    public ValidationException(String message){
        super(message);
    }
}
