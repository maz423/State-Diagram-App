package com.example.assignment3;

import java.util.Hashtable;

public class SMRect extends SMStateNode{
    Hashtable <String,String> Info;
    public SMRect(double nx, double ny, double nw, double nh) {
        super(nx, ny, nw, nh);
        Info = new Hashtable<>();
        Info.put("Event","No Event");
        Info.put("Context","No Context");
        Info.put("Side-effect","No Side effect");
    }
}
