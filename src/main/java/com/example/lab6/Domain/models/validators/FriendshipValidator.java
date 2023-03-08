package com.example.lab6.Domain.models.validators;

import com.example.lab6.Domain.models.Friendship;

import java.util.Objects;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship entity) throws ValidationException{
        String err = " ";
        if (Objects.equals(entity.getIdUser1(),""))
            err += "The id of the first user can't be null!";
        if (Objects.equals(entity.getIdUser2(),""))
            err += "The id of the second user can't be null!";
        if (!err.equals(" "))
            throw new ValidationException(err);
    }
}
