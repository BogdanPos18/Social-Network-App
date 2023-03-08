package com.example.lab6.Domain.models.validators;

import com.example.lab6.Domain.models.User;

import java.util.Objects;

public class UserValidator implements Validator<User>{
    @Override
    public void validate(User entity) throws ValidationException {
        String err = " ";
        if (Objects.equals(entity.getName(), "") || entity.getName() == null)
            err += " The name of the user can't be null!";
        if (Objects.equals(entity.getId(),"") || entity.getName() == null)
            err += " The id of the user can't be null!";
        if (!err.equals(" "))
            throw new ValidationException(err);
    }
}
