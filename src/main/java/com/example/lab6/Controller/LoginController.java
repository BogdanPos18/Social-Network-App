package com.example.lab6.Controller;
import com.example.lab6.Domain.models.Client;
import com.example.lab6.Repo.ClientsDBRepo;
import com.example.lab6.Service.ServiceManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Iterator;

public class LoginController {
    private String url = "jdbc:postgresql://localhost:5432/lab5";
    private ClientsDBRepo repo = new ClientsDBRepo(url, "postgres","18oct2001");
    @FXML
    TextField loginName;

    @FXML
    PasswordField password;

    @FXML
    Button loginButton;

    @FXML
    Button firstSignupButton;

    @FXML
    void loginButtonClicked() throws IOException{
        Iterable<Client> clients = repo.findAll();
        Iterator<Client> it = clients.iterator();
        int okU = 0;
        int okP = 0;
        while (it.hasNext()){
            Client c = it.next();
            if (loginName.getText().equals(c.getUsername()))
                okU = 1;
            if (password.getText().equals(c.getPassword()))
                okP = 1;
        }

        if (okU == 0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Fail");
            alert.setContentText("Invalid username");
            alert.show();
            return;
        }

        if (okP == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Fail");
            alert.setContentText("Incorrect password");
            alert.show();
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/userView.fxml"));
        AnchorPane root = loader.load();

        ServiceManager service = new ServiceManager();

        UserController ctrl = loader.getController();
        ctrl.setService(service);

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 650, 450 ));
        stage.show();

        Stage thisStage =(Stage) loginButton.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    void signupButtonClicked() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signupView.fxml"));
        AnchorPane root = loader.load();

        SignupController ctrl = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }
}
