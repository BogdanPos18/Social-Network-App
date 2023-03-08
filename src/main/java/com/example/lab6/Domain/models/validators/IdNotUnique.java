package com.example.lab6.Domain.models.validators;

public class IdNotUnique extends RuntimeException{
    public IdNotUnique(String message){
        super(message);
    }
}
