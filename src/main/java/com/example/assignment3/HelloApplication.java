package com.example.assignment3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainUI root = new MainUI();
        Scene scene = new Scene(root);
        stage.setTitle("Editor App");

        stage.setScene(scene);


        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}