package it.polimi.ingsw.model.player;

public class ExpertPlayer extends Player {
    private int myCoins;

    public ExpertPlayer(String name, int playerID) {
        super(name, playerID);
    }

    public int getMyCoins() {
        return myCoins;
    }
}