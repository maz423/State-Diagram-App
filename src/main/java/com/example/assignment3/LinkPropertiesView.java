package com.example.assignment3;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LinkPropertiesView extends StackPane {

    TextField name;

    TextField Cname;

    TextField SFname;

    Button Update;

    public LinkPropertiesView(){

        VBox root = new VBox(10);
        root.setStyle("-fx-border-color: Black");
        HBox container = new HBox();
        container.setStyle("-fx-background-color: grey");
        Label state = new Label("            Transition            \n\n");
        Label Event = new Label("Event:");
        name = new TextField("No Event");
        Label Context = new Label("Context:");
        Cname = new TextField("No Context");
        Label sideEffect = new Label("Side-Effect:");
        SFname = new TextField("No Side effect");
        container.getChildren().add(state);
        Update = new Button("update");
        root.getChildren().addAll(container,Event,name,Context,Cname,sideEffect,SFname,Update);

        this.getChildren().add(root);
    }

    public void setController(AppController controller){
        Update.setOnAction(e -> controller.handleNodeUpdateTransition(name.getText(),Cname.getText(),SFname.getText()));
    }
}
