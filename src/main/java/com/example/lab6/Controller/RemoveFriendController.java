package com.example.lab6.Controller;

import com.example.lab6.Domain.models.Request;
import com.example.lab6.Domain.models.User;
import com.example.lab6.Repo.FriendshipsDBRepo;
import com.example.lab6.Service.ServiceManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

public class RemoveFriendController {
    ObservableList<User> list;

    String url = "jdbc:postgresql://localhost:5432/lab5";

    FriendshipsDBRepo repo = new FriendshipsDBRepo(url,"postgres","18oct2001");

    ServiceManager service;

    @FXML
    TextField Name;

    @FXML
    TextField Age;

    @FXML
    Button removeButton;

    User user;

    User friend;

    public void setUser(User u){
        this.user = new User(u.getName(),u.getAge());
        this.user.setId(u.getId());
    }

    @FXML
    public void removeButtonPressed(){
        this.service = new ServiceManager();

        if (Name.getText() == null && Age.getText() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Fail");
            alert.setContentText("Fields can't be null");
            return;
        }

        int val = Integer.parseInt(Age.getText());
        this.friend = new User(Name.getText(),val);
        List<User> users = this.service.findAllUsers();
        Iterator<User> it = users.iterator();
        while (it.hasNext()){
            User u = it.next();
            if (u.getName().equals(this.friend.getName()) && u.getAge() == this.friend.getAge())
                this.friend.setId(u.getId());
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

        if (!this.service.findFriendship(this.user,this.friend)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Fail");
            alert.setContentText("This users are not friends!");
            alert.show();
            return;
        }

        this.service.removeFriend(this.user,this.friend);
        Request r = this.service.getRequestByUsers(this.user,this.friend);
        if (r != null){
            this.service.removeRequest(r);
        }

        Stage thisStage = (Stage) removeButton.getScene().getWindow();
        thisStage.close();
    }
}
