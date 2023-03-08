package com.example.lab6;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/view/loginPage.fxml"));
        Scene scene = new Scene(loader.load(),500,400);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
