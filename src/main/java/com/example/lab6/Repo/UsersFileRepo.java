package com.example.lab6.Repo;

import com.example.lab6.Domain.models.User;
import com.example.lab6.Domain.models.validators.ValidationException;
import com.example.lab6.Domain.models.validators.Validator;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class UsersFileRepo extends InMemoryRepo<User>{
    private String filename;
    public UsersFileRepo(Validator validator, String filename){
        super(validator);
        this.filename = filename;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(filename);
        try{
            List<String> lines = Files.readAllLines(path);
            for (String line: lines){
                String[] words = line.split(";");
                User user = new User(words[1], Integer.parseInt(words[2]));
                user.setId(words[0]);
                super.save(user);
            }
        }
        catch(IOException e){
            System.err.println("Error while opening the file!");
        }
    }

    private void saveToFile(){
        try{
            new FileOutputStream(filename).close();
            FileWriter myWriter = new FileWriter(filename);
            Iterable<User> users = findAll();
            Iterator<User> it = users.iterator();
            while (it.hasNext()){
                User u = it.next();
                Integer age = u.getAge();
                String ageStr = age.toString();
                String userStr = u.getId() + ';' + u.getName() + ';' + ageStr + '\n';
                myWriter.write(userStr);
            }
            myWriter.close();
        }
        catch(IOException e){
            System.out.println("Error while saving to file!");
        }
    }

    public void add(User entity){
        try {
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

    public void update(User entity1, User entity2){
        try{
            super.update(entity1,entity2);
            saveToFile();
        }
        catch(IllegalArgumentException i){
            System.out.println(i.getMessage());
        }
        catch(ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    public User delete(User entity){
        try {
            super.delete(entity);
            saveToFile();
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

    public User findOne(User entity){
        return super.findOne(entity);
    }

    public Iterable<User> findAll(){
        return super.findAll();
    }
}
