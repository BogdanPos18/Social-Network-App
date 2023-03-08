package com.example.lab6.Controller;

import com.example.lab6.Domain.models.Client;
import com.example.lab6.Repo.ClientsDBRepo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Iterator;

public class SignupController {
    private String url = "jdbc:postgresql://localhost:5432/lab5";

    private ClientsDBRepo repo = new ClientsDBRepo(url,"postgres","18oct2001");

    @FXML
    TextField signName;

    @FXML
    PasswordField signPassword;

    @FXML
    Button signupButton;

    Client client;

    @FXML
    public void signupButtonPressed(){
        String username = signName.getText();
        String password = signPassword.getText();
        this.client = new Client(username,password);

        Iterable<Client> clients = repo.findAll();
        Iterator<Client> it = clients.iterator();
        int ok = 0;
        while (it.hasNext()){
            Client c = it.next();
            if (c.getUsername().equals(username))
                ok = 1;
        }

        if (ok == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Fail");
            alert.setContentText("This username already exists");
            alert.show();
        }

        this.repo.save(this.client);

        Stage thisStage = (Stage) signupButton.getScene().getWindow();
        thisStage.close();
    }
}
