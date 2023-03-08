package com.example.lab6.Repo;

import com.example.lab6.Domain.models.Friendship;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class FriendshipsDBRepo implements Repo<Friendship> {
    private String url;
    private String username;
    private String password;

    public FriendshipsDBRepo(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }


    @Override
    public Friendship save(Friendship entity) {
        String sql = "insert into friendships(id_user1,id_user2,friends_from) values (?,?,?)";

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            String id1 = entity.getIdUser1();
            String id2 = entity.getIdUser2();
            Timestamp timestamp = Timestamp.valueOf(entity.getFriendsFrom());
            int id1int = Integer.parseInt(id1);
            int id2int = Integer.parseInt(id2);
            ps.setInt(1,id1int);
            ps.setInt(2,id2int);
            ps.setTimestamp(3,timestamp);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Friendship delete(Friendship entity) {
        String idStr = entity.getId();
        int ID = Integer.parseInt(idStr);
        String sql = "delete from friendships where id = ?";

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
    public void update(Friendship entity1, Friendship entity2) {

    }

    @Override
    public Friendship findOne(Friendship entity) {
        String idStr = entity.getId();
        int ID = Integer.parseInt(idStr);
        String sql = "select id,id_user1,id_user2,friends_from from friendships where id = ?";
        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1,ID);
            ResultSet result = ps.executeQuery();
            int id = result.getInt("id");
            int idUser1 = result.getInt("id_user1");
            int idUser2 = result.getInt("id_user2");
            Timestamp tstmp = result.getTimestamp("friends_from");
            String idf = Integer.toString(id);
            String id1 = Integer.toString(idUser1);
            String id2 = Integer.toString(idUser2);
            LocalDateTime friendsFrom = tstmp.toLocalDateTime();
            Friendship f = new Friendship(id1,id2,friendsFrom);
            f.setId(idf);
            return f;
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();
        String sql = "select * from friendships";

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet result = ps.executeQuery())
        {
            while (result.next()){
                Integer id = result.getInt("id");
                Integer id1 = result.getInt("id_user1");
                Integer id2 = result.getInt("id_user2");
                String idStr = Integer.toString(id);
                String idStr1 = Integer.toString(id1);
                String idStr2 = Integer.toString(id2);
                Timestamp tstmp = result.getTimestamp("friends_from");
                LocalDateTime friendsFrom = tstmp.toLocalDateTime();
                Friendship f = new Friendship(idStr1,idStr2,friendsFrom);
                f.setId(idStr);
                friendships.add(f);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return friendships;
    }
}
