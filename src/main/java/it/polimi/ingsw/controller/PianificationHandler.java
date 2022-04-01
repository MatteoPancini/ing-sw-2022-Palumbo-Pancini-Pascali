package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.enumerations.*;

import java.util.ArrayList;

public class PianificationHandler {
    private final Game game;
    private final GameBoard board;

    public PianificationHandler(Game game, GameBoard board){
        this.game = game;
        this.board = board;
    }

    public void playAssistantCard() {
        for (int i = 0; i < game.getPlayersNumber(); i++) {
            AssistantCard chosenCard = board.getLastAssistantUsed().get(i).getOwner().pickAssistant();
            board.getLastAssistantUsed().get(i).setState(CardState.PLAYED);
            board.setLastAssistantUsed(i, chosenCard);
            chosenCard.setState(CardState.IN_USE);
        }

        for (int j = 0; j < board.getLastAssistantUsed().size(); j++) {
            int temp = 0;
            int minimum = j;
            for(int k = j + 1; k < board.getLastAssistantUsed().size(); k++){
                if (board.getLastAssistantUsed().get(minimum) > board.getLastAssistantUsed().get(k)) minimum = k;
                if (minimum != j){
                    temp = board.getLastAssistantUsed().get(j);
                    board.getLastAssistantUsed().get(j) = board.getLastAssistantUsed().get(minimum);
                    board.getLastAssistantUsed().get(minimum) = temp;
                }
            }
        }
    }

}
