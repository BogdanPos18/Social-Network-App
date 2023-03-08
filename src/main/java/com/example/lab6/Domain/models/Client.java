package com.example.lab6.Domain.models;

import java.util.Objects;

public class Client extends Entity{
    private String username;

    private String password;

    public Client(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String toString(){
        return "id: " + this.getId() + ", " + "username: " + this.username;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Client client = (Client) o;
        return client.getId().equals(this.getId()) & client.username.equals(this.username) & client.password.equals(this.password);
    }

    @Override
    public int hashCode(){
        return Objects.hash(getUsername(),getPassword());
    }
}
