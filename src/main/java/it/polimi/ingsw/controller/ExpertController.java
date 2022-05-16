package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.messages.servertoclient.RequestAction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Island;

public class ExpertController {
    private final Game game;
    private final GameBoard board;
    private final TurnController turnController;


    public ExpertController(Game game, GameBoard board, TurnController turnController) {
        this.game = game;
        this.board = board;
        this.turnController = turnController;
    }

    public void heraldEffect() {
        RequestAction islandDestination = new RequestAction(Action.PICK_ISLAND);
        turnController.getGameHandler().sendSinglePlayer(islandDestination, turnController.getCurrentPlayer().getPlayerID());
    }

    public void knightEffect(){
        turnController.getCurrentPlayer().setIslandInfluence(2);
    }


}
