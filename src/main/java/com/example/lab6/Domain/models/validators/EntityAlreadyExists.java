package com.example.lab6.Domain.models.validators;

public class EntityAlreadyExists extends RuntimeException{
    public EntityAlreadyExists(String message){
        super(message);
    }
}
