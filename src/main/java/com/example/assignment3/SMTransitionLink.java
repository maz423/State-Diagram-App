package com.example.assignment3;

import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class SMTransitionLink extends SMItem{

    SMStateNode startNode;

    SMStateNode endNode;

    SMRect TextBox;


    double linkStartX, linkStartY,linkStartX1,linkStartY1;
    double linkEndX, linkEndY, linkEndX1,linkEndY1;
    double rectX, rectY;

    double circleX,circleY,circleDiametre;
    boolean self = false;

    double ArrowSX, ArrowSY, ArrowSX1,ArrowYS1,ArrowEX, ArrowEY, ArrowSE1,ArrowYE1;


    boolean temp;

    public SMTransitionLink(SMStateNode start, SMStateNode end){
       startNode = start;
       endNode = end;

       calcRect();
       calcS();
       calcSe();
       calcE();
       calcEe();
       if(start == end ){
           self = true;
           calcCircle();
       }



    }


    public void calcS(){
        HashMap<String,Double> st = calcStarts(startNode,TextBox);
        linkStartX = st.get("x");
        linkStartY = st.get("y");
    }

    public void calcSe(){
        HashMap<String,Double> st = calcStarts(TextBox,startNode);
        linkStartX1 = st.get("x");
        linkStartY1 = st.get("y");
    }

    public void calcE(){
        HashMap<String,Double> ed = calcStarts(TextBox,endNode);
        linkEndX = ed.get("x");
        linkEndY = ed.get("y");
    }

    public void calcEe(){
        HashMap<String,Double> ed = calcStarts(endNode,TextBox);
        linkEndX1 = ed.get("x");
        linkEndY1 = ed.get("y");
    }

    public HashMap<String,Double> calcStarts(SMStateNode start, SMStateNode end){

        //Convenience vars
        double STopLeftX, STopLeftY, STopRightX, STopRightY, SBottomLeftX, SBottomLeftY, SBottomRightX, SBottomRightY;
        double ETopLeftX, ETopLeftY, ETopRightX, ETopRightY, EBottomLeftX, EBottomLeftY, EBottomRightX, EBottomRightY;

        HashMap<String,Double> pts = new HashMap<>();
        //START NODE
        STopLeftX = start.x;
        STopLeftY = start.y;
        STopRightX = STopLeftX + start.Width;
        STopRightY = STopLeftY;
        SBottomLeftX = STopLeftX;
        SBottomLeftY = STopLeftY + start.Height;
        SBottomRightX = STopRightX;
        SBottomRightY = STopRightY + start.Height;


        //END NODE
        ETopLeftX = end.x;
        ETopLeftY = end.y;
        ETopRightX = ETopLeftX + end.Width;
        ETopRightY = ETopLeftY;
        EBottomLeftX = ETopLeftX;
        EBottomLeftY = ETopLeftY + end.Height;
        EBottomRightX = ETopRightX;
        EBottomRightY = ETopRightY + end.Height;


        if((EBottomRightX < STopLeftX && EBottomRightY > STopLeftY) ||( ETopRightX < SBottomLeftX && ETopRightY < SBottomLeftY)){ //attach to left of node
              double linkX = STopLeftX;
              double linkY = STopLeftY + startNode.Height/2;
              pts.put("x",linkX);
              pts.put("y",linkY);
              start.LeftOccupied = true;

        }
        else if ((EBottomLeftX > STopRightX && EBottomLeftY > STopRightY) || ETopLeftX > SBottomRightX && ETopLeftY < SBottomRightY){//attach to right of node
              double linkX = STopRightX;
              double linkY = STopRightY + startNode.Height/2;
              pts.put("x",linkX);
              pts.put("y",linkY);
              start.RightOccupied = true;

        }

        else if ((EBottomRightX > STopLeftX && EBottomRightY < STopLeftY) || (EBottomLeftX < STopRightX && EBottomLeftY < STopRightY)){
            double linkX = STopLeftX + startNode.Width/2;
            double linkY = STopLeftY;
            pts.put("x",linkX);
            pts.put("y",linkY);
            start.TopOccupied = true;
        }

        else if ((ETopRightX > SBottomLeftX && ETopRightY > SBottomLeftY) || (ETopLeftX < SBottomRightX && ETopLeftY > SBottomRightY ) ){
            double linkX = SBottomLeftX + startNode.Width/2;
            double linkY = SBottomLeftY;
            pts.put("x",linkX);
            pts.put("y",linkY);
            start.BottomOccupied = true;
        }

        else{


            double linkX = STopRightX;
            double linkY = STopRightY + startNode.Height/2;
            pts.put("x",linkX);
            pts.put("y",linkY);
            start.RightOccupied = true;

        }





        return pts;

    }


    public void calcCircle(){

        if(startNode == endNode){


        TextBox.x = startNode.x + startNode.Width + 60;
        TextBox.y = startNode.y -75;


        ArrowSX = startNode.x + startNode.Width;
        ArrowSY = startNode.y + startNode.Height/2;

        ArrowEX = TextBox.x;
        ArrowEY = TextBox.y + TextBox.Height/2;

        circleX = ArrowSX;
        circleY = ArrowSY -25;
        circleDiametre = Math.sqrt(Math.pow(ArrowEX-ArrowSX,2) + Math.pow(ArrowEY-ArrowSY,2));}
    }

    public void calcRect(){

        rectX = (startNode.x + endNode.x)/2;
        rectY  = (startNode.y + endNode.y)/2;
        TextBox = new SMRect(rectX,rectY,130,200);
    }

    public void ReCalcRec(){
        TextBox.x = (startNode.x + endNode.x)/2;
        TextBox.y  = (startNode.y + endNode.y)/2;

    }


    public boolean contains(double cx, double cy) {

        double left = rectX;
        double right = rectX + 100;
        double top = rectY;
        double bottom =  rectY + 300;
        if(cx > left && cx < right && cy > top && cy < bottom ){
            return true;
        }
        else{
            return false;
        }

    }

    public void setInfo(String event, String context, String sideEffect) {
        TextBox.Info.clear();
        TextBox.Info.put("Event",event);
        TextBox.Info.put("Context",context);
        TextBox.Info.put("Side-effect",sideEffect);
    }


}
