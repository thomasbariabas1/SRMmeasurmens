package com.example.tom.srmmeasurmens;


import android.app.Application;

public class Main extends Application {
    static String ip;
    public  static String getip() {
        return ip;
    }
    public static void setip(String ip2){ip=ip2;}
}
