package com.example.assignment3;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ToolPalette extends StackPane implements  IModelListener{

    InteractionModel iModel;

    Button pointer;
    Button link;
    Button move;



    
    public ToolPalette(){
    VBox root = new VBox();
        this.setMaxWidth(100);
        this.setMinWidth(20);
        Image Ar = new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzRNEe94PGeRMFZmJmnDD_TSBvaDJ4LakPOWIvlByRGAxilopl");
        ImageView view = new ImageView(Ar);

        view.setFitHeight(50);
        view.setFitWidth(50);

        Image pan = new Image("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQ7lHtBQL8Emgm_CtatxlyxIGFCHE6meksd0WobScRmWgOWLSTf");
        ImageView view1 = new ImageView(pan);

        view1.setFitHeight(50);
        view1.setFitWidth(50);

        Image li = new Image("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQ8DM8NHiZgLj0YeQ534jwAvK-qDiGaqYAlxHI28osz-i2n067k");
        ImageView view2 = new ImageView(li);

        view2.setFitHeight(50);
        view2.setFitWidth(50);



    root.setStyle("-fx-border-color: black");
    pointer = new Button();
    pointer.setGraphic(view);
        pointer.setPadding(new Insets(0, 0, 0, 0));


        pointer.setStyle("-fx-border-color: red");
    link = new Button();

        link.setGraphic(view2);
        link.setPadding(new Insets(0, 0, 0, 0));
        link.setStyle("-fx-border-color: Aqua");
    move = new Button();

        move.setGraphic(view1);
        move.setPadding(new Insets(0, 0, 0, 0));
        move.setStyle("-fx-border-color: Aqua");










        root.getChildren().addAll(pointer,move,link);
    this.getChildren().add(root);
    }

    public void setInteractionModel(InteractionModel im) {
        iModel = im;
    }

    public void setController(AppController controller) {

        pointer.setOnAction(e -> controller.handleToolClick(InteractionModel.Tool.POINTER));
        move.setOnAction(e -> controller.handleToolClick(InteractionModel.Tool.MOVE));
        link.setOnAction(e -> controller.handleToolClick(InteractionModel.Tool.LINK));
    }

    @Override
    public void iModelChanged() {
        if(iModel.selectedTool == InteractionModel.Tool.POINTER){
            pointer.setStyle("-fx-border-color: red");
            move.setStyle("-fx-border-color: aqua");
            link.setStyle("-fx-border-color: aqua");
        }
        else if (iModel.selectedTool == InteractionModel.Tool.LINK){
            link.setStyle("-fx-border-color: red");
            pointer.setStyle("-fx-border-color: aqua");
            move.setStyle("-fx-border-color: aqua");
        }
        else if (iModel.selectedTool == InteractionModel.Tool.MOVE){
            move.setStyle("-fx-border-color: red");
            pointer.setStyle("-fx-border-color: aqua");
            link.setStyle("-fx-border-color: aqua");
        }
    }


    public void setiModel(InteractionModel im){

        iModel = im;

    }


}

