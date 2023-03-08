package com.example.lab6.Domain.models;

import java.io.Serializable;

public class Entity implements Serializable {
    private String id;
    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }
}
