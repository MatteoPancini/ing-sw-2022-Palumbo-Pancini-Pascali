package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;


public class Controller {
    private final Game game;
    private final GameHandler gameHandler;
    private final RoundHandler roundHandler;

    public Controller(Game game, GameHandler gameHandler){
        this.game = game;
        this.gameHandler = gameHandler;

    }
}


