package com.example.lab6.Repo;

public interface Repo<E> {
    E save(E entity);
    E delete(E entity);

    void update(E entity1, E entity2);
    E findOne(E entity);
    Iterable<E> findAll();
}
