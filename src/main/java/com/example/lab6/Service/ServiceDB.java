package com.example.lab6.Service;

import com.example.lab6.Domain.graph.myGraph;
import com.example.lab6.Domain.models.Friendship;
import com.example.lab6.Domain.models.User;
import com.example.lab6.Domain.models.validators.EntityAlreadyExists;
import com.example.lab6.Domain.models.validators.EntityNotFoundException;
import com.example.lab6.Domain.models.validators.IdNotUnique;
import com.example.lab6.Repo.FriendshipsDBRepo;
import com.example.lab6.Repo.UsersDBRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

public class ServiceDB {
    private UsersDBRepo repoU;
    private FriendshipsDBRepo repoF;
    private myGraph<User> friendships;

    public ServiceDB(UsersDBRepo repoU, FriendshipsDBRepo repoF){
        this.repoU = repoU;
        this.repoF = repoF;
        this.friendships = new myGraph<>();
        loadFriendships();
    }

    private void loadFriendships(){
        Iterable<Friendship> fr = repoF.findAll();
        Iterator<Friendship> it = fr.iterator();
        User u1 = new User("",0);
        User u2 = new User("", 0);
        u1.setId(" ");
        u2.setId(" ");
        while (it.hasNext()){
            Friendship f = it.next();
            String id1 = f.getIdUser1();
            String id2 = f.getIdUser2();
            Iterable<User> users = repoU.findAll();
            Iterator<User> it1 = users.iterator();
            while (it1.hasNext()){
                User u = it1.next();
                if (u.getId().equals(id1))
                    u1 = u;
                if (u.getId().equals(id2))
                    u2 = u;
            }
            friendships.addEdge(u1,u2);
        }
    }

    public void addUser(User entity){
        if (repoU.findOne(entity) != null)
            throw new EntityAlreadyExists("This user already exists!");
        Iterable<User> users = repoU.findAll();
        Iterator<User> it = users.iterator();
        while (it.hasNext()){
            if (it.next().getId().equals(entity.getId()))
                throw new IdNotUnique("This id already exists!");
        }
        repoU.save(entity);
    }

    public void updateUser(String id, User entity){
        Iterable<User> users = repoU.findAll();
        Iterator<User> it = users.iterator();
        boolean found = false;
        User user = new User("",0);
        user.setId("");
        while (it.hasNext() && !user.getId().equals(id)){
            User u = it.next();
            if (u.getId().equals(id)){
                found = true;
                user = u;
            }
        }
        if (!found)
            throw new EntityNotFoundException("The user doesn't exists!");
        repoU.update(user,entity);
        friendships.removeValue(user);
        Iterable<Friendship> fr = repoF.findAll();
        Iterator<Friendship> itf = fr.iterator();
        ArrayList<String> friends = new ArrayList<String>();
        while (itf.hasNext()){
            Friendship f = itf.next();
            if (f.getIdUser1().equals(user.getId())) {
                itf.remove();
                repoF.delete(f);
                friends.add(f.getIdUser2());
            }
            if (f.getIdUser2().equals(user.getId())) {
                itf.remove();
                repoF.delete(f);
                friends.add(f.getIdUser1());
            }
        }
        Iterator<String> itm = friends.iterator();
        while (itm.hasNext()){
            String id1 = user.getId();
            String id2 = itm.next();
            User u1 = repoU.getById(id1);
            User u2 = repoU.getById(id2);
            friendships.addEdge(u1,u2);
            LocalDateTime date = LocalDateTime.now();
            Friendship f = new Friendship(id1,id2,date);
            repoF.save(f);
        }
    }

    public void deleteUser(String id){
        Iterable<User> users = repoU.findAll();
        Iterator<User> it = users.iterator();
        boolean found = false;
        User user = new User("",0);
        user.setId("");
        while (it.hasNext() && !user.getId().equals(id)){
            User u = it.next();
            if (u.getId().equals(id)){
                found = true;
                user = u;
            }
        }
        if (!found)
            throw new EntityNotFoundException("The user doesn't exists!");
        repoU.delete(user);
        friendships.removeValue(user);
        Iterator<Friendship> itf = repoF.findAll().iterator();
        while (itf.hasNext()){
            Friendship f = itf.next();
            if (f.getIdUser1().equals(user.getId()) || f.getIdUser2().equals(user.getId())) {
                itf.remove();
                repoF.delete(f);
            }
        }
    }

    public void addFriend(String id1, String id2){
        User u1 = repoU.getById(id1);
        User u2 = repoU.getById(id2);
        if (u1 == null || u2 == null)
            throw new EntityNotFoundException("One or both users don't exist!");
        friendships.addEdge(u1,u2);
        LocalDateTime date = LocalDateTime.now();
        Friendship f = new Friendship(u1.getId(),u2.getId(),date);
        repoF.save(f);
    }

    public Friendship deleteFriend(String id1, String id2){
        User u1 = repoU.getById(id1);
        User u2 = repoU.getById(id2);
        if (u1 == null || u2 == null)
            throw new EntityNotFoundException("One or both users don't exist!");
        Iterable<Friendship> f =  repoF.findAll();
        Iterator<Friendship> it1 = f.iterator();
        String dateStr = "2000-01-01 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(dateStr, formatter);
        Friendship fr = new Friendship(" "," ",date);
        fr.setId(" ");
        while (it1.hasNext()) {
            Friendship friendship = it1.next();
            if (friendship.getIdUser1().equals(id1) && friendship.getIdUser2().equals(id2))
                fr = friendship;
        }
        if (fr.getIdUser1().equals(" ") && fr.getIdUser2().equals(" "))
            throw new EntityNotFoundException("This friendship doesn't exists!");
        friendships.removeEdge(u1,u2);
        friendships.removeVertexIfNoEdge(u1);
        friendships.removeVertexIfNoEdge(u2);
        repoF.delete(fr);
        return fr;
    }

    public User findUser(User entity){
        return repoU.findOne(entity);
    }

    public Iterable<User> findAll(){
        return repoU.findAll();
    }
    public void displayAll(){
        friendships.print();
    }
}
