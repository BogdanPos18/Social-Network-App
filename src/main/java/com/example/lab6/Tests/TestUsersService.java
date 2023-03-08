package com.example.lab6.Tests;

import com.example.lab6.Domain.models.User;
import com.example.lab6.Domain.models.validators.FriendshipValidator;
import com.example.lab6.Domain.models.validators.UserValidator;
import com.example.lab6.Repo.FriendshipsFileRepo;
import com.example.lab6.Repo.UsersFileRepo;
import com.example.lab6.Service.UsersService;

public class TestUsersService {
    public static void main(String[] args) {
        UsersFileRepo repoU = new UsersFileRepo(new UserValidator(),"data/users.csv");
        FriendshipsFileRepo repoF = new FriendshipsFileRepo(new FriendshipValidator(),"data/friendships.csv");
        UsersService service = new UsersService(repoU,repoF);
        Iterable<User> it = service.findAll();
        it.forEach(System.out::println);
        System.out.println();
        User user = new User("Roman",20);
        user.setId("5");
        service.addUser(user);
        it = service.findAll();
        it.forEach(System.out::println);
        System.out.println();
        service.deleteUser(user.getId());
        it = service.findAll();
        it.forEach(System.out::println);
    }
}
