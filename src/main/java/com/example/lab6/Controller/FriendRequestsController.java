package com.example.lab6.Controller;

import com.example.lab6.Domain.models.Friendship;
import com.example.lab6.Domain.models.Request;
import com.example.lab6.Domain.models.User;
import com.example.lab6.Service.ServiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class FriendRequestsController {
    ObservableList<Request> modelRequest = FXCollections.observableArrayList();

    private ServiceManager service;

    @FXML
    TableColumn<Request, String> tableColumnFrom;

    @FXML
    TableColumn<Request, String> tableColumnDate;

    @FXML
    TableColumn<Request, String> tableColumnStatus;

    @FXML
    TableView<Request> tableViewRequests;

    @FXML
    Button confirmButton;

    @FXML
    Button removeButton;

    @FXML
    public void initialize(){
        tableColumnFrom.setCellValueFactory(new PropertyValueFactory<>("nameFrom"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableViewRequests.setItems(modelRequest);
    }

    private List<Request> getRequestsList(){
        return service.findAllRequests()
                .stream()
                .map(r -> new Request(r.getNameFrom(),r.getNameTo(),r.getStatus(),r.getDate()))
                .collect(Collectors.toList());
    }

    public void setRequestsList(ObservableList<Request> l){
        this.modelRequest = l;
    }

    public void setTable(){
        tableColumnFrom.setCellValueFactory(new PropertyValueFactory<>("nameFrom"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableViewRequests.setItems(modelRequest);
    }

    public void setService(){
        this.service = new ServiceManager();
        this.modelRequest.setAll(getRequestsList());
    }

    @FXML
    public void confirmButtonClicked(){
        Request r = tableViewRequests.getSelectionModel().getSelectedItem();
        Iterator<Request> it = this.service.findAllRequests().iterator();
        while (it.hasNext()){
            Request r1 = it.next();
            if (r1.getNameTo().equals(r.getNameTo())){
                r.setId(r1.getId());
                r.setNameFrom(r1.getNameFrom());
                r.setStatus(r1.getStatus());
                r.setDate(r1.getDate());
            }
        }

        Iterator<User> itu = this.service.findAllUsers().iterator();
        User u1 = new User(r.getNameFrom(),0);
        User u2 = new User(r.getNameTo(),0);
        while (itu.hasNext()) {
            User u = itu.next();
            if (u.getName().equals(u1.getName()) && u1.getId() == null) {
                u1.setId(u.getId());
                u1.setAge(u.getAge());
            }
            if (u.getName().equals(u2.getName()) && u2.getId() == null) {
                u2.setId(u.getId());
                u2.setAge(u.getAge());
            }
        }

        if (r.getStatus().equals("accepted")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Fail");
            alert.setContentText("This users are already friends!");
            alert.show();
            return;
        }

        if (r.getStatus().equals("pending"))
            this.service.addFriend(u1,u2);

        this.service.updateRequest(r,"accepted");

        Stage thisStage = (Stage) confirmButton.getScene().getWindow();
        thisStage.close();
    }
    @FXML
    public void removeRequestButtonClicked(){
        Request r = tableViewRequests.getSelectionModel().getSelectedItem();
        Iterator<Request> it = this.service.findAllRequests().iterator();
        while (it.hasNext()){
            Request r1 = it.next();
            if (r1.getNameTo().equals(r.getNameTo())){
                r.setId(r1.getId());
                r.setNameFrom(r1.getNameFrom());
                r.setStatus(r1.getStatus());
                r.setDate(r1.getDate());
            }
        }

        Iterator<User> itu = this.service.findAllUsers().iterator();
        User u1 = new User(r.getNameFrom(),0);
        User u2 = new User(r.getNameTo(),0);
        while (itu.hasNext()){
            User u = itu.next();
            if (u.getName().equals(u1.getName()) && u1.getId() == null)
                u1.setId(u.getId());
            if (u.getName().equals(u2.getName()) && u2.getId() == null)
                u2.setId(u.getId());
        }

        this.service.removeRequest(r);

        Stage thisStage = (Stage) removeButton.getScene().getWindow();
        thisStage.close();
    }
}
