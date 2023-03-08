package com.example.lab6.UI;



import com.example.lab6.Domain.models.User;
import com.example.lab6.Domain.models.validators.EntityAlreadyExists;
import com.example.lab6.Domain.models.validators.EntityNotFoundException;
import com.example.lab6.Domain.models.validators.IdNotUnique;
import com.example.lab6.Service.ServiceDB;

import java.util.Scanner;

public class UI_DB {
    private ServiceDB service;

    public UI_DB(ServiceDB service){
        this.service = service;
    }

    public void addUserUI(){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Give the name: ");
            String name = sc.nextLine();
            System.out.println("Give the age: ");
            int age = sc.nextInt();
            User user = new User(name, age);
            service.addUser(user);
        }
        catch(EntityAlreadyExists e){
            System.out.println(e.getMessage());
        }
        catch(IdNotUnique i){
            System.out.println(i.getMessage());
        }
    }

    public void updateUserUI() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Give the id of the user to update: ");
            String id = sc.nextLine();
            System.out.println("Give the new name");
            String name = sc.nextLine();
            System.out.println("Give the new age: ");
            int age = sc.nextInt();
            User user = new User(name,age);
            user.setId(id);
            service.updateUser(id,user);
        }
        catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteUserUI(){
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("Give the id: ");
            String id = sc.nextLine();
            service.deleteUser(id);
        }
        catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void addFriendUI(){
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("Give the id of the first user: ");
            String id = sc.nextLine();

            Scanner sc1 = new Scanner(System.in);
            System.out.println("Give the id of the second user: ");
            String id1 = sc1.nextLine();


            service.addFriend(id,id1);
        }
        catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void removeFriendUI(){
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("Give the id of the first user: ");
            String id = sc.nextLine();

            Scanner sc1 = new Scanner(System.in);
            System.out.println("Give the id of the second user: ");
            String id1 = sc1.nextLine();

            service.deleteFriend(id,id1);
        }
        catch(EntityNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void displayAllUsers(){
        Iterable<User> it = service.findAll();
        it.forEach(System.out::println);
    }

    public void displayFriendships(){
        service.displayAll();
    }

    public void menu(){
        System.out.println("1. Add user.");
        System.out.println("2. Update user.");
        System.out.println("3. Remove user.");
        System.out.println("4. Add friend.");
        System.out.println("5. Remove friend.");
        System.out.println("6. Display users.");
        System.out.println("7. Display friendships");
    }

    public void runMenu(){
        menu();
        System.out.println("Give an option: ");
        Scanner sc = new Scanner(System.in);
        int op = sc.nextInt();
        while (op != 0){
            switch (op){
                case 1: {addUserUI(); break;}
                case 2: {updateUserUI(); break;}
                case 3: {deleteUserUI(); break;}
                case 4: {addFriendUI(); break;}
                case 5: {removeFriendUI(); break;}
                case 6: {displayAllUsers(); break;}
                case 7: {displayFriendships(); break;}
            }
            System.out.println("Give an option: ");
            op = sc.nextInt();
        }
    }
}
