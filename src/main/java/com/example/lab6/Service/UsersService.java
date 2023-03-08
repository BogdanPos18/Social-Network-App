package com.example.lab6.Service;

import com.example.lab6.Domain.graph.myGraph;
import com.example.lab6.Domain.models.Friendship;
import com.example.lab6.Domain.models.User;
import com.example.lab6.Domain.models.validators.EntityAlreadyExists;
import com.example.lab6.Domain.models.validators.EntityNotFoundException;
import com.example.lab6.Domain.models.validators.IdNotUnique;
import com.example.lab6.Repo.FriendshipsFileRepo;
import com.example.lab6.Repo.UsersFileRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class UsersService {
    private UsersFileRepo repoU;

    private FriendshipsFileRepo repoF;

    private myGraph<User> friendships;

    public UsersService(UsersFileRepo repoU, FriendshipsFileRepo repoF){
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
        if (findUser(entity) != null)
            throw new EntityAlreadyExists("This user already exists!");
        Iterable<User> users = findAll();
        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(entity.getId()))
                throw new IdNotUnique("This id already exists!");
        }
        repoU.add(entity);
    }

    public void updateUser(String id, User entity2){
        Iterable<User> users = findAll();
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
        repoU.update(user,entity2);
        friendships.removeValue(user);
        Iterable<Friendship> fr = repoF.findAll();
        Iterator<Friendship> itf = fr.iterator();
        ArrayList<String> friends = new ArrayList<String>();
        while (itf.hasNext()) {
            Friendship f = itf.next();
            if (f.getIdUser1().equals(user.getId())) {
                friends.add(f.getIdUser2());
                itf.remove();
                repoF.delete(f);
            }
            if (f.getIdUser2().equals(user.getId())) {
                friends.add(f.getIdUser1());
                itf.remove();
                repoF.delete(f);
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
            f.setId(id1+id2);
            repoF.add(f);
        }
    }

    public User deleteUser(String id){
        Iterable<User> users = findAll();
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
        return user;
    }

    public void addFriend(String id1, String id2){
        Iterable<User> users = findAll();
        User u1 = repoU.getById(id1);
        User u2 = repoU.getById(id2);
        if (u1 == null || u2 == null)
            throw new EntityNotFoundException("One or both users don't exist!");
        friendships.addEdge(u1,u2);
        LocalDateTime date = LocalDateTime.now();
        Friendship f = new Friendship(u1.getId(),u2.getId(),date);
        f.setId(u1.getId()+u2.getId());
        repoF.add(f);
    }

    public Friendship deleteFriend(String id1, String id2){
        Iterable<User> users = findAll();
        Iterator<User> it = users.iterator();
        User u1 = new User(" ",0);
        User u2 = new User(" ",0);
        u1.setId(id1);
        u2.setId(id2);
        while (it.hasNext()){
            User u = it.next();
            if (u.getId().equals(id1))
                u1 = u;
            if (u.getId().equals(id2))
                u2 = u;
        }
        if (u1.getId().equals(" ") && u2.getId().equals(" "))
            throw new EntityNotFoundException("One or both users don't exists!");
        Iterable<Friendship> f =  repoF.findAll();
        Iterator<Friendship> it1 = f.iterator();
        String dateStr = "2000-01-01 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(dateStr, formatter);
        Friendship fr = new Friendship(" "," ",date);
        fr.setId(" ");
        while (it1.hasNext()) {
            Friendship friendship = it1.next();
            String id = u1.getId() + u2.getId();
            if (friendship.getId().equals(id))
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
