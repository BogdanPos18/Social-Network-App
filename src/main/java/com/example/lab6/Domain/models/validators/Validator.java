package com.example.lab6.Domain.models.validators;

public interface Validator <T> {
    void validate(T entity) throws ValidationException;
}
