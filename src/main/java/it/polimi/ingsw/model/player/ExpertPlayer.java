package it.polimi.ingsw.model.player;

public class ExpertPlayer extends Player {
    private int myCoins;

    public ExpertPlayer(String name) {
        super(name);
    }

    public int getMyCoins() {
        return myCoins;
    }
}