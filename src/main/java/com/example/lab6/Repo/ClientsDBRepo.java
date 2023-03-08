package com.example.lab6.Repo;

import com.example.lab6.Domain.models.Client;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ClientsDBRepo implements Repo<Client> {
    private String url;

    private String username;

    private String password;

    public ClientsDBRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Client save(Client entity) {
        String sql = "insert into app_clients(username,password) values (?,?)";

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            String username = entity.getUsername();
            String password = entity.getPassword();
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Client delete(Client entity) {
        return null;
    }

    @Override
    public void update(Client entity1, Client entity2) {

    }

    @Override
    public Client findOne(Client entity) {
        return null;
    }

    @Override
    public Iterable<Client> findAll() {
        Set<Client> clients = new HashSet<>();
        String sql = "select * from app_clients";

        try (Connection conection = DriverManager.getConnection(url,username,password);
             PreparedStatement ps = conection.prepareStatement(sql);
             ResultSet result = ps.executeQuery()) {
            while (result.next()) {
                Integer id = result.getInt("id");
                String username = result.getString("username");
                String password = result.getString("password");
                String idStr = Integer.toString(id);
                Client client = new Client(username,password);
                client.setId(idStr);
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }
}
