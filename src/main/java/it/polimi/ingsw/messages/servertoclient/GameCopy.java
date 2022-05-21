package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.model.Game;

//TODO: NON VIENE SERIALIZZATA

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
