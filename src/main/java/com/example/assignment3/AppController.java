package com.example.assignment3;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.security.Key;

public class AppController {
    SMModel model;

    double prevX;

    double prevY;

    double dX,dY;



    public void handleKeyPress(KeyEvent keyEvent) {
        switch(keyEvent.getCode()) {
            case DELETE -> model.deleteNode();

        }
    }


    enum state {
        READY,
        PREPARE,
        DRAGGING

    }

    state CurrentState = state.READY;
    InteractionModel imodel;

    public AppController(){}
    public void handlePressed(MouseEvent event) {
        if(CurrentState == state.READY && imodel.selectedTool == InteractionModel.Tool.POINTER){
            if (model.hitNode(event.getX(),event.getY())) {

                SMStateNode b = model.whichHit(event.getX(),event.getY());
                imodel.setSelectedNode(b);

                prevX = event.getX();
                prevY = event.getY();

               CurrentState = state.DRAGGING;
            }
            else{
                model.addNode(event.getX() - imodel.regionX,event.getY() - imodel.regionY,100,50);}

        }
        else if (CurrentState == state.READY && imodel.selectedTool == InteractionModel.Tool.LINK){

            if (model.hitNode(event.getX(),event.getY())) {
                SMStateNode b = model.whichHit(event.getX(),event.getY());


                prevX = event.getX();
                prevY = event.getY();
                if(b.getClass() != SMRect.class){
                model.addLink(event.getX(),event.getY(),event.getX(),event.getY());
                model.tempLink.startNode = b;
                CurrentState = state.PREPARE;}


            }
        }
        else if (CurrentState == state.READY && imodel.selectedTool == InteractionModel.Tool.MOVE){
            prevX = event.getX();
            prevY = event.getY();

            CurrentState = state.DRAGGING;
        }



    }





    public void handleReleased(MouseEvent event) {

        if (CurrentState == state.PREPARE && imodel.selectedTool == InteractionModel.Tool.LINK){
            if (model.hitNode(event.getX(),event.getY())) { //target node, make permanent transition link here
                SMStateNode b = model.whichHit(event.getX(),event.getY());
                if(b.getClass() != SMRect.class){


                model.tempLink.endNode = b;
                model.addTransitionLink(model.tempLink.startNode,b);

               model.removeTempLink();




            }
            model.removeTempLink();}

        }
         CurrentState = state.READY;


    }

    public void handleDragged(MouseEvent event) {
        if(CurrentState == state.DRAGGING && imodel.selectedTool == InteractionModel.Tool.POINTER){

            dX = event.getX() - prevX;
            dY = event.getY() - prevY;
            prevX = event.getX();
            prevY = event.getY();


            model.moveNode(imodel.getSelectedNode(), dX,dY);

        }

        else if(CurrentState == state.PREPARE && imodel.selectedTool == InteractionModel.Tool.LINK){


            dX = event.getX() - prevX;
            dY = event.getY() - prevY;
            prevX = event.getX();
            prevY = event.getY();
            model.extendLink(model.tempLink,dX,dY);

        }

        else if (CurrentState == state.DRAGGING && imodel.selectedTool == InteractionModel.Tool.MOVE){

            dX = event.getX() - prevX;
            dY = event.getY() - prevY;
            prevX = event.getX();
            prevY = event.getY();
            imodel.moveScrollRegion(dX,dY);

        }

    }




    public void setModel(SMModel m) {
       model = m;
    }

    public void setIModel(InteractionModel im) {
        imodel= im;
    }

    public void handleToolClick(InteractionModel.Tool pointer) {
        if(pointer == InteractionModel.Tool.POINTER){
            imodel.setSelectedTool(InteractionModel.Tool.POINTER);

        }
        else if (pointer == InteractionModel.Tool.LINK){
            imodel.setSelectedTool(InteractionModel.Tool.LINK);
        }
        else if (pointer == InteractionModel.Tool.MOVE){
            imodel.setSelectedTool(InteractionModel.Tool.MOVE);

        }
    }

    public void handleNodeUpdate(String text) {
        SMItem  node = imodel.getSelectedNode();
        model.setText(node,text);


    }

    public void handleNodeUpdateTransition(String Event ,String Context ,String SideEffect) {
        SMItem  node = imodel.getSelectedNode();
        model.setInfo(node,Event,Context,SideEffect);
    }
}
