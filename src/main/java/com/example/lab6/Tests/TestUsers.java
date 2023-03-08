package com.example.lab6.Tests;

import com.example.lab6.Domain.models.User;
import com.example.lab6.Domain.models.validators.UserValidator;
import com.example.lab6.Repo.UsersFileRepo;

public class TestUsers {
    public static void main(String[] args){
        UserValidator validator = new UserValidator();
        UsersFileRepo usersFileRepo = new UsersFileRepo(validator,"data/users.csv");
        Iterable<User> u = usersFileRepo.findAll();
        u.forEach(System.out::println);
    }
}
