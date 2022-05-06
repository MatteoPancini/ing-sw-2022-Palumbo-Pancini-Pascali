package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.Wizards;


public class Controller {
    private final Game game;
    private final GameHandler gameHandler;
    //private final TurnController turnController;

    public Controller(Game game, GameHandler gameHandler) {
        this.game = game;
        this.gameHandler = gameHandler;

    }


    public void setPlayerWizard(int playerID, Wizards chosenWizard) {
        game.getPlayerByID(playerID).setWizard(chosenWizard);
    }


    public void setupGame() {
        //operazioni di setup
        //turnController.pianificationPhase();

    }

}


