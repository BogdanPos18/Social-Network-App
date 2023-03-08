package com.example.lab6.Domain.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Friendship extends Entity{
    private String idUser1, idUser2;
    private LocalDateTime friendsFrom;

    public Friendship(String id1, String id2, LocalDateTime f){
        this.idUser1 = id1;
        this.idUser2 = id2;
        this.friendsFrom = f;
    }

    public String getIdUser1(){
        return idUser1;
    }

    public String getIdUser2(){
        return idUser2;
    }

    public LocalDateTime getFriendsFrom(){
        return friendsFrom;
    }

    public void setIdUser1(String id1){
        this.idUser1 = id1;
    }

    public void setIdUser2(String id2){
        this.idUser2 = id2;
    }

    public void setFriendsFrom(LocalDateTime f){
        this.friendsFrom = f;
    }

    @Override
    public String toString(){
        return "id: " + getId() + ", " + "idUser1: " + getIdUser1() + ", "
                + "idUser2: " + getIdUser2() + ", " + "friendsFrom: " + getFriendsFrom();
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Friendship friendship = (Friendship) o;
        return getId().equals(friendship.getId()) && getIdUser1().equals(friendship.getIdUser1())
                && getIdUser2().equals(friendship.getIdUser2()) && getFriendsFrom().equals(friendship.getFriendsFrom());
    }

    @Override
    public int hashCode(){
        return Objects.hash(getId(),getIdUser1(),getIdUser2(),getFriendsFrom());
    }
}
