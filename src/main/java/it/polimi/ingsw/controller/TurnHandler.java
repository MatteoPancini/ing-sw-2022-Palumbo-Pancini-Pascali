package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.CloudSide;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;

public class TurnHandler {
    private Game game;
    private final GameHandler gameHandler;
    private final PianificationHandler pianificationHandler;
    private final ActionHandler actionHandler;
    private ArrayList<Player> activePlayers;

    public TurnHandler(PianificationHandler pianificationHandler, ActionHandler actionHandler, GameHandler gameHandler){
        this.pianificationHandler = pianificationHandler;
        this.actionHandler = actionHandler;
        this.gameHandler = gameHandler;
    }

    public void startPianification(){
        gameHandler.putStudentsOnCloud();

        for (int i = 0; i < game.getPlayersNumber(); i++) {
            AssistantCard chosenCard = board.getLastAssistantUsed().get(i).getOwner().pickAssistant();
            board.getLastAssistantUsed().get(i).getOwner().getAssistantDeck().removeCard(chosenCard);
            board.setLastAssistantUsed(i, chosenCard);
        }

        for (int j = 0; j < board.getLastAssistantUsed().size(); j++) {
            boolean flag = false;
            for(int k = 0; k < board.getLastAssistantUsed().size() - 1; k++) {
                if(board.getLastAssistantUsed().get(j).getValue() > board.getLastAssistantUsed().get(j + 1).getValue()) {
                    AssistantCard ac = board.getLastAssistantUsed().get(j);
                    board.setLastAssistantUsed(j, board.getLastAssistantUsed().get(j + 1));
                    board.setLastAssistantUsed(j + 1, ac);
                    flag = true;
                }
            }
            if(!flag) break;
        }
    }

    }

    public void startAction(){
        actionHandler.moveMotherNature();
        actionHandler.moveStudents();
        actionHandler.fromCloudToEntrance();
    }
}



