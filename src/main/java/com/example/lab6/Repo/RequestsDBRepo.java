package com.example.lab6.Repo;

import com.example.lab6.Domain.models.Request;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

public class RequestsDBRepo implements Repo<Request> {
    private String url;

    private String username;

    private String password;

    public RequestsDBRepo(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Request save(Request entity) {
        String sql = "insert into friend_requests(from_,to_,status,date_since) values(?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement ps = connection.prepareStatement(sql)){
            String from = entity.getNameFrom();
            String to = entity.getNameTo();
            String status = entity.getStatus();
            LocalDate ldate = entity.getDate();
            Date date = Date.valueOf(ldate);
            ps.setString(1,from);
            ps.setString(2,to);
            ps.setString(3,status);
            ps.setDate(4,date);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Request delete(Request entity) {
        String idStr = entity.getId();
        int ID = Integer.parseInt(idStr);
        String sql = "delete from friend_requests where id = ?";

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,ID);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Request entity1, Request entity2) {
        String idStr = entity1.getId();
        int ID = Integer.parseInt(idStr);
        String sql = "update friend_requests set status = ? where id = ?";

        try (Connection connection = DriverManager.getConnection(url,username,password);
              PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1,entity1.getStatus());
            ps.setInt(2,ID);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Request findOne(Request entity) {
        return null;
    }

    @Override
    public Iterable<Request> findAll() {
        Set<Request> requests = new HashSet<>();
        String sql = "select * from friend_requests";

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet result = ps.executeQuery()) {
            while(result.next()){
                Integer id = result.getInt("id");
                String from = result.getString("from_");
                String to = result.getString("to_");
                String status = result.getString("status");
                Date date1 = result.getDate("date_since");
                LocalDate date = date1.toLocalDate();
                String idStr = Integer.toString(id);
                Request request = new Request(from,to,status,date);
                request.setId(idStr);
                requests.add(request);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return requests;
    }
}
