package com.example.lab6.Controller;

import com.example.lab6.Domain.models.Request;
import com.example.lab6.Domain.models.User;
import com.example.lab6.Service.ServiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class UserController {
    ObservableList<User> modelUser = FXCollections.observableArrayList();

    ObservableList<Request> modelRequests = FXCollections.observableArrayList();

    public ServiceManager service;

    @FXML
    TableColumn<User, String> tableColumnName;

    @FXML
    TableColumn<User, String> tableColumnAge;

    @FXML
    TableView<User> tableViewUsers;

    @FXML
    Button sendRequestButton;

    @FXML
    Button addFriendButton;

    @FXML
    Button removeFriendButton;

    @FXML
    Button allFriendsButton;

    @FXML
    Button friendRequestsButton;

    @FXML
    public void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableViewUsers.setItems(modelUser);
    }

    private List<User> getUsersList() {
        return service.findAllUsers()
                .stream()
                .map(u -> new User(u.getName(), u.getAge()))
                .collect(Collectors.toList());
    }

    private List<User> getFriendsList(User user){
        List<User> friends =  service.findFriends(user)
                              .stream()
                              .map(u -> new User(u.getName(), u.getAge()))
                              .collect(Collectors.toList());
        List<User> users = service.findFriends(user);
        List<User> users1 = new ArrayList<>();
        Iterator<User> it = friends.iterator();
        while (it.hasNext()){
            User u = it.next();
            Iterator<User> it1 = users.iterator();
            while (it1.hasNext()){
                User u1 = it1.next();
                if (u.getName().equals(u1.getName()) && u.getAge() == u1.getAge())
                    u.setId(u1.getId());
            }
            users1.add(u);
        }
        return users1;
    }

    private List<Request> getRequestsList(User user){
        List<Request> requests = service.findUserRequests(user)
                              .stream()
                              .map(r -> new Request("",user.getName(),"", LocalDate.now()))
                              .collect(Collectors.toList());
        List<Request> req = service.findUserRequests(user);
        List<Request> req1 = new ArrayList<>();
        Iterator<Request> it = req.iterator();
        while (it.hasNext()){
            Request r = it.next();
            Iterator<Request> it1 = requests.iterator();
            while (it1.hasNext()){
                Request r1 = it1.next();
                if (r1.getNameTo().equals(r.getNameTo())) {
                    r1.setId(r.getId());
                    r1.setNameFrom(r.getNameFrom());
                    r1.setStatus(r.getStatus());
                    r1.setDate(r.getDate());
                }
            }
            req1.add(r);
        }
        return req1;
    }

    public void setService(ServiceManager service) {
        this.service = service;
        modelUser.setAll(getUsersList());
    }

    @FXML
    public void sendRequestButtonClicked() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sendRequestView.fxml"));
        AnchorPane root = loader.load();

        User user = tableViewUsers.getSelectionModel().getSelectedItem();

        SendRequestController ctrl = loader.getController();
        ctrl.setUser(user);

        Stage stage = new Stage();
        stage.setScene(new Scene(root,550,400));
        stage.show();
    }

    @FXML
    public void addFriendButtonClicked() throws IOException{
        FXMLLoader loaderf = new FXMLLoader(getClass().getResource("/view/addFriendView.fxml"));
        FXMLLoader loadera = new FXMLLoader(getClass().getResource("/view/allFriendsView.fxml"));

        AnchorPane rootf = loaderf.load();
        AnchorPane roota = loadera.load();

        AddFriendController ctrlf = loaderf.getController();
        User user = tableViewUsers.getSelectionModel().getSelectedItem();
        List<User> users = service.findAllUsers();

        Iterator<User> it = users.iterator();
        while (it.hasNext()){
            User u = it.next();
            if (user.getName().equals(u.getName()) && (user.getAge() == u.getAge()))
                user.setId(u.getId());
        }

        AllFriendsController ctrla = loadera.getController();
        ObservableList<User> friends = FXCollections.observableList(getFriendsList(user));
        ctrla.setFriendsList(friends);

        ctrlf.setUser(user);

        Stage stage = new Stage();
        stage.setScene(new Scene(rootf,400,300));
        stage.show();
    }

    @FXML
    public void removeFriendButtonClicked() throws IOException{
        FXMLLoader loaderf = new FXMLLoader(getClass().getResource("/view/removeFriendView.fxml"));
        FXMLLoader loadera = new FXMLLoader(getClass().getResource("/view/allFriendsView.fxml"));

        AnchorPane rootf = loaderf.load();
        AnchorPane roota = loadera.load();

        RemoveFriendController ctrlf = loaderf.getController();
        User user = tableViewUsers.getSelectionModel().getSelectedItem();
        List<User> users = service.findAllUsers();

        Iterator<User> it = users.iterator();
        while (it.hasNext()){
            User u = it.next();
            if (user.getName().equals(u.getName()) && user.getAge() == u.getAge())
                user.setId(u.getId());
        }

        AllFriendsController ctrla = loadera.getController();
        ObservableList<User> friends = FXCollections.observableList(getFriendsList(user));
        ctrla.setFriendsList(friends);

        ctrlf.setUser(user);

        Stage stage = new Stage();
        stage.setScene(new Scene(rootf,400,300));
        stage.show();
    }

    @FXML
    public void allFriendsButtonClicked() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/allFriendsView.fxml"));
        AnchorPane root = loader.load();
        AllFriendsController ctrl = loader.getController();

        User user = tableViewUsers.getSelectionModel().getSelectedItem();
        ObservableList<User> friends = FXCollections.observableList(getFriendsList(user));
        List<User> users = service.findAllUsers();
        Iterator<User> it = users.iterator();
        while (it.hasNext()){
            User u = it.next();
            if (u.getName().equals(user.getName()) && u.getAge() == user.getAge())
                user.setId(u.getId());
        }
        ctrl.setFriendsList(friends);
        ctrl.setTable();

        Stage stage = new Stage();
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }

    @FXML
    public void friendRequestsButtonClicked() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/friendRequestsView.fxml"));
        AnchorPane root = loader.load();

        FriendRequestsController ctrl = loader.getController();
        User user = tableViewUsers.getSelectionModel().getSelectedItem();
        ObservableList<Request> requests = FXCollections.observableArrayList(getRequestsList(user));
        ctrl.setService();
        ctrl.setRequestsList(requests);
        ctrl.setTable();

        Stage stage = new Stage();
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }
}
