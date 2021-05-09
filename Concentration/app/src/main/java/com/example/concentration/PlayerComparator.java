package com.example.concentration;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        if(p1.getScore() < p2.getScore()){
            return 1;
        }
        else if (p1.getScore() > p2.getScore()){
            return -1;
        }
        else{
            return 0;
        }
    }
}
