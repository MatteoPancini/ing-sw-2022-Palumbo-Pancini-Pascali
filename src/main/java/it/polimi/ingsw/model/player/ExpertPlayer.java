package it.polimi.ingsw.model.player;

import java.io.Serializable;

public class ExpertPlayer extends Player implements Serializable {
    private int myCoins;

    public ExpertPlayer(String name, int playerID) {
        super(name, playerID);
        myCoins = 1;
    }

    public int getMyCoins() {
        return myCoins;
    }
}