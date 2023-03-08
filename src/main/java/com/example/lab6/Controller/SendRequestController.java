package com.example.lab6.Controller;

import com.example.lab6.Domain.models.Friendship;
import com.example.lab6.Domain.models.Request;
import com.example.lab6.Domain.models.User;
import com.example.lab6.Repo.RequestsDBRepo;
import com.example.lab6.Repo.UsersDBRepo;
import com.example.lab6.Service.ServiceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;

public class SendRequestController {
    private String url = "jdbc:postgresql://localhost:5432/lab5";

    private RequestsDBRepo repoR = new RequestsDBRepo(url,"postgres","18oct2001");

    private UsersDBRepo repoU = new UsersDBRepo(url,"postgres","18oct2001");

    ServiceManager service;

    @FXML
    TextField Name;

    @FXML
    Button sendButton;

    User user;

    User friend;

    public void setUser(User u){
        this.user = new User(u.getName(),u.getAge());
        this.user.setId(u.getId());
    }

    public void sendButtonPressed(){
        this.service = new ServiceManager();
        this.friend = new User(Name.getText(),0);
        Iterable<User> users = repoU.findAll();
        Iterator<User> it = users.iterator();
        while (it.hasNext()){
            User u = it.next();
            if (u.getName().equals(this.friend.getName())) {
                this.friend.setId(u.getId());
                this.friend.setAge(u.getAge());
            }
        }

        if (this.friend.getId() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Fail");
            alert.setContentText("This user doesn't exists");
            alert.show();
            return;
        }

        if (this.friend.equals(this.user)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Fail");
            alert.setContentText("Can't be the same user");
            alert.show();
            return;
        }

        Request r = new Request(this.user.getName(),this.friend.getName(),"pending",LocalDate.now());
        Request r1 = new Request(this.friend.getName(),this.user.getName(),"pending",LocalDate.now());
        if (this.service.findRequest(r) || this.service.findRequest(r1)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Fail");
            alert.setContentText("This request already exists");
            alert.show();
            return;
        }

        if (this.service.findFriendship(this.user,this.friend)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Fail");
            alert.setContentText("This users are already friends");
            alert.show();
            return;
        }

        this.service.sendRequest(r);

        Stage thisStage = (Stage) sendButton.getScene().getWindow();
        thisStage.close();
    }
}
