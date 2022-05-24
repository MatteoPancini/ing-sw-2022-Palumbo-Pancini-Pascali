package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.model.Game;

public class GameCopy implements Answer {
    private Game gameCopy;

    public GameCopy(Game gameCopy){
        this.gameCopy = gameCopy;
    }


    @Override
    public Game getMessage() {
        return gameCopy;
    }
}
