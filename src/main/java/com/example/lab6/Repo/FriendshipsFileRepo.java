package com.example.lab6.Repo;

import com.example.lab6.Domain.models.Friendship;
import com.example.lab6.Domain.models.validators.ValidationException;
import com.example.lab6.Domain.models.validators.Validator;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class FriendshipsFileRepo extends InMemoryRepo<Friendship>{
    private String filename;
    public FriendshipsFileRepo(Validator validator, String filename){
        super(validator);
        this.filename = filename;
        loadData();
    }

    private void loadData(){
        Path path = Paths.get(filename);
        try{
            List<String> lines = Files.readAllLines(path);
            for (String line: lines){
                String words[] = line.split(";");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime date = LocalDateTime.parse(words[3],formatter);
                Friendship friendship = new Friendship(words[1], words[2], date);
                friendship.setId(words[0]);
                super.save(friendship);
            }
        }
        catch(IOException e){
            System.out.println("Error while opening the file!");
        }
    }

    private void saveToFile(){
        try{
            new FileOutputStream(filename).close();
            FileWriter myWriter = new FileWriter(filename);
            Iterable<Friendship> friendships = findAll();
            Iterator<Friendship> it = friendships.iterator();
            while (it.hasNext()){
                Friendship f = it.next();
                LocalDateTime date = f.getFriendsFrom();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String dateStr = date.format(formatter);
                String friendshipStr = f.getId() + ';' + f.getIdUser1() + ';' + f.getIdUser2() + ';' + dateStr + '\n';
                myWriter.write(friendshipStr);
            }
            myWriter.close();
        }
        catch(IOException e){
            System.out.println("Error while saving to file!");
        }
    }

    public void add(Friendship entity){
        try{
            super.save(entity);
            saveToFile();
        }
        catch(IllegalArgumentException i){
            System.out.println(i.getMessage());
        }
        catch(ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    public Friendship delete(Friendship entity){
        try{
            super.delete(entity);
            saveToFile();;
            return entity;
        }
        catch(IllegalArgumentException i){
            System.out.println(i.getMessage());
        }
        catch(ValidationException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}