package com.example.concentration;

public class Player {
    private String name;
    private int score;
    Player(String n, int s){
        name = n;
        score = s;
    }
    public String getName(){
        return name;
    }
    public int getScore(){
        return score;
    }
}
