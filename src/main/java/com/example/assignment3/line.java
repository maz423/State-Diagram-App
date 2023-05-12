package com.example.assignment3;

public class line {

    double x1,y1;
    double x2,y2;

    SMStateNode startNode;

    SMStateNode endNode;

    public line (double nx1,double ny1, double nx2,double ny2){
        x1 = nx1;
        x2 = nx2;
        y1 = ny1;
        y2 = ny2;

    }

    public void extend(double dX, double dY) {
        x2 += dX;
        y2 += dY;

    }
}
