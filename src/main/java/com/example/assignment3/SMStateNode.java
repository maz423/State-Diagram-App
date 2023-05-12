package com.example.assignment3;

import java.util.List;

public class SMStateNode extends SMItem{

 double x;
 double y;

 double Width;

 double Height;

 String text = "default";

 SMModel model;

 boolean LeftOccupied, RightOccupied, TopOccupied, BottomOccupied;






 public SMStateNode(double nx,double ny,double nw,double nh){
     x = nx;
     y = ny;
     Width = nw;
     Height= nh;
     LeftOccupied = false;
     RightOccupied = false;
     TopOccupied = false;
     BottomOccupied = false;



 }



 public void setModel(SMModel m){
     model = m;
 }



 private double dist(double x1,double y1,double x2, double y2) {
        return Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
    }

    public boolean contains(double cx, double cy) {


        double nx = x + model.imodel.regionX;
        double  ny = y + model.imodel.regionY;

        double left = nx;
        double right = nx + Width;
        double top = ny;
        double bottom =  ny + Height;


        if(cx > left && cx < right && cy > top && cy < bottom ){
            return true;
        }
        else{
            return false;
        }

    }


    public void move(double dX, double dY) {
     x += dX;
     y += dY;
    }

    public void setText(String new_text) {
     text = new_text;

    }
}
