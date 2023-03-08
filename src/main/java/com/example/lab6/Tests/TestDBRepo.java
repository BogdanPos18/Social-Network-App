package com.example.lab6.Tests;

import com.example.lab6.Repo.UsersDBRepo;

public class TestDBRepo {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/lab5";
        UsersDBRepo repo = new UsersDBRepo(url, "postgres", "18oct2001");
        repo.findAll().forEach(System.out::println);
    }
}
