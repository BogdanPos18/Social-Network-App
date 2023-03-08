package com.example.lab6.Controller;

import com.example.lab6.Domain.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AllFriendsController {
    ObservableList<User> modelFriends = FXCollections.observableArrayList();
    @FXML
    TableColumn<User,String> tableColumnFriendName;

    @FXML
    TableColumn<User,String> tableColumnFriendAge;

    @FXML
    TableView<User> tableViewFriends;

    @FXML
    public void initialize(){
        tableColumnFriendName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnFriendAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableViewFriends.setItems(modelFriends);
    }

    public List<User> getFriendsList(){
        return this.modelFriends;
    }

    public void setFriendsList(ObservableList<User> l){
        this.modelFriends = l;
    }

    public void setTable(){
        tableColumnFriendName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnFriendAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableViewFriends.setItems(modelFriends);
    }
}
