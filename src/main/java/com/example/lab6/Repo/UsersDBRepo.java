package com.example.lab6.Repo;

import com.example.lab6.Domain.models.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UsersDBRepo implements Repo<User>{
    private String url;
    private String username;
    private String password;

    public UsersDBRepo(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public User save(User entity) {
        String sql = "insert into users(name, age) values (?,?)";

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, entity.getName());
            ps.setInt(2,entity.getAge());
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User delete(User entity) {
        String idStr = entity.getId();
        int ID = Integer.parseInt(idStr);
        String sql = "delete from users where id = ?";

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1,ID);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(User entity1, User entity2) {
        String idStr = entity1.getId();
        int ID = Integer.parseInt(idStr);
        String sql = "update users set name = ?, age = ? where id = ?";
        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement ps = connection.prepareStatement(sql);)
        {
            ps.setString(1,entity2.getName());
            ps.setInt(2,entity2.getAge());
            ps.setInt(3,ID);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public User findOne(User entity) {
        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement ps = connection.prepareStatement("select * from users");
             ResultSet resultSet = ps.executeQuery())
        {
            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                User user = new User(name,age);
                String idStr = id.toString();
                user.setId(idStr);
                if (user.getId().equals(entity.getId()) && user.getName().equals(entity.getName())
                    && user.getAge() == entity.getAge())
                    return user;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<User> findAll() {
        Set<User> users = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement ps = connection.prepareStatement("select * from users");
             ResultSet resultSet = ps.executeQuery())
        {
            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String idStr = id.toString();

                User user = new User(name,age);
                user.setId(idStr);
                users.add(user);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    public User getById(String id){
        Iterable<User> users = findAll();
        Iterator<User> it = users.iterator();
        while (it.hasNext()){
            User u = it.next();
            if (u.getId().equals(id))
                return u;
        }
        return null;
    }
}
