package com.example.lab6.Domain.graph;

import java.util.*;

public class myGraph <T> {
    private Map<T, List<T>> map = new HashMap<>();

    public void addVertex(T entity){
        map.put(entity, new ArrayList<>());
    }

    public void addEdge(T entity1, T entity2){
        if (!map.containsKey(entity1))
            addVertex(entity1);
        if (!map.containsKey(entity2))
            addVertex(entity2);
        map.get(entity1).add(entity2);
        map.get(entity2).add(entity1);
    }

    public void removeEdge(T entity1, T entity2){
        map.get(entity1).remove(entity2);
        map.get(entity2).remove(entity1);
    }

    public void removeVertex(T entity){
        map.remove(entity);
    }

    public void removeVertexIfNoEdge(T entity){
        List<T> list = map.get(entity);
        if (list.size() == 0)
            removeVertex(entity);
    }

    public void removeValue(T entity){
        Iterator<Map.Entry<T, List<T>>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<T, List<T>> entry = it.next();
            List<T> list = entry.getValue();
            T key = entry.getKey();
            if (list.contains(entity))
                map.get(key).remove(entity);
            if (key == entity)
                it.remove();
        }
    }
    public void print(){
        for (Map.Entry<T, List<T>> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " -> Value: " + entry.getValue());
            }
    }

    public HashMap<T, List<T>> getGraph(){
        return (HashMap<T, List<T>>) map;
    }
}
