package com.example.lab6.Domain.container;

import com.example.lab6.Domain.models.Entity;

public class myList <T extends Entity> {
    private T[] arr;
    private int size;
    private int capacity;

    public myList(){
        this.size = 0;
        this.capacity = 10;
        this.arr = (T[]) new Object[capacity];
    }

    public int getSize(){
        return this.size;
    }

    private void resize(){
        this.capacity *= 2;
        Object[] newArr = new Object[capacity];
        for (int i = 0; i < this.size; i++)
            newArr[i] = this.arr[i];
        this.arr = (T[]) newArr;
    }

    public void add(T entity){
        if (size >= capacity)
            resize();
        this.arr[size++] = entity;
    }

    public T remove(T entity){
        if (find(entity)) {
            this.size--;
            return entity;
        }
        return null;
    }

    public T getIndex(int index){
        return (T)this.arr[index];
    }
    public boolean find(T entity){
        for (int i = 0; i < size; i++)
            if (this.arr[i] == entity)
                return true;
        return false;
    }

    public T[] getAll(){
        return this.arr;
    }
}