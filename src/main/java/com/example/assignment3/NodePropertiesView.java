package com.example.assignment3;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class NodePropertiesView extends StackPane {

    Button Update;
    TextField name;
    public NodePropertiesView(){

        VBox root = new VBox(10);
        root.setStyle("-fx-border-color: Black");
        HBox container = new HBox();
        container.setStyle("-fx-background-color: grey");
        Label state = new Label("            State            \n\n");
        Label Sname = new Label("State name:");
        name = new TextField("Default");
        container.getChildren().add(state);
        Update = new Button("update");
        root.getChildren().addAll(container,Sname,name,Update);

        this.getChildren().add(root);

    }


    public void setController( AppController controller){
        Update.setOnAction(e -> controller.handleNodeUpdate(name.getText()));


    }
}
