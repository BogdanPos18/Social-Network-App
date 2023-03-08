package com.example.lab6.Repo;

import com.example.lab6.Domain.models.Entity;
import com.example.lab6.Domain.models.validators.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InMemoryRepo<E extends Entity> implements Repo<E> {
    private List<E> entities;
    private Validator<E> validator;

    public InMemoryRepo(Validator<E> validator) {
        this.validator = validator;
        this.entities = new ArrayList<>();
    }

    @Override
    public E save(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity must not be null!");
        if (entities.contains(entity))
            return entity;
        validator.validate(entity);
        entities.add(entity);
        return entity;
    }

    @Override
    public void update(E entity1, E entity2){
        if (entity1 == null || entity2 == null)
            throw new IllegalArgumentException("Entities must not be null!");
        validator.validate(entity1);
        validator.validate(entity2);
        Iterator<E> it = entities.iterator();
        int index = 0;
        while (it.hasNext()){
            E entity = it.next();
            if (entity == entity1)
                entities.set(index,entity2);
            index++;
        }
    }

    @Override
    public E delete(E entity){
        if (entity == null)
            throw new IllegalArgumentException("Entity must not be null!");
        validator.validate(entity);
        entities.remove(entity);
        return entity;
    }

    @Override
    public E findOne(E entity){
        if (entities.contains(entity))
            return entity;
        return null;
    }

    @Override
    public Iterable<E> findAll(){
        return entities;
    }

    public E getById(String id){
        Iterator<E> it = entities.iterator();
        while (it.hasNext()){
            E entity = it.next();
            if (entity.getId().equals(id))
                return entity;
        }
        return null;
    }
}
