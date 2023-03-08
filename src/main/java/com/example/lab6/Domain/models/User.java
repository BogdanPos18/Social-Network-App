package com.example.lab6.Domain.models;

import java.util.Objects;

public class User extends Entity {
    private String name;
    private int age;

    public User(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return this.age;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    @Override
    public String toString(){
        return "id: " + this.getId() + ", " + "name: " + this.name + ", " + "age: " + this.age;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return getName().equals(user.getName()) && getAge() == user.getAge();
    }

    @Override
    public int hashCode(){
        return Objects.hash(getName(),getAge());
    }
}
