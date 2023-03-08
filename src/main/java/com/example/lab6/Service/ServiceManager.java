package com.example.lab6.Service;

import com.example.lab6.Domain.models.Friendship;
import com.example.lab6.Domain.models.Request;
import com.example.lab6.Domain.models.User;
import com.example.lab6.Repo.FriendshipsDBRepo;
import com.example.lab6.Repo.RequestsDBRepo;
import com.example.lab6.Repo.UsersDBRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServiceManager {
    String url = "jdbc:postgresql://localhost:5432/lab5";
    UsersDBRepo repoU = new UsersDBRepo(url,"postgres","18oct2001");

    FriendshipsDBRepo repoF = new FriendshipsDBRepo(url,"postgres","18oct2001");

    RequestsDBRepo repoR = new RequestsDBRepo(url,"postgres","18oct2001");

    public List<User> findAllUsers(){
        List<User> users = new ArrayList<>();
        Iterable<User> iterable = repoU.findAll();
        iterable.forEach(users::add);
        return users;
    }

    public List<User> findFriends(User user){
        List<User> friends = new ArrayList<>();
        Iterable<Friendship> friendships = repoF.findAll();
        Iterator<Friendship> itf = friendships.iterator();
        while (itf.hasNext()){
            Friendship f = itf.next();
            if (f.getIdUser1().equals(user.getId())){
                User u = repoU.getById(f.getIdUser2());
                friends.add(u);
            }
            if (f.getIdUser2().equals(user.getId())){
                User u = repoU.getById(f.getIdUser1());
                friends.add(u);
            }
        }
        return friends;
    }

    public List<Friendship> getAllFriendships(){
        List<Friendship> friendships = new ArrayList<>();
        Iterable<Friendship> iterable = repoF.findAll();
        iterable.forEach(friendships::add);
        return friendships;
    }

    public boolean findFriendship(User u1, User u2){
        List<Friendship> friendships = getAllFriendships();
        Iterator<Friendship> it = friendships.iterator();
        int ok = 0;
        while (it.hasNext()){
            Friendship f = it.next();
            if (f.getIdUser1().equals(u1.getId()) && f.getIdUser2().equals(u2.getId()))
                ok = 1;
            if (f.getIdUser1().equals(u2.getId()) && f.getIdUser2().equals(u1.getId()))
                ok = 1;
        }
        if (ok == 0)
            return false;
        return true;
    }

    public void addFriend(User u1, User u2){
        Friendship f = new Friendship(u1.getId(),u2.getId(),LocalDateTime.now());
        repoF.save(f);
    }

    public void removeFriend(User u1, User u2){
        List<Friendship> friendships = getAllFriendships();
        Iterator<Friendship> it = friendships.iterator();
        while (it.hasNext()){
            Friendship f = it.next();
            if (f.getIdUser1().equals(u1.getId()) && f.getIdUser2().equals(u2.getId()))
                this.repoF.delete(f);
            if (f.getIdUser1().equals(u2.getId()) && f.getIdUser2().equals(u1.getId()))
                this.repoF.delete(f);
        }
    }

    public void sendRequest(Request r){
        Request r1 = new Request(r.getNameFrom(),r.getNameTo(),r.getStatus(),r.getDate());
        this.repoR.save(r1);
    }

    public List<Request> findAllRequests(){
        List<Request> requests = new ArrayList<>();
        Iterable<Request> iterable = repoR.findAll();
        iterable.forEach(requests::add);
        return requests;
    }

    public List<Request> findUserRequests(User u){
        Iterator<Request> it = repoR.findAll().iterator();
        List<Request> requests = new ArrayList<>();
        while (it.hasNext()){
            Request r = it.next();
            if (r.getNameTo().equals(u.getName()))
                requests.add(r);
        }
        return requests;
    }

    public boolean findRequest(Request r){
        Iterable<Request> requests = repoR.findAll();
        Iterator<Request> it = requests.iterator();
        while (it.hasNext()){
            Request r1 = it.next();
            if (r1.getNameFrom().equals(r.getNameFrom()) && r1.getNameTo().equals(r.getNameTo()))
                return true;
        }
        return false;
    }

    public Request getRequestByUsers(User u1, User u2) {
        Iterator<Request> it = repoR.findAll().iterator();
        Request r = new Request("", "", "", LocalDate.now());
        while (it.hasNext()) {
            Request r1 = it.next();
            if (r1.getNameFrom().equals(u1.getName()) && r1.getNameTo().equals(u2.getName())) {
                r.setId(r1.getId());
                r.setNameFrom(u1.getName());
                r.setNameTo(u2.getName());
                r.setStatus(r1.getStatus());
                r.setDate(r1.getDate());
                return r;
            }
            if (r1.getNameFrom().equals(u2.getName()) && r1.getNameTo().equals(u1.getName())){
                r.setId(r1.getId());
                r.setNameFrom(u2.getName());
                r.setNameTo(u1.getName());
                r.setStatus(r1.getStatus());
                r.setDate(r1.getDate());
                return r;
            }
        }
        return null;
    }

    public void removeRequest(Request r){
        this.repoR.delete(r);
    }

    public void updateRequest(Request r, String status){
        r.setStatus(status);
        this.repoR.update(r,r);
    }
}
