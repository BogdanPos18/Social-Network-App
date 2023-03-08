package com.example.lab6.Domain.models.validators;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message){
        super(message);
    }
}
