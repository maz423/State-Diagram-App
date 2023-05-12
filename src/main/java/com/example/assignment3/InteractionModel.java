package com.example.assignment3;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class InteractionModel {




    enum Tool {
        POINTER,
        MOVE,
        LINK
    }

    Tool selectedTool = Tool.POINTER;

    SMItem selectedNode;

    double viewWidth,viewHeight, worldWidth, worldHeight;

    double  scrollX, scrollY;

    double regionX, regionY;

    public SMStateNode Viewport;


    private ArrayList<IModelListener> subscribers;

    public InteractionModel() {
        subscribers = new ArrayList<>();
        scrollX = 0;
        scrollY = 0;
        regionX = 0;
        regionY = 0;
        Viewport = new SMStateNode(0,0,800.0/2,800.0/2);
    } //set selected color at startup

    public void setSelectedTool(Tool newTool) {
        selectedTool = newTool;
        notifySubscribers();
    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.iModelChanged());
    }

    public void addSubscriber(IModelListener sub) { // for version 2
        subscribers.add(sub);
    }

    public Tool getSelectedTool(){
        return selectedTool;
    }

    public SMItem getSelectedNode(){
        return selectedNode;
    }

    public void setSelectedNode(SMStateNode node){

        selectedNode = node;
        notifySubscribers();
    }

    public void setWorldWidth(double w) {
        worldWidth = w;
    }

    public void setViewWidth(double w) {
        viewWidth = w;
    }

    public void setViewHeight(double h) {
        viewHeight = h;
    }

    public void moveScrollRegion(double dX,double dY) { //might need one for Y as well


       double left = Viewport.x - dX/2;
       double right = Viewport.x -dX/2 + Viewport.Width;
       double top = Viewport.y - dY/2;
       double bottom = Viewport.y - dY/2 +Viewport.Height;




       if(left >= 0 && right <=  viewWidth  && top >= 0 && bottom <= viewHeight){
           Viewport.move(-dX/2,-dY/2);
           regionX += dX;
           regionY += dY;
       }

       notifySubscribers();
    }

    public void unselect() {
        selectedNode = null;
        notifySubscribers();
    }



}
