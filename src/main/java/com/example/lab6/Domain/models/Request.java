package com.example.lab6.Domain.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Request extends Entity{
    private String nameFrom;
    private String nameTo;
    private String status;
    private LocalDate date;

    public Request(String nameFrom, String nameTo, String status, LocalDate date){
        this.nameFrom = nameFrom;
        this.nameTo = nameTo;
        this.status = status;
        this.date = date;
    }

    public String getNameFrom(){
        return this.nameFrom;
    }

    public String getNameTo(){
        return this.nameTo;
    }

    public String getStatus(){
        return this.status;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public void setNameFrom(String nameFrom){
        this.nameFrom = nameFrom;
    }

    public void setNameTo(String nameTo){
        this.nameTo = nameTo;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    @Override
    public String toString(){
        return "id: " + getId() + ", from: " + this.nameFrom + ", to: " + this.nameTo
                + ", status: " + this.status + ", date: " + this.date;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Request request = (Request) o;
        return getNameFrom().equals(request.nameFrom) & getNameTo().equals(request.nameTo)
                & getStatus().equals(request.status) & getDate().equals(request.date);
    }

    @Override
    public int hashCode(){
        return Objects.hash(getNameFrom(),getNameTo(),getStatus(),getDate());
    }
}
