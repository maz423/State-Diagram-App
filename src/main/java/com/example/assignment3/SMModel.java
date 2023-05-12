package com.example.assignment3;

import java.util.ArrayList;
import java.util.List;

public class SMModel implements IModelListener{

    private List<ModelListener> subscribers;

    private List<SMStateNode> Nodes;

    public List<SMTransitionLink> Links;
    public InteractionModel imodel;

    public line tempLink;







    public SMModel(){
        Nodes = new ArrayList<>();
        subscribers = new ArrayList<>();
        Links = new ArrayList<>();


    }

    public void setIModel(InteractionModel im){
        imodel = im;



    }

    public void addNode(double x, double y, double w, double h){
        Nodes.add(new SMStateNode(x,y,w,h));
        Nodes.forEach(n -> {
            n.model = this;
        });

        notifySubscribers();
    }

    public List<SMStateNode> getNodes(){
        return Nodes;

    }

    private void notifySubscribers() {
        subscribers.forEach(s -> s.modelChanged());
    }

    public void addSubscriber(DiagramView centre) {
        subscribers.add(centre);
    }

    public boolean hitNode(double x, double y) {
        for (SMStateNode b : Nodes) {
            if (b.contains(x,y)) return true;
        }

        for(SMTransitionLink l : Links){
            if(l.TextBox.contains(x,y)) return true;
        }
        return false;
    }

    public SMStateNode whichHit(double x, double y) {
        for (SMStateNode b : Nodes) {
            if (b.contains(x,y)) return b;
        }

        for(SMTransitionLink l : Links){
            if(l.TextBox.contains(x,y)) return l.TextBox;
        }
        return null;
    }

    public void moveNode(SMItem selectedNode, double dX, double dY) {

        selectedNode.move(dX,dY);
        updateTransitionLink(selectedNode,dX,dY);

        notifySubscribers();
    }



    public void addLink(double x1,double y1,double x2,double y2) {
        tempLink = new line(x1,y1,x2,y2);
        notifySubscribers();
    }

    public void addTransitionLink(SMStateNode start, SMStateNode end) {
        Links.add(new SMTransitionLink(start, end));
        Links.forEach(l -> {
            l.TextBox.model = this;
        });
        notifySubscribers();
    }

    public List<SMTransitionLink> getLinks(){
        return Links;

    }

    public void extendLink( line link, double dX, double dY) {
        link.extend(dX,dY);
        notifySubscribers();
    }

    public void removeTempLink(){
        tempLink = null;
        notifySubscribers();
    }

    public void updateTransitionLink(SMItem node , double dX, double dY) {
        Links.forEach(l -> {
            if(l.startNode == node){
                if(l.startNode == l.endNode){
                    l.linkStartX += dX;
                    l.linkStartY += dY;
                    l.linkEndX1 += dX;
                    l.linkEndY1 += dY;

                }
                else{
                  l.linkStartX += dX;
                  l.linkStartY += dY;}

            }
            else if (l.endNode == node){

                l.linkEndX1 += dX;
                l.linkEndY1 += dY;


//
            }

            else if (l.TextBox == node){
                l.linkStartX1 += dX;
                l.linkStartY1 += dY;
                l.linkEndX += dX;
                l.linkEndY += dY;

            }
            l.calcCircle();







        });
    }

    public boolean hitTransitionLink(double x ,double y){
        for (SMTransitionLink b : Links) {
            if (b.contains(x,y)) return true;
        }
        return false;

    }

    public void setText(SMItem node, String new_text) {
        Nodes.forEach(n ->{
            if(n == node){
                n.setText(new_text);
                notifySubscribers();
            }

        });
    }


    public void setInfo(SMItem node, String Event, String Context, String SideEffect) {
        Links.forEach(l -> {

            if(l.TextBox == node){
                l.setInfo(Event,Context,SideEffect);
                notifySubscribers();
            }
        });

    }

    @Override
    public void iModelChanged() {

    }

    public void deleteNode() {
        if (imodel.selectedNode != null){

            for (int i =0; i < Nodes.size();i++){
                if (Nodes.get(i) == imodel.selectedNode){
                    Nodes.remove(i);
                    for (int k = 0 ; k< Links.size();k++){

                        if(Links.get(k).startNode == imodel.selectedNode || Links.get(k).endNode == imodel.selectedNode){
                            Links.remove(k);
                        }
                    }
                }
            }
            for (int j = 0 ; j< Links.size();j++){

                if(Links.get(j).TextBox == imodel.selectedNode){
                    Links.remove(j);
                }
            }
            notifySubscribers();
        }
    }
}
